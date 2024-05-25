package ru.kaplaan.api.domain.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Лабораторная работа по БПЛС",
        description = "В лабораторной работе разработан бизнес-процесс компании SuperJob",
        contact = Contact(
            name = "Dmitry Kaplan",
            email = "dmitry@kaplaan.ru",
            url = "https://kaplaan.ru",
        )
    )
)
class OpenApiConfig