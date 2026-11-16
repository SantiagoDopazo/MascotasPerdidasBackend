package mascotasperdidas.petidentification;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Arrays;
import java.util.Objects;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/// Contains the embedding and the type of the pet (PET_TYPE) in a photo
/// @param embedding the embedding of the pet in the photo (null if no pet detected)
/// @param type the type of the pet detected by the AI (DOG, CAT or NOT_DETECTED).
///             It is NOT_DETECTED if it wasn't possible to detect any dog or cat in the photo
@Embeddable
public record PetIdentity(
        @JdbcTypeCode(SqlTypes.ARRAY)
        @Column(name = "embedding", columnDefinition = "real[]") float[] embedding,
        @Enumerated(EnumType.STRING)
        @Column(name = "detected_type", length = 20) PET_TYPE_DETECTED type) {

    public PetIdentity {
        Objects.requireNonNull(type, "pet.identification.type.required");
        embedding = type == PET_TYPE_DETECTED.NOT_DETECTED || embedding == null ? null : embedding.clone();
    }

    @Override
    public float[] embedding() {
        return embedding == null ? null : embedding.clone();
    }

    public boolean hasEmbedding() {
        return embedding != null && embedding.length > 0;
    }

    public boolean canCompareWith(PetIdentity other) {
        return other != null && hasEmbedding() && other.hasEmbedding()
                && type == other.type && embedding.length == other.embedding.length;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof PetIdentity identity
                && type == identity.type && Arrays.equals(embedding, identity.embedding);
    }

    @Override
    public int hashCode() {
        return 31 * type.hashCode() + Arrays.hashCode(embedding);
    }
}
