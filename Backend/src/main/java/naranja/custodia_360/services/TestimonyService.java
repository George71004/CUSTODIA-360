package naranja.custodia_360.services;

import naranja.custodia_360.models.Testimony;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class TestimonyService {

    private final TranscriptionService transcriptionService;
    private final ObjectMapper objectMapper;

    public TestimonyService(TranscriptionService transcriptionService, ObjectMapper objectMapper) {
        this.transcriptionService = transcriptionService;
        this.objectMapper = objectMapper;
    }

    public Testimony processAudio(MultipartFile audioFile) {
        try {
            // 1. Transcribir directamente desde el stream del archivo recibido
            String text = transcriptionService.transcribe(audioFile.getInputStream());

            if (text == null || text.isBlank()) {
                throw new RuntimeException("No se detectó voz en el audio");
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Testimony testimony = new Testimony(text, timestamp);

            // 3. Guardar en la carpeta /data
            saveToJsonFile(testimony);

            return testimony;

        } catch (Exception e) {
            throw new RuntimeException("Error procesando el testimonio: " + e.getMessage());
        }
    }

    private void saveToJsonFile(Testimony testimony) throws Exception {
        String directoryPath = "data";

        // Crear carpeta si no existe
        Files.createDirectories(Paths.get(directoryPath));

        // Generar un nombre de archivo único para evitar sobrescritura
        String fileName = "testimony_" + UUID.randomUUID() + ".json";
        File file = new File(directoryPath + File.separator + fileName);

        // Escribir el JSON
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, testimony);
    }

}