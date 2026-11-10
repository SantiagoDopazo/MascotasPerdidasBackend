package mascotasperdidas.api.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import mascotasperdidas.dominio.Estado;
import mascotasperdidas.dominio.TipoAnimal;

public record PublicacionFiltro(
        TipoAnimal tipoAnimal,
        Estado estado,
        String zona,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {
}