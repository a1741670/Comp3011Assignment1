package au.edu.adelaide.speech_to_text.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import au.edu.adelaide.speech_to_text.service.TranscriptionService;

@RestController
@RequestMapping("/api/v1")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;

    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<TranscriptionResponse> transcribe(
            @RequestParam("audio") MultipartFile audioFile) throws IOException {

        String text = transcriptionService.transcribe(audioFile);

        return ResponseEntity.ok(
                new TranscriptionResponse(text)
        );
    }

    public static class TranscriptionResponse {

        private String text;

        public TranscriptionResponse(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}