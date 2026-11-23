package mascotasperdidas.publication;
import java.time.Instant;
/// Filtros opcionales del feed. Si un campo es null, ese filtro no se aplica.
/// @param species  tipo de animal (DOG / CAT)
/// @param type 	situacion de la mascota (LOST / FOUND)
/// @param zone 	texto a buscar en la zona (no distingue mayusculas)
/// @param from 	fecha desde (inclusive)
/// @param to   	fecha hasta (inclusive)
public record PublicationFilter(
        Species species,
        PublicationType type,
        String zone,
        Instant from,
        Instant to) {
    public PublicationFilter {
        zone = (zone == null || zone.isBlank()) ? null : zone.strip();
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("publication.filter.dateRange");
        }
    }
    /// Sin filtros: trae todas las activas
    public static PublicationFilter empty() {
        return new PublicationFilter(null, null, null, null, null);
    }
}

