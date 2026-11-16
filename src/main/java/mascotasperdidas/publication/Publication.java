package mascotasperdidas.publication;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import mascotasperdidas.user.User;

public class Publication {
    private final UUID id;
    private final User author;
    private final PublicationType type;
    private final Instant createdAt;
    private final Clock clock;
    private final List<Photo> photos;
    private PetCharacteristics characteristics;
    private Location location;
    private Instant occurredAt;
    private String description;
    private PublicationState state = new ActivePublication();
    private Instant deactivatedAt;

    public Publication(User author, PublicationType type, String description,
            PetCharacteristics characteristics, Location location,
            Instant occurredAt, List<Photo> photos) {
        this(author, type, description, characteristics, location, occurredAt, photos, Clock.systemUTC());
    }

    Publication(User author, PublicationType type, String description,
            PetCharacteristics characteristics, Location location,
            Instant occurredAt, List<Photo> photos, Clock clock) {
        this.id = UUID.randomUUID();
        this.author = Objects.requireNonNull(author, "publication.author.required");
        this.type = Objects.requireNonNull(type, "publication.type.required");
        this.clock = Objects.requireNonNull(clock, "publication.clock.required");
        this.createdAt = clock.instant();
        this.description = validateDescription(description);
        this.characteristics = Objects.requireNonNull(characteristics, "publication.characteristics.required");
        this.location = Objects.requireNonNull(location, "publication.location.required");
        this.occurredAt = Objects.requireNonNull(occurredAt, "publication.occurrence.required");
        this.photos = new ArrayList<>(List.copyOf(photos));
        if (this.photos.stream().distinct().count() != this.photos.size()) {
            throw new IllegalArgumentException("publication.photos.duplicate");
        }
    }

    public void updateInformation(String description, PetCharacteristics characteristics,
            Location location, Instant occurredAt) {
        state.validateEditing();
        String newDescription = validateDescription(description);
        Objects.requireNonNull(characteristics, "publication.characteristics.required");
        Objects.requireNonNull(location, "publication.location.required");
        Objects.requireNonNull(occurredAt, "publication.occurrence.required");
        this.description = newDescription;
        this.characteristics = characteristics;
        this.location = location;
        this.occurredAt = occurredAt;
    }

    public void addPhoto(Photo photo) {
        state.validateEditing();
        Objects.requireNonNull(photo, "publication.photo.required");
        if (photos.contains(photo)) {
            throw new IllegalArgumentException("publication.photo.alreadyPresent");
        }
        photos.add(photo);
    }

    public void removePhoto(Photo photo) {
        state.validateEditing();
        Objects.requireNonNull(photo, "publication.photo.required");
        if (!photos.remove(photo)) {
            throw new IllegalArgumentException("publication.photo.notFound");
        }
    }

    public void close() {
        deactivate(state.close());
    }

    public void cancel() {
        deactivate(state.cancel());
    }

    private void deactivate(PublicationState target) {
        deactivatedAt = clock.instant();
        state = target;
    }

    public boolean isActive() {
        return state.isVisible();
    }

    private static String validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("publication.description.required");
        }
        return description.strip();
    }

    public UUID getId() { return id; }
    public User getAuthor() { return author; }
    public PublicationType getType() { return type; }
    public String getDescription() { return description; }
    public PetCharacteristics getCharacteristics() { return characteristics; }
    public Location getLocation() { return location; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getCreatedAt() { return createdAt; }
    public PublicationState getState() { return state; }
    public Optional<Instant> getDeactivatedAt() { return Optional.ofNullable(deactivatedAt); }
    public List<Photo> getPhotos() { return List.copyOf(photos); }
}
