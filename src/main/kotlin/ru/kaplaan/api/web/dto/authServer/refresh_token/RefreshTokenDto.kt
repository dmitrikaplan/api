package ru.kaplaan.api.web.dto.authServer.refresh_token

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Сущность токена обновления")
data class RefreshTokenDto(
    @Schema(description = "JWT refresh token", example = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6Im5ld3RvbjA0NCIsImVtYWlsIjoiZG1pdHJ5QGthcGxhYW4ucnUiLCJwYXNzd29yZCI6IiQyYSQxMCRITHlkeEliZ2dsdEw0bzRLaEZya01PNTl5RFBiUXEvS0UvTGpJaXozLnIyRndrdVIucC9hZSIsImlhdCI6MTcxMzI3NTUzNiwiZXhwIjoxNzE1ODY3NTM2fQ.PSsCdPf6Pmh8DLqxc2WIZ86LxXhyTnfLMhIFwnD3TsWCs2ld-gGsOwkyOoUqEF5SPc5PFsQ3YqHye3ahuNIzLA")
    @field:NotBlank(message = "Refresh token не должен быть пустым!")
    val refreshToken: String
)