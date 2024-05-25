package ru.kaplaan.api.domain.exceptionHandler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.kaplaan.api.domain.exception.RoleNotFoundException


@RestControllerAdvice
class RequestExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(RoleNotFoundException::class)
    fun roleNotFoundExceptionHandler(e: RoleNotFoundException): ResponseEntity<ProblemDetail> =
        ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
            .apply {
                setProperty("errors", e.message)
            }
            .also {
                log.error(e.message)
            }
            .let {
                ResponseEntity.badRequest().body(it)
            }
}