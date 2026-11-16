package mascotasperdidas.publication;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class PublicationStateConverter implements AttributeConverter<PublicationState, String> {
    private static final Map<PublicationState, String> VALUES = Map.of(
            new ActivePublication(), "ACTIVA",
            new ClosedPublication(), "CERRADA",
            new CancelledPublication(), "CANCELADA");

    @Override
    public String convertToDatabaseColumn(PublicationState state) {
        return state == null ? null : VALUES.get(state);
    }

    @Override
    public PublicationState convertToEntityAttribute(String value) {
        return value == null ? null : VALUES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey).findFirst().orElseThrow();
    }
}
