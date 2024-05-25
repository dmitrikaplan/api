package ru.kaplaan.api.domain.exceptionHandler

import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import ru.kaplaan.api.domain.exception.BadResponseException

@RestControllerAdvice
class ResponseExceptionHandler {

    @ExceptionHandler(BadResponseException::class)
    fun emptyBodyExceptionHandler(e: BadResponseException): Mono<ResponseEntity<ProblemDetail>> {
        return e.response
    }

}