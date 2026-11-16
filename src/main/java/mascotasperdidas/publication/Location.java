package mascotasperdidas.publication;

public record Location(double latitude, double longitude, String description) {
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
