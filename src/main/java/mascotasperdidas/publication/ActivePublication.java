package mascotasperdidas.publication;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;

@Embeddable
@DiscriminatorValue("ACTIVE")
public final class ActivePublication extends PublicationState {
    public ActivePublication() {
        super("ACTIVE");
    }

    @Override
    public void validateEditing() {
    }

    @Override
    public PublicationState close() {
        return new ClosedPublication();
    }

    @Override
    public PublicationState cancel() {
        return new CancelledPublication();
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
