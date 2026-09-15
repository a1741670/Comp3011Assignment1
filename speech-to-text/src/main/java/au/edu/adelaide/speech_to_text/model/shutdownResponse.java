package au.edu.adelaide.speech_to_text.model;

public class shutdownResponse {

    private String message;

    public shutdownResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}