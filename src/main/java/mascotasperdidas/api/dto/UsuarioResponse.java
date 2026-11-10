package mascotasperdidas.api.dto;

import mascotasperdidas.dominio.Rol;

public record UsuarioResponse(Long id, String username, String email, Rol rol) {
}