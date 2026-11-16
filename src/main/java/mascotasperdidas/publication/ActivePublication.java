package mascotasperdidas.publication;

public record ActivePublication() implements PublicationState {
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
