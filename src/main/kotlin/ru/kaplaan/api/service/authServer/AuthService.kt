package ru.kaplaan.api.service.authServer

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.kaplaan.api.web.dto.authServer.refresh_token.RefreshTokenDto
import ru.kaplaan.api.web.dto.authServer.response.JwtResponse
import ru.kaplaan.api.web.dto.authServer.response.MessageResponse
import ru.kaplaan.api.web.dto.authServer.user.UserDto
import ru.kaplaan.api.web.dto.authServer.user.UserIdentificationDto

@Service
interface AuthService {

    fun activateAccount(code: String): Mono<String>

    fun register(userDto: Mono<UserDto>): Mono<MessageResponse>

    fun login(userIdentificationDto: Mono<UserIdentificationDto>): Mono<JwtResponse>

    fun refresh(refreshTokenDto: Mono<RefreshTokenDto>): Mono<JwtResponse>

    fun passwordRecovery(userIdentificationDto: Mono<UserIdentificationDto>): Mono<MessageResponse>
}