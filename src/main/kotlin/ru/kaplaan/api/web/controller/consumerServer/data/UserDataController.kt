package ru.kaplaan.api.web.controller.consumerServer.data

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.data.UserDataService
import ru.kaplaan.api.web.dto.consumerServer.data.UserDataDto
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@RestController
@RequestMapping("/api/v1/data/user")
@Tag(name = "User data controller", description = "Контролер взаимодействия с данными пользователей")
class UserDataController(
    private val userDataService: UserDataService
) {


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER_DATA')")
    @Operation(summary = "Сохранить информацию о пользователе")
    fun saveUserData(
        @RequestBody @Validated(OnCreate::class)
        userDataDto: Mono<UserDataDto>,
        authentication: Authentication
    ): Mono<UserDataDto> =
        userDataService.saveUserData(
            userDataDto.map {
                it.apply {
                    userId = (authentication.details as String).toLong()
                }
            }
        )

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_USER_DATA')")
    @Operation(summary = "Обновление информации о пользователе")
    fun updateUserData(
        @RequestBody @Validated(OnUpdate::class)
        userDataDto: Mono<UserDataDto>,
        authentication: Authentication
    ) =
        userDataService.updateUserData(
            userDataDto.map {
                it.apply {
                    userId = (authentication.details as String).toLong()
                }
            }
        )

    @GetMapping("/{userId}")
    @Operation(summary = "Получить информацию о пользователе")
    fun getUserDataByUsername(
        @Validated @Min(0, message = "Id пользователя должен быть больше или равен 0!")
        @Parameter(description = "Id пользователя")
        @PathVariable userId: Long
    ): Mono<UserDataDto> = userDataService.getUserDataByUserId(userId)

}