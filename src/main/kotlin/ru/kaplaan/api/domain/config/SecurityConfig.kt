package ru.kaplaan.api.domain.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.reactive.function.client.WebClient
import ru.kaplaan.api.domain.filter.JwtAuthenticationFilter
import ru.kaplaan.api.service.JwtService

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig (
    private val webClient: WebClient,
    private val jwtAuthenticationConverter: ServerAuthenticationConverter,
    private val jwtService: JwtService
) {

    @Value("\${auth-server.base-url}")
    lateinit var baseUrl: String

    @Value("\${auth-server.endpoint.authentication}")
    lateinit var authenticationEndpoint: String

    @Bean
    fun securityWebFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
        httpSecurity
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .cors {
                it.disable()
            }
            .authorizeExchange {
                it.pathMatchers(HttpMethod.GET, "actuator/**").permitAll()
                it.pathMatchers("swagger-ui.html").permitAll()
                it.pathMatchers("webjars/swagger-ui/**").permitAll()
                it.pathMatchers("v3/api-docs/**").permitAll()

                it.pathMatchers( "api/v1/auth/**").permitAll()

                it.pathMatchers(HttpMethod.GET, "api/v1/data/user/**").permitAll()
                it.pathMatchers(HttpMethod.GET, "api/v1/data/company/**").permitAll()

                it.pathMatchers(HttpMethod.GET, "api/v1/vacancy/**").permitAll()

                it.anyExchange().authenticated()
            }
            .addFilterBefore(JwtAuthenticationFilter("$baseUrl$authenticationEndpoint", jwtAuthenticationConverter, webClient, jwtService), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()


}
