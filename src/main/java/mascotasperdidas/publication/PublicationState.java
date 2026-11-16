package mascotasperdidas.publication;

public interface PublicationState {
    void validateEditing();

    PublicationState close();

    PublicationState cancel();

    boolean isVisible();
}
