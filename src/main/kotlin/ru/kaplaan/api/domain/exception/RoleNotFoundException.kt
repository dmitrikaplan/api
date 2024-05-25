package ru.kaplaan.api.domain.exception

class RoleNotFoundException: RuntimeException("Роль не найдена!") {
    override val message: String
        get() = super.message!!
}