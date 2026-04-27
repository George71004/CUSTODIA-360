package naranja.custodia_360.controllers;

import naranja.custodia_360.models.Booking;
import naranja.custodia_360.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> registerBooking(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("cedula") String governmentId,
            @RequestParam("photo") MultipartFile photo) {

        Booking savedBooking = bookingService.createBooking(firstName, lastName, governmentId, photo);
        return ResponseEntity.ok(savedBooking);
    }
}