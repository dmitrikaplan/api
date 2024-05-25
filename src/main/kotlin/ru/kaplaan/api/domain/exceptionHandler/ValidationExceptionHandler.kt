package ru.kaplaan.api.domain.exceptionHandler

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

@RestControllerAdvice
class ValidationExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun bindExceptionHandler(bindException: WebExchangeBindException): Mono<ResponseEntity<ProblemDetail>> =
        ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, bindException.message)
            .apply {
                setProperty("errors", bindException.allErrors.map { it.defaultMessage })
            }
            .let {
                Mono.just(ResponseEntity.status(it.status).body(it))
            }
}