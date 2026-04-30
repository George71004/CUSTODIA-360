package naranja.custodia_360.controllers;

import naranja.custodia_360.models.Testimony;
import naranja.custodia_360.services.AudioConversionService;
import naranja.custodia_360.services.TestimonyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/testimonies")
public class TestimonyController {

    private final TestimonyService testimonyService;
    private final AudioConversionService audioConversionService;

    public TestimonyController(TestimonyService testimonyService, AudioConversionService audioConversionService) {
        this.testimonyService = testimonyService;
        this.audioConversionService = audioConversionService;
    }

    /**
     * Endpoint para audios ya formateados (WAV 16kHz Mono).
     * Ideal para pruebas rápidas y máxima eficiencia.
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Testimony> registerReady(@RequestParam("audio") MultipartFile audio) {
        return ResponseEntity.ok(testimonyService.processAudio(audio));
    }

    /**
     * Endpoint para audios "raw" (MP3, WebM, etc.).
     */
    @PostMapping(value = "/raw", consumes = "multipart/form-data")
    public ResponseEntity<Testimony> registerRaw(@RequestParam("audio") MultipartFile audio) {
        // 1. Convertir el audio a formato compatible
        InputStream normalizedStream = null;
        try {
            normalizedStream = audioConversionService.convertToVoskFormat(audio);
            if(normalizedStream != null) System.out.println("Convertido");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 2. Procesar la transcripción con el audio ya normalizado
        Testimony testimony = testimonyService.processAudioFromStream(normalizedStream);

        return ResponseEntity.ok(testimony);
    }
}