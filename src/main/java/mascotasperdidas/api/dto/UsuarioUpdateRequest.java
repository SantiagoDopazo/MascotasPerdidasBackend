package mascotasperdidas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(@NotBlank @Size(min = 3, max = 30) String username) {
}