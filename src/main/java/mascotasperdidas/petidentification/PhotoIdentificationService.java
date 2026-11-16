package mascotasperdidas.petidentification;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import mascotasperdidas.publication.Photo;

public class PhotoIdentificationService {
    private final PetIdentification petIdentification;

    public PhotoIdentificationService(PetIdentification petIdentification) {
        this.petIdentification = Objects.requireNonNull(petIdentification);
    }

    public CompletableFuture<List<Photo>> identifyPhotos(List<Photo> photos, List<byte[]> images) {
        List<Photo> requestedPhotos = List.copyOf(photos);
        List<byte[]> requestedImages = List.copyOf(images);
        if (requestedPhotos.size() != requestedImages.size()) {
            throw new IllegalArgumentException("photo.identification.imageCount");
        }
        if (requestedPhotos.isEmpty()) {
            return CompletableFuture.completedFuture(List.of());
        }
        return petIdentification.identifyPetAsync(requestedImages).thenApply(results -> {
            if (results.size() != requestedPhotos.size()) {
                throw new IllegalStateException("photo.identification.resultCount");
            }
            return IntStream.range(0, requestedPhotos.size())
                    .mapToObj(index -> requestedPhotos.get(index).withIdentification(results.get(index)))
                    .toList();
        });
    }
}
