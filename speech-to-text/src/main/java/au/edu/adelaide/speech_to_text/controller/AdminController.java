package au.edu.adelaide.speech_to_text.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.edu.adelaide.speech_to_text.model.errorResponse;
import au.edu.adelaide.speech_to_text.model.globalStatsResponse;
import au.edu.adelaide.speech_to_text.model.shutdownResponse;
import au.edu.adelaide.speech_to_text.model.UptimeResponse;
import au.edu.adelaide.speech_to_text.service.statisticsService;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final Instant serverStartTime;
    private final statisticsService statisticsService;
    private final ConfigurableApplicationContext applicationContext;

    private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);

    public AdminController(
            statisticsService statisticsService,
            ConfigurableApplicationContext applicationContext) {

        this.serverStartTime = Instant.now();
        this.statisticsService = statisticsService;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/admin/uptime")
    public UptimeResponse getServerUptime() {

        Instant now = Instant.now();

        double uptimeSeconds =
                Duration.between(serverStartTime, now)
                        .toNanos() / 1_000_000_000.0;

        return new UptimeResponse(
                serverStartTime,
                now,
                uptimeSeconds
        );
    }

    @GetMapping("/global/stats")
    public globalStatsResponse getGlobalStats() {

        return new globalStatsResponse(
                statisticsService.getInputTokens(),
                statisticsService.getOutputTokens()
        );
    }

    @PostMapping("/admin/shutdown")
    public ResponseEntity<?> shutdownServer() {

        if (!shutdownRequested.compareAndSet(false, true)) {

            errorResponse errorResponse = new errorResponse(
                    Instant.now(),
                    409,
                    "Conflict",
                    "Graceful shutdown is already in progress.",
                    "/api/v1/admin/shutdown"
            );

            return ResponseEntity.status(409).body(errorResponse);
        }

        shutdownResponse response =
                new shutdownResponse("Graceful shutdown requested.");

        Thread shutdownThread = new Thread(() -> {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            applicationContext.close();

        });

        shutdownThread.start();

        return ResponseEntity.accepted().body(response);
    }
}