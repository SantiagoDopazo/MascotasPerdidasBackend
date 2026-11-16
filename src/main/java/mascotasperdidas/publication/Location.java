package mascotasperdidas.publication;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Location(
        @Column(nullable = false) double latitude,
        @Column(nullable = false) double longitude,
        @Column(name = "zona", length = 150) String description) {
    public Location {
        if (!Double.isFinite(latitude) || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("location.latitude.range");
        }
        if (!Double.isFinite(longitude) || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("location.longitude.range");
        }
        description = description == null ? "" : description.strip();
    }
}
