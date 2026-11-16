package mascotasperdidas.publication;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mascotasperdidas.user.User;

@Entity
@Table(name = "publicacion")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacion", nullable = false, length = 20, updatable = false)
    private PublicationType type;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant createdAt;

    @Transient
    private Clock clock = Clock.systemUTC();

    @ElementCollection
    @CollectionTable(name = "foto", joinColumns = @JoinColumn(name = "publicacion_id", nullable = false))
    @OrderColumn(name = "position")
    private List<Photo> photos = new ArrayList<>();

    @Embedded
    private PetCharacteristics characteristics;

    @Embedded
    private Location location;

    @Column(name = "fecha", nullable = false)
    private Instant occurredAt;

    @Column(name = "titulo", nullable = false, length = 150)
    private String title;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String description;

    @Embedded
    private PublicationState state = new ActivePublication();

    @Column(name = "deactivated_at")
    private Instant deactivatedAt;

    protected Publication() {
    }

    public Publication(User author, PublicationType type, String title, String description,
            PetCharacteristics characteristics, Location location,
            Instant occurredAt, List<Photo> photos) {
        this(author, type, title, description, characteristics, location, occurredAt, photos, Clock.systemUTC());
    }

    Publication(User author, PublicationType type, String title, String description,
            PetCharacteristics characteristics, Location location,
            Instant occurredAt, List<Photo> photos, Clock clock) {
        this.author = Objects.requireNonNull(author, "publication.author.required");
        this.type = Objects.requireNonNull(type, "publication.type.required");
        this.clock = Objects.requireNonNull(clock, "publication.clock.required");
        this.createdAt = clock.instant();
        this.title = title;
        this.description = validateDescription(description);
        this.characteristics = Objects.requireNonNull(characteristics, "publication.characteristics.required");
        this.location = Objects.requireNonNull(location, "publication.location.required");
        this.occurredAt = Objects.requireNonNull(occurredAt, "publication.occurrence.required");
        this.photos = new ArrayList<>(List.copyOf(photos));
        if (this.photos.stream().distinct().count() != this.photos.size()) {
            throw new IllegalArgumentException("publication.photos.duplicate");
        }
    }

    public void updateInformation(String title, String description, PetCharacteristics characteristics,
            Location location, Instant occurredAt) {
        state.validateEditing();
        String newDescription = validateDescription(description);
        Objects.requireNonNull(characteristics, "publication.characteristics.required");
        Objects.requireNonNull(location, "publication.location.required");
        Objects.requireNonNull(occurredAt, "publication.occurrence.required");
        this.title = title;
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

    public Long getId() { return id; }
    public User getAuthor() { return author; }
    public PublicationType getType() { return type; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public PetCharacteristics getCharacteristics() { return characteristics; }
    public Location getLocation() { return location; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getCreatedAt() { return createdAt; }
    public PublicationState getState() { return state; }
    public Optional<Instant> getDeactivatedAt() { return Optional.ofNullable(deactivatedAt); }
    public List<Photo> getPhotos() { return List.copyOf(photos); }
}
