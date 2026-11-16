package mascotasperdidas.publication;

public record ClosedPublication() implements PublicationState {
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
