package mascotasperdidas.publication;

public record CancelledPublication() implements PublicationState {
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
