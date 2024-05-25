package ru.kaplaan.api.web.controller.consumerServer.data

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.data.CompanyDataService
import ru.kaplaan.api.web.dto.consumerServer.data.CompanyDataDto
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@RestController
@RequestMapping("/api/v1/data/company")
@Tag(name = "Company Data controller", description = "Контролер взаимодействия с данными компаний")
class CompanyDataController(
    private val companyDataService: CompanyDataService
) {

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_COMPANY_DATA')")
    @Operation(summary = "Добавление информации о компании")
    fun saveCompanyData(
        @RequestBody @Validated(OnCreate::class)
        companyDataDto: Mono<CompanyDataDto>,
        authentication: Authentication
    ): Mono<CompanyDataDto> =
        companyDataService.saveCompanyData(
            companyDataDto.map {
                it.apply {
                    companyId =  (authentication.details as String).toLong()
                }
            }
        )


    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_COMPANY_DATA')")
    @Operation(summary = "Обновление информации о компании")
    fun updateCompanyData(
        @RequestBody @Validated(OnUpdate::class)
        companyDataDto: Mono<CompanyDataDto>,
        authentication: Authentication
    ): Mono<CompanyDataDto> =
        companyDataService.updateCompanyData(
            companyDataDto.map {
                it.apply {
                    companyId = (authentication.details as String).toLong()
                }
            }
        )

    @GetMapping("/{companyId}")
    @Operation(summary = "Получить информацию о компании по Id компании")
    fun getCompanyDataByCompanyId(
        @Validated @Min(0, message = "Id компании не должен быть больше или равен 0!")
        @Parameter(description = "Название компании", required = true)
        @PathVariable companyId: Long
    ): Mono<CompanyDataDto> = companyDataService.getCompanyDataByCompanyId(companyId)
}