package mascotasperdidas.petidentification;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface PetIdentification {

    List<PetIdentity> identifyPet(List<byte[]> images);


    CompletableFuture<List<PetIdentity>> identifyPetAsync(List<byte[]> images);
}
