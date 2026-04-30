package naranja.custodia_360.services;

import org.vosk.Model;
import org.vosk.Recognizer;
import org.springframework.stereotype.Service;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class TranscriptionService {

    private final Model model;

    public TranscriptionService() throws Exception {
        // Al estar fuera del classpath, se accede directamente
        File modelDir = new File("models/vosk-model-es");

        if (!modelDir.exists() || !modelDir.isDirectory()) {
            throw new RuntimeException("No se encontró el modelo en: " + modelDir.getAbsolutePath());
        }

        this.model = new Model(modelDir.getAbsolutePath());
    }

    public String transcribe(String audioPath) {
        try (InputStream ais = new FileInputStream(audioPath);
             BufferedInputStream bis = new BufferedInputStream(ais);
             Recognizer recognizer = new Recognizer(model, 16000.0f)) {
            byte[] buffer = new byte[4096];
            int nbytes;
            while ((nbytes = bis.read(buffer)) >= 0) {
                recognizer.acceptWaveForm(buffer, nbytes);
            }

            // Extraemos solo el texto del JSON que devuelve Vosk
            String result = recognizer.getFinalResult();
            return cleanJsonResult(result);

        } catch (Exception e) {
            throw new RuntimeException("Error en la transcripción de audio", e);
        }
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
        // Vosk devuelve algo como: { "text" : "hola como estas" }
        // Para no meter librerías de JSON extra, un split simple sirve:
        System.out.println(json);
        return json.split("\"text\" : \"")[1].split("\"")[0];
    }
}