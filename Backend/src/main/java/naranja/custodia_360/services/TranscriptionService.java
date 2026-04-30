package naranja.custodia_360.services;

import org.springframework.beans.factory.annotation.Value;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.InputStream;

@Service
public class TranscriptionService {

    private final Model model;

    public TranscriptionService(@Value("${vosk.model.path}") String path) throws Exception {
        this.model = new Model(path);
    }

    public String transcribe(InputStream inputStream) {
        try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            Recognizer recognizer = new Recognizer(model, 16000.0f);
            byte[] buffer = new byte[4096];
            int nbytes;
            while ((nbytes = bis.read(buffer)) >= 0) {
                recognizer.acceptWaveForm(buffer, nbytes);
            }
            String result = recognizer.getFinalResult();
            return cleanJsonResult(result);

        } catch (Exception e) {
            throw new RuntimeException("Error en la transcripción de audio", e);
        }
    }

    private String cleanJsonResult(String json) {
        // Vosk devuelve algo como: {"text": "Hola, como estas"}
        // Para no meter librerías de JSON extra, un split simple sirve:
        System.out.println(json);
        return json.split("\"text\" : \"")[1].split("\"")[0];
    }
}