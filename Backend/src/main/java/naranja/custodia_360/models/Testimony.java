package naranja.custodia_360.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "testimonies")
public class Testimony {

    @Id
    private final UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // El texto transcrito

    @Column(name = "created_at", nullable = false)
    private final LocalDateTime createdAt;

    public Testimony() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}