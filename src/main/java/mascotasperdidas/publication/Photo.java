package mascotasperdidas.publication;

public record Photo(String reference) {
    public Photo {
        if (reference == null || reference.isBlank()) {
            throw new IllegalArgumentException("photo.reference.required");
        }
        reference = reference.strip();
    }
}
