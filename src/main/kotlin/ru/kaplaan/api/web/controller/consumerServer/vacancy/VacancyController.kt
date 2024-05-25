package ru.kaplaan.api.web.controller.consumerServer.vacancy

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.vacancy.VacancyService
import ru.kaplaan.api.web.dto.consumerServer.vacancy.ArchiveVacancyDto
import ru.kaplaan.api.web.dto.consumerServer.vacancy.VacancyDto
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@RestController
@RequestMapping("/api/v1/vacancy")
@Tag(name = "Vacancy Controller", description = "Контролер взаимодействия с вакансиями")
class VacancyController(
    private val vacancyService: VacancyService
) {

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_VACANCY')")
    @Operation(summary = "Добавление вакансии")
    fun save(
        @RequestBody @Validated(OnCreate::class)
        vacancyDto: Mono<VacancyDto>,
        authentication: Authentication
    ): Mono<VacancyDto> =
        vacancyService.save(
            vacancyDto.map {
                it.apply {
                    companyId = (authentication.details as String).toLong()
                }
            }
        )

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_VACANCY')")
    @Operation(summary = "Обновление вакансии")
    fun update(
        @RequestBody @Validated(OnUpdate::class)
        vacancyDto: Mono<VacancyDto>,
        authentication: Authentication
    ): Mono<VacancyDto> =
        vacancyService.update(
            vacancyDto.map {
                it.apply {
                    companyId = (authentication.details as String).toLong()
                }
            }
        )

    @DeleteMapping("/{vacancyId}")
    @PreAuthorize("hasAuthority('DELETE_VACANCY')")
    @Operation(summary = "Удаление вакансии")
    fun delete(
        @PathVariable
        @Parameter(description = "Id вакансии", required = true)
        vacancyId: Long,
        authentication: Authentication
    ): Mono<Any> =
        vacancyService.delete((authentication.details as String).toLong(), vacancyId)

    @GetMapping("/get-by-vacancy-id/{vacancyId}")
    @Operation(summary = "Получить вакансию по Id вакансии")
    fun getVacancyById(
        @Parameter(description = "Id вакансии", required = true)
        @PathVariable vacancyId: Long
    ): Mono<VacancyDto> =
        vacancyService.getVacancyById(vacancyId)

    @GetMapping("/get-by-company-id/{companyId}/{page}")
    @Operation(summary = "Получить вакансии по названию компании")
    fun getVacanciesByCompanyName(
        @Validated @Min(0, message = "Id компании не должен быть больше или равен 0!")
        @Parameter(description = "Id компании", required = true)
        @PathVariable companyId: Long,
        @Parameter(description = "Номер страницы", required = true)
        @Validated @Min(0, message = "Номер страницы должен быть положительным числом!")
        @PathVariable page: Int
    ): Flux<VacancyDto> =
        vacancyService.getVacanciesByCompanyId(companyId, page)


//    @GetMapping("/{page}")
//    @Operation(summary = "Получить все вакансии")
//    fun getVacancies(
//        @Parameter(description = "номер страницы", required = true)
//        @Validated @Min(0, message = "Номер страницы должен быть положительным числом!")
//        @PathVariable page: Int
//    ): Mono<ResponseEntity<Flux<VacancyDto>>> = vacancyService.getVacancies(page)

    @GetMapping("/get-by-text/{text}/{page}")
    @Operation(summary = "Получить вакансии по тексту")
    fun getVacanciesByText(
        @Parameter(description = "текст", required = true)
        @PathVariable text: String,
        @Parameter(description = "номер страницы", required = true)
        @Validated @Min(0, message = "Номер страницы должен быть положительным числом!")
        @PathVariable page: Int
    ): Flux<VacancyDto> = vacancyService.getVacanciesByText(text, page)


    @PutMapping("/archive")
    @PreAuthorize("hasAuthority('UPDATE_VACANCY')")
    @Operation(summary = "Архивирование вакансии")
    fun archiveVacancy(
        @RequestBody @Validated(OnCreate::class)
        archiveVacancyDto: Mono<ArchiveVacancyDto>,
        authentication: Authentication
    ): Mono<Any> =
        vacancyService.archiveVacancy(
            archiveVacancyDto.map {
                it.apply {
                    this.companyId = (authentication.details as String).toLong()
                }
            }
        )


    @PutMapping("/unarchive")
    @PreAuthorize("hasAuthority('UPDATE_VACANCY')")
    @Operation(summary = "Разархивирование вакансии")
    fun unarchiveVacancy(
        @RequestBody @Validated(OnUpdate::class)
        archiveVacancyDto: Mono<ArchiveVacancyDto>,
        authentication: Authentication
    ): Mono<Any> =
        vacancyService.unarchiveVacancy(
            archiveVacancyDto.map {
                it.apply {
                    this.companyId = (authentication.details as String).toLong()
                }
            }
        )
}