package mascotasperdidas.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import mascotasperdidas.dominio.Estado;
import mascotasperdidas.dominio.TipoAnimal;

public record PublicacionResponse(
        Long id,
        TipoAnimal tipoAnimal,
        Estado estado,
        String titulo,
        String descripcion,
        String zona,
        LocalDate fecha,
        LocalDateTime fechaCreacion,
        AutorResponse autor,
        List<FotoResponse> fotos) {
}