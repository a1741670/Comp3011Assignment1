package au.edu.adelaide.speech_to_text.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;
// AI was used to help write this code. The AI was prompted to write a transcription service that uses the OpenAI API to transcribe audio files. 
// The AI was given the following prompt: "Write a Java service class that uses the OpenAI API to transcribe audio files. 
// The service should have a method that takes a MultipartFile as input, saves it to a temporary file, sends it to the OpenAI API for transcription, and returns the transcribed text. 
// The service should handle any exceptions that may occur during the process and ensure that the temporary file is deleted after the transcription is complete.
@Service
public class TranscriptionService {

    private final OpenAIClient openAIClient;

    public TranscriptionService() {
        openAIClient = OpenAIOkHttpClient.fromEnv();
    }

    public String transcribe(MultipartFile audioFile) throws IOException {

        Path temporaryFile = Files.createTempFile(
                "speech-to-text-",
                ".webm"
        );

        try {

            audioFile.transferTo(temporaryFile);

            System.out.println("Audio file received.");
            System.out.println("Audio file size: " + Files.size(temporaryFile) + " bytes.");

            TranscriptionCreateParams params =
                    TranscriptionCreateParams.builder()
                            .file(temporaryFile)
                            .model("gpt-4o-mini-transcribe")
                            .language("en")
                            .build();

            System.out.println("Sending audio to OpenAI...");

            TranscriptionCreateResponse response =
                    openAIClient.audio()
                            .transcriptions()
                            .create(params);

            System.out.println("OpenAI transcription response received.");

            

            String text = response.transcription()
                    .get()
                    .text();

            System.out.println("Transcription completed.");

            return text;

        } catch (Exception e) {

            System.err.println("TRANSCRIPTION ERROR:");
            e.printStackTrace();

            throw e;

        } finally {

            Files.deleteIfExists(temporaryFile);
        }
    }
}