package mascotasperdidas.publication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;
import mascotasperdidas.petidentification.PetIdentity;

@Embeddable
public record Photo(
        @Column(name = "url", nullable = false, length = 500) String reference,
        @Embedded PetIdentity identification) {
    public Photo(String reference) {
        this(reference, null);
    }

    public Photo {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("photo.reference.required");
        }
        reference = reference.strip();
    }

    public boolean isIdentificationPending() {
        return identification == null;
    }

    public boolean hasEmbedding() {
        return identification != null && identification.hasEmbedding();
    }

    public Photo withIdentification(PetIdentity identification) {
        return new Photo(reference, Objects.requireNonNull(identification, "photo.identification.required"));
    }
}
