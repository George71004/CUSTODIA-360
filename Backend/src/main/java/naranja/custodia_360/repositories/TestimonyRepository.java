package naranja.custodia_360.repositories;

import naranja.custodia_360.models.Testimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TestimonyRepository extends JpaRepository<Testimony, UUID> {
    // Al heredar de JpaRepository, ya tienes métodos como:
    // save(), findById(), findAll(), deleteById(), etc.
}