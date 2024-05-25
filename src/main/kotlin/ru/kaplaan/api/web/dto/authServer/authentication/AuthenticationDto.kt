package ru.kaplaan.api.web.dto.authServer.authentication


data class AuthenticationDto(
    val principal: Any,
    val credentials: Any,
    val authorities: List<String>,
)