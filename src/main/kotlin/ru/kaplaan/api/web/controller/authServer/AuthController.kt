package ru.kaplaan.api.web.controller.authServer

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.authServer.AuthService
import ru.kaplaan.api.service.authServer.UserInfoService
import ru.kaplaan.api.web.dto.authServer.refresh_token.RefreshTokenDto
import ru.kaplaan.api.web.dto.authServer.response.JwtResponse
import ru.kaplaan.api.web.dto.authServer.response.MessageResponse
import ru.kaplaan.api.web.dto.authServer.user.Role
import ru.kaplaan.api.web.dto.authServer.user.UserDto
import ru.kaplaan.api.web.dto.authServer.user.UserIdentificationDto
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnRecovery

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Контроллер аутентификации")
class AuthController(
    private val authService: AuthService,
    private val userInfoService: UserInfoService,
) {

    @GetMapping("/test")
    fun test(): String{
        return "test"
    }

    @PostMapping("/registration/user")
    @Operation(summary = "Регистрация пользователя")
    fun registerUser(
        @RequestBody @Validated(OnCreate::class)
        userDto: Mono<UserDto>,
    ): Mono<MessageResponse> {
        return authService.register(
            userDto.map {
                it.apply {
                    it.role = Role.ROLE_USER
                }
            }
        )
    }

    @PostMapping("/registration/company")
    @Operation(summary = "Регистрация компании")
    fun registerCompany(
        @RequestBody @Validated(OnCreate::class)
        userDto: Mono<UserDto>,
    ): Mono<MessageResponse> {
        return authService.register(
            userDto.map {
                it.apply {
                    this.role = Role.ROLE_COMPANY
                }
            }
        )
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    fun login(
        @RequestBody @Validated(OnCreate::class)
        userIdentificationDto: Mono<UserIdentificationDto>,
    ): Mono<JwtResponse> = authService.login(userIdentificationDto)

    @GetMapping("/activation/{activationCode}")
    @Operation(summary = "Активация аккаунта пользователя")
    fun activateAccount(
        @PathVariable @Validated @NotBlank
        @Parameter(description = "код активации аккаунта", required = true)
        activationCode: String,
    ): Mono<String> = authService.activateAccount(activationCode)

    @PostMapping("/recovery")
    @Operation(summary = "Восстановление доступа пользователя", hidden = true)
    fun passwordRecovery(
        @RequestBody @Validated(OnRecovery::class)
        userIdentificationDto: Mono<UserIdentificationDto>,
    ): Mono<MessageResponse> = authService.passwordRecovery(userIdentificationDto)

    @Operation(summary = "Обновление jwt access токена")
    @PostMapping("/refresh")
    fun refresh(
        @RequestBody @Validated refreshTokenDto: Mono<RefreshTokenDto>,
    ): Mono<JwtResponse> = authService.refresh(refreshTokenDto)

    @Operation(summary = "Получить username пользователя по Id")
    @GetMapping("/{userId}")
    fun getUsernameById(
        @Validated @Min(0, message = "Минимальное Id пользователя - 0!")
        @PathVariable userId: Long,
    ): Mono<String> = userInfoService.getUsernameByUserId(userId)
}
