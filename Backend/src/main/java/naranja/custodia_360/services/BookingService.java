package naranja.custodia_360.services;

import naranja.custodia_360.models.Booking;
import naranja.custodia_360.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(String firstName, String lastName, String cedula, MultipartFile photo) {
        // 1. Validar unicidad ANTES de tocar el disco
        if (bookingRepository.existsByCedula(cedula)) {
            throw new RuntimeException("Cedula already exists");
        }
        Booking booking = new Booking();
        booking.setFirstName(firstName);
        booking.setLastName(lastName);
        booking.setCedula(cedula);

        if (photo != null && !photo.isEmpty()) {
            String path = savePhoto(booking.getId(), photo);
            booking.setPhotoPath(path);
        }

        return bookingRepository.save(booking);
    }

    private String savePhoto(UUID id, MultipartFile photo) {
        try {
            String fileExtension = getFileExtension(photo.getOriginalFilename());
            String fileName = id + fileExtension;
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(photo.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not store the photo file.", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return ".jpg";
        return fileName.substring(fileName.lastIndexOf("."));
    }
}