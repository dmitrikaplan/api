package ru.kaplaan.api.service.authServer.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.authServer.AuthService
import ru.kaplaan.api.web.dto.authServer.refresh_token.RefreshTokenDto
import ru.kaplaan.api.web.dto.authServer.response.JwtResponse
import ru.kaplaan.api.web.dto.authServer.response.MessageResponse
import ru.kaplaan.api.web.dto.authServer.user.UserDto
import ru.kaplaan.api.web.dto.authServer.user.UserIdentificationDto

@Service
class AuthServiceImpl(
    private val webClient: WebClient
): AuthService {

    @Value("\${auth-server.base-url}")
    lateinit var baseUrl: String

    @Value("\${auth-server.endpoint.registration}")
    lateinit var registrationEndpoint: String

    @Value("\${auth-server.endpoint.login}")
    lateinit var loginEndpoint: String

    @Value("\${auth-server.endpoint.activation}")
    lateinit var activationEndpoint: String

    @Value("\${auth-server.endpoint.recovery}")
    lateinit var recoveryEndpoint: String

    @Value("\${auth-server.endpoint.refresh}")
    lateinit var refreshEndpoint: String

    override fun register(userDto: Mono<UserDto>): Mono<MessageResponse> =
        webClient
            .post()
            .uri("$baseUrl/$registrationEndpoint")
            .body(userDto)
            .retrieve()
            .bodyToMono(MessageResponse::class.java)


    override fun login(userIdentificationDto: Mono<UserIdentificationDto>): Mono<JwtResponse> =
        webClient
            .post()
            .uri("$baseUrl/$loginEndpoint")
            .body(userIdentificationDto)
            .retrieve()
            .bodyToMono(JwtResponse::class.java)

    override fun activateAccount(code: String): Mono<String> =
        webClient
            .get()
            .uri("$baseUrl/$activationEndpoint/$code")
            .retrieve()
            .bodyToMono(String::class.java)

    override fun passwordRecovery(userIdentificationDto: Mono<UserIdentificationDto>): Mono<MessageResponse> =
        webClient
            .post()
            .uri("$baseUrl/$recoveryEndpoint")
            .body(userIdentificationDto)
            .retrieve()
            .bodyToMono(MessageResponse::class.java)

    override fun refresh(refreshTokenDto: Mono<RefreshTokenDto>): Mono<JwtResponse> =
        webClient
            .post()
            .uri("$baseUrl/$refreshEndpoint")
            .body(refreshTokenDto)
            .retrieve()
            .bodyToMono(JwtResponse::class.java)
}