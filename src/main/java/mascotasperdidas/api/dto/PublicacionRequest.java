package mascotasperdidas.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import mascotasperdidas.dominio.Estado;
import mascotasperdidas.dominio.TipoAnimal;

public record PublicacionRequest(
        @NotNull TipoAnimal tipoAnimal,
        @NotNull Estado estado,
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 2000) String descripcion,
        @NotBlank @Size(max = 100) String zona,
        @NotNull @PastOrPresent LocalDate fecha) {
}