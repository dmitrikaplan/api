package ru.kaplaan.api.service.consumerServer.vacancy

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.kaplaan.api.web.dto.consumerServer.vacancy.ArchiveVacancyDto
import ru.kaplaan.api.web.dto.consumerServer.vacancy.VacancyDto


@Service
interface VacancyService {

    fun save(vacancyDto: Mono<VacancyDto>): Mono<VacancyDto>

    fun update(vacancyDto: Mono<VacancyDto>): Mono<VacancyDto>

    fun delete(companyId: Long, vacancyId: Long): Mono<Any>

    fun getVacancyById(vacancyId: Long): Mono<VacancyDto>

    fun getVacanciesByCompanyId(companyId: Long, pageNumber: Int): Flux<VacancyDto>

    fun getVacancies(pageNumber: Int): Flux<VacancyDto>

    fun getVacanciesByText(text: String, pageNumber: Int): Flux<VacancyDto>

    fun archiveVacancy(archiveVacancyDto: Mono<ArchiveVacancyDto>): Mono<Any>

    fun unarchiveVacancy(archiveVacancyDto: Mono<ArchiveVacancyDto>): Mono<Any>
}