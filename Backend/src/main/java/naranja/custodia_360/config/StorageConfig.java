package naranja.custodia_360.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {
    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @PostConstruct
    public void initDirectory() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Carpeta de almacenamiento creada en: " + uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de fotos", e);
        }
    }
}
