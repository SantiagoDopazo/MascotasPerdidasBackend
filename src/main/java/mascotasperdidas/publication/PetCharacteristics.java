package mascotasperdidas.publication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public record PetCharacteristics(
        @Enumerated(EnumType.STRING)
        @Column(name = "tipo_animal", nullable = false, length = 20) Species species,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20) PetSize size,
        @Column(nullable = false, columnDefinition = "text") String colors,
        @Column(name = "distinctive_marks", columnDefinition = "text") String distinctiveMarks) {

    public PetCharacteristics {
        Objects.requireNonNull(species, "pet.species.required");
        Objects.requireNonNull(size, "pet.size.required");
        if (colors == null || colors.isBlank()) {
            throw new IllegalArgumentException("pet.colors.required");
        }
        colors = colors.strip();
        distinctiveMarks = distinctiveMarks == null ? "" : distinctiveMarks.strip();
    }
}
