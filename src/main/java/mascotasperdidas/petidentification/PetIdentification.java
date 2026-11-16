package mascotasperdidas.petidentification;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/// Identify a pet from a photo using AI and returns their
/// embedding and the type of pet detected (DOG, CAT or NOT_DETECTED)
public interface PetIdentification {

    /// Generates the embeddings of the pet from a list of .jpg images converted to a sequence of bytes
    /// @param images the list of .jpg images, each one converted to a byte array format
    /// @return the embedding and the type of pet detected (DOG, CAT or NOT_DETECTED)
    List<PetIdentity> identifyPet(List<byte[]> images);


    /// Generates the embeddings of the pet from a list of .jpg images converted to a sequence of bytes
    /// @param images the list of .jpg images, each one converted to a byte array format
    /// @return the embedding and the type of pet detected (DOG, CAT or NOT_DETECTED), in an async format (using CompletableFuture)
    CompletableFuture<List<PetIdentity>> identifyPetAsync(List<byte[]> images);
}
