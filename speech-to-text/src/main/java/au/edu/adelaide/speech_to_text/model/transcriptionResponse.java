package au.edu.adelaide.speech_to_text.model;

public class transcriptionResponse {

    private String text;

    public transcriptionResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}