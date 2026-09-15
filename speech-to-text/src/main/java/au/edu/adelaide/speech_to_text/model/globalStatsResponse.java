package au.edu.adelaide.speech_to_text.model;

public class globalStatsResponse {

    private long inputTokens;
    private long outputTokens;

    public globalStatsResponse(long inputTokens, long outputTokens) {
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
    }

    public long getInputTokens() {
        return inputTokens;
    }

    public long getOutputTokens() {
        return outputTokens;
    }
}
