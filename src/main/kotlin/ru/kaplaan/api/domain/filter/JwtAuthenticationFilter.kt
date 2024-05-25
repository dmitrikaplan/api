package ru.kaplaan.api.domain.filter

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.onErrorResume
import reactor.kotlin.core.publisher.switchIfEmpty
import ru.kaplaan.api.domain.exception.JwtTokenNotFoundException
import ru.kaplaan.api.domain.exception.UserNotAuthenticatedException
import ru.kaplaan.api.service.JwtService
import ru.kaplaan.api.web.dto.authServer.authentication.AuthenticationDto
import ru.kaplaan.api.web.mapper.toDto
import ru.kaplaan.api.web.mapper.toUsernamePasswordAuthentication


class JwtAuthenticationFilter(
    private val url: String,
    private val jwtAuthenticationConverter: ServerAuthenticationConverter,
    private val webClient: WebClient,
    private val jwtService: JwtService
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
            return jwtAuthenticationConverter.convert(exchange)
                .switchIfEmpty{
                    Mono.error(JwtTokenNotFoundException())
                }
                .map {
                    it.toDto()
                }
                .flatMap {
                    authenticationRequest(Mono.just(it))
                }
                .doOnNext {
                    it.details = jwtService.extractUserIdFromAccessToken(extractToken(exchange))
                }
                .map {
                    ReactiveSecurityContextHolder.withAuthentication(it)
                }
                .flatMap { context ->
                    chain.filter(exchange).contextWrite(context)
                }
                .onErrorResume(AuthenticationException::class){
                    chain.filter(exchange)
                }
    }

    private fun authenticationRequest(authenticationDto: Mono<AuthenticationDto>): Mono<UsernamePasswordAuthenticationToken> {
        return webClient
            .post()
            .uri(url)
            .body(authenticationDto)
            .retrieve()
            .toEntity(AuthenticationDto::class.java)
            .publishOn(Schedulers.boundedElastic())
            .handle { response, sink ->
                if (response.statusCode != HttpStatus.OK || response.body == null)
                    sink.error(UserNotAuthenticatedException())
                else
                    sink.next(response.body!!.toUsernamePasswordAuthentication())

            }
    }

    private fun extractToken(exchange: ServerWebExchange): String{
        return exchange.request.headers[HttpHeaders.AUTHORIZATION]!!
            .first { headerValue -> headerValue.startsWith("Bearer ") }.split(" ")[1]
    }
}
