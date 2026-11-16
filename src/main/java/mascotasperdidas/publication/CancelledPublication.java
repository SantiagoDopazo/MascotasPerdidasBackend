package mascotasperdidas.publication;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;

@Embeddable
@DiscriminatorValue("CANCELLED")
public final class CancelledPublication extends PublicationState {
    public CancelledPublication() {
        super("CANCELLED");
    }

    @Override
    public void validateEditing() {
        throw new IllegalStateException("publication.cancelled.edit");
    }

    @Override
    public PublicationState close() {
        throw new IllegalStateException("publication.cancelled.close");
    }

    @Override
    public PublicationState cancel() {
        throw new IllegalStateException("publication.cancelled.cancel");
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
