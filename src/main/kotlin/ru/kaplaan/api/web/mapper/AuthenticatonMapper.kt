package ru.kaplaan.api.web.mapper

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import ru.kaplaan.api.domain.user.Privilege
import ru.kaplaan.api.web.dto.authServer.authentication.AuthenticationDto


fun Authentication.toDto() =
    AuthenticationDto(
        principal = this.principal,
        credentials = this.credentials,
        authorities = this.authorities.map { it.authority },
    )



fun AuthenticationDto.toUsernamePasswordAuthentication() =
    UsernamePasswordAuthenticationToken.authenticated(
        this.principal,
        this.credentials,
        this.authorities.map { Privilege.valueOf(it) }
    )!!