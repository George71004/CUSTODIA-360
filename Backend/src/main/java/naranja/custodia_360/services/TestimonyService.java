package naranja.custodia_360.services;

import naranja.custodia_360.models.Testimony;
import naranja.custodia_360.repositories.TestimonyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class TestimonyService {

    private final TestimonyRepository repository;
    private final TranscriptionService transcriptionService;

    public TestimonyService(TestimonyRepository repository, TranscriptionService transcriptionService) {
        this.repository = repository;
        this.transcriptionService = transcriptionService;
    }

    public Testimony processAudio(MultipartFile audioFile) {
        try {
            // 1. Transcribir directamente desde el stream del archivo recibido
            String text = transcriptionService.transcribe(audioFile.getInputStream());

            if (text == null || text.isBlank()) {
                throw new RuntimeException("No se detectó voz en el audio");
            }

            // 2. Crear y guardar la entidad
            Testimony testimony = new Testimony();
            testimony.setContent(text);

            return repository.save(testimony);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando el testimonio: " + e.getMessage());
        }
    }

    public Testimony processAudioFromStream(InputStream is) {
        String text = transcriptionService.transcribe(is); // Usa el método que ya corregimos

        if (text == null || text.isBlank()) {
            throw new RuntimeException("No se detectó voz");
        }

        Testimony testimony = new Testimony();
        testimony.setContent(text);
        return repository.save(testimony);
    }

}