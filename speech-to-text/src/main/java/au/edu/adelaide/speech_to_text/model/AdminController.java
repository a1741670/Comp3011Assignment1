package au.edu.adelaide.speech_to_text.model;

import java.time.Duration;
import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final Instant serverStartTime;

    public AdminController() {
        this.serverStartTime = Instant.now();
    }

    @GetMapping("/uptime")
    public UptimeResponse getUptime() {

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
}
