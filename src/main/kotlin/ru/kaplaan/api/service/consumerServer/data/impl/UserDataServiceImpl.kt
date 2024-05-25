package ru.kaplaan.api.service.consumerServer.data.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.data.UserDataService
import ru.kaplaan.api.web.dto.consumerServer.data.UserDataDto

@Service
class UserDataServiceImpl(
    private val webClient: WebClient
): UserDataService {

    @Value("\${consumer-server.base-url}")
    lateinit var baseUrl: String

    @Value("\${consumer-server.data.url}")
    lateinit var url: String

    @Value("\${consumer-server.data.save-user-data}")
    lateinit var saveUserDataEndpoint: String

    @Value("\${consumer-server.data.update-user-data}")
    lateinit var updateUserDataEndpoint: String

    @Value("\${consumer-server.data.get-user-data}")
    lateinit var getUserDataEndpoint: String

    override fun saveUserData(userDataDto: Mono<UserDataDto>): Mono<UserDataDto> =
        webClient
            .post()
            .uri("$baseUrl$url$saveUserDataEndpoint")
            .body(userDataDto)
            .retrieve()
            .bodyToMono(UserDataDto::class.java)

    override fun updateUserData(userDataDto: Mono<UserDataDto>): Mono<UserDataDto> =
        webClient
            .put()
            .uri("$baseUrl$url$updateUserDataEndpoint")
            .body(userDataDto)
            .retrieve()
            .bodyToMono(UserDataDto::class.java)

    override fun getUserDataByUserId(userId: Long): Mono<UserDataDto> =
        webClient
            .get()
            .uri("$baseUrl$url$getUserDataEndpoint/$userId")
            .retrieve()
            .bodyToMono(UserDataDto::class.java)
}