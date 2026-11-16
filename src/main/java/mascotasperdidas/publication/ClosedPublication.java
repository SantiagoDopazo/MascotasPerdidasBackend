package mascotasperdidas.publication;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;

@Embeddable
@DiscriminatorValue("CLOSED")
public final class ClosedPublication extends PublicationState {
    public ClosedPublication() {
        super("CLOSED");
    }

    @Override
    public void validateEditing() {
        throw new IllegalStateException("publication.closed.edit");
    }

    @Override
    public PublicationState close() {
        throw new IllegalStateException("publication.closed.close");
    }

    @Override
    public PublicationState cancel() {
        throw new IllegalStateException("publication.closed.cancel");
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
