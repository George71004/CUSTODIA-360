package naranja.custodia_360.controllers;

import naranja.custodia_360.models.Testimony;
import naranja.custodia_360.services.TestimonyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/testimonies")
public class TestimonyController {

    private final TestimonyService testimonyService;

    public TestimonyController(TestimonyService testimonyService) {
        this.testimonyService = testimonyService;
    }

    /**
     * Endpoint para audios ya formateados (WAV 16kHz Mono).
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Testimony> registerReady(@RequestParam("audio") MultipartFile audio) {
        return ResponseEntity.ok(testimonyService.processAudio(audio));
    }
}