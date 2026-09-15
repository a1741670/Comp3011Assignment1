package au.edu.adelaide.speech_to_text.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final AtomicLong inputTokens = new AtomicLong(0);
    private final AtomicLong outputTokens = new AtomicLong(0);

    public long getInputTokens() {
        return inputTokens.get();
    }

    public long getOutputTokens() {
        return outputTokens.get();
    }

    public void addInputTokens(long tokens) {
        inputTokens.addAndGet(tokens);
    }

    public void addOutputTokens(long tokens) {
        outputTokens.addAndGet(tokens);
    }
}