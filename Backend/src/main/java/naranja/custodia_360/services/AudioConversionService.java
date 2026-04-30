package naranja.custodia_360.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class AudioConversionService {

    public InputStream convertToVoskFormat(MultipartFile rawAudio) throws Exception {
        // 1. Crear archivos temporales para la conversión
        File source = File.createTempFile("raw_", rawAudio.getOriginalFilename());
        File target = File.createTempFile("normalized_", ".wav");
        rawAudio.transferTo(source);

        // 2. Configuración de Audio (Atributos para Vosk)
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le"); // Formato PCM 16 bits
        audio.setSamplingRate(16000); // 16kHz
        audio.setChannels(1);         // Mono

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("wav");
        attrs.setAudioAttributes(audio);

        // 3. Ejecutar conversión
        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attrs);

        // 4. Leer resultado en memoria y limpiar archivos temporales
        byte[] encodedBytes = Files.readAllBytes(target.toPath());
        source.delete();
        target.delete();

        return new ByteArrayInputStream(encodedBytes);
    }
}