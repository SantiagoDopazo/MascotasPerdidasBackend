package mascotasperdidas.publication;

import java.util.Objects;

public record PetCharacteristics(
        Species species, PetSize size, String colors, String distinctiveMarks) {

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
