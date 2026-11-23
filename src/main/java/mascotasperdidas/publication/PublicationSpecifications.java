package mascotasperdidas.publication;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;
/// Arma la consulta del feed a partir de los filtros elegidos.
/// Siempre trae solo publicaciones ACTIVE; el resto de los filtros son opcionales.
public final class PublicationSpecifications {
    private PublicationSpecifications() {
    }
    public static Specification<Publication> activeWith(PublicationFilter filter) {
        PublicationFilter f = filter == null ? PublicationFilter.empty() : filter;
        return (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>();
            // Siempre: solo publicaciones activas (columna etapa)
            conditions.add(cb.equal(root.get("state").get("code"), "ACTIVE"));
            // Tipo de animal (DOG / CAT)
            if (f.species() != null) {
                conditions.add(cb.equal(root.get("characteristics").get("species"), f.species()));
            }
            // Situacion de la mascota (LOST / FOUND)
            if (f.type() != null) {
                conditions.add(cb.equal(root.get("type"), f.type()));
            }
            // Zona: busca el texto en cualquier parte, sin distinguir mayusculas
            if (f.zone() != null) {
                String pattern = "%" + f.zone().toLowerCase(Locale.ROOT) + "%";
                conditions.add(cb.like(cb.lower(root.get("location").get("description")), pattern));
            }
            // Rango de fechas (cuando se perdio / encontro)
            if (f.from() != null) {
                conditions.add(cb.greaterThanOrEqualTo(root.get("occurredAt"), f.from()));
            }
            if (f.to() != null) {
                conditions.add(cb.lessThanOrEqualTo(root.get("occurredAt"), f.to()));
            }
            return cb.and(conditions.toArray(new Predicate[0]));
        };
    }
}


