package ru.kaplaan.api.service.consumerServer.data

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.kaplaan.api.web.dto.consumerServer.data.CompanyDataDto
import ru.kaplaan.api.web.dto.consumerServer.data.ContactPersonDto


@Service
interface CompanyDataService {

    fun saveCompanyData(companyDataDto: Mono<CompanyDataDto>): Mono<CompanyDataDto>

    fun updateCompanyData(companyDataDto: Mono<CompanyDataDto>): Mono<CompanyDataDto>

    fun getCompanyDataByCompanyId(companyId: Long): Mono<CompanyDataDto>

    fun getContactPersonByCompanyId(companyId: Long): Mono<ContactPersonDto>
}