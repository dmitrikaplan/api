package ru.kaplaan.api.service.consumerServer.data

import reactor.core.publisher.Mono
import ru.kaplaan.api.web.dto.consumerServer.data.UserDataDto

interface UserDataService {
    fun saveUserData(userDataDto: Mono<UserDataDto>): Mono<UserDataDto>

    fun updateUserData(userDataDto: Mono<UserDataDto>): Mono<UserDataDto>

    fun getUserDataByUserId(userId: Long): Mono<UserDataDto>
}