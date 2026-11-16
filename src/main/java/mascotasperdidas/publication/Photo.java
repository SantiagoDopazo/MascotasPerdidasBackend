package mascotasperdidas.publication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Photo(@Column(name = "url", nullable = false, length = 500) String reference) {
    public Photo {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("photo.reference.required");
        }
        reference = reference.strip();
    }
}
