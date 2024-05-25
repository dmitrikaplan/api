package ru.kaplaan.api.domain.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ProblemDetail
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.kaplaan.api.domain.exception.BadResponseException

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient =
        WebClient
            .builder()
            .defaultStatusHandler({it.isError}){ response ->
                Mono.error(BadResponseException(response.toEntity(ProblemDetail::class.java)))
            }
            .build()
}