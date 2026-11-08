package mascotasperdidas.petidentification;

/// Contains the embedding and the type of the pet (PET_TYPE) in a photo
/// @param embedding the embedding of the pet in the photo (null if no pet detected)
/// @param type the type of the pet detected by the AI (DOG, CAT or NOT_DETECTED).
///             It is NOT_DETECTED if it wasn't possible to detect any dog or cat in the photo
public record PetIdentity(float[] embedding, PET_TYPE_DETECTED type) { }
