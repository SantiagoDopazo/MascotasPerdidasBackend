package mascotasperdidas.publication;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class SpeciesConverter implements AttributeConverter<Species, String> {
    private static final Map<Species, String> VALUES = Map.of(
            Species.DOG, "PERRO", Species.CAT, "GATO");

    @Override
    public String convertToDatabaseColumn(Species species) {
        return species == null ? null : VALUES.get(species);
    }

    @Override
    public Species convertToEntityAttribute(String value) {
        return value == null ? null : VALUES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey).findFirst().orElseThrow();
    }
}
