package mascotasperdidas.publication;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class PublicationTypeConverter implements AttributeConverter<PublicationType, String> {
    private static final Map<PublicationType, String> VALUES = Map.of(
            PublicationType.LOST, "PERDIDO", PublicationType.FOUND, "ENCONTRADO");

    @Override
    public String convertToDatabaseColumn(PublicationType type) {
        return type == null ? null : VALUES.get(type);
    }

    @Override
    public PublicationType convertToEntityAttribute(String value) {
        return value == null ? null : VALUES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey).findFirst().orElseThrow();
    }
}
