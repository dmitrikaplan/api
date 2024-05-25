package ru.kaplaan.api.service.consumerServer.data.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.data.CompanyDataService
import ru.kaplaan.api.web.dto.consumerServer.data.CompanyDataDto
import ru.kaplaan.api.web.dto.consumerServer.data.ContactPersonDto


@Service
class CompanyDataServiceImpl(
    private val webClient: WebClient
): CompanyDataService {

    @Value("\${consumer-server.base-url}")
    lateinit var baseUrl: String

    @Value("\${consumer-server.data.url}")
    lateinit var url: String

    @Value("\${consumer-server.data.save-company-data}")
    lateinit var saveCompanyDataEndpoint: String

    @Value("\${consumer-server.data.update-company-data}")
    lateinit var updateCompanyDataEndpoint: String

    @Value("\${consumer-server.data.get-company-data}")
    lateinit var getCompanyDataEndpoint: String

    @Value("\${consumer-server.data.get-contact-person-by-company-id}")
    lateinit var getContactPersonByCompanyIdEndpoint: String

    
    override fun saveCompanyData(companyDataDto: Mono<CompanyDataDto>): Mono<CompanyDataDto> =
        webClient
            .post()
            .uri("$baseUrl$url$saveCompanyDataEndpoint")
            .body(companyDataDto)
            .retrieve()
            .bodyToMono(CompanyDataDto::class.java)

    override fun updateCompanyData(companyDataDto: Mono<CompanyDataDto>): Mono<CompanyDataDto> =
        webClient
            .put()
            .uri("$baseUrl$url$updateCompanyDataEndpoint")
            .body(companyDataDto)
            .retrieve()
            .bodyToMono(CompanyDataDto::class.java)

    override fun getCompanyDataByCompanyId(companyId: Long): Mono<CompanyDataDto> =
        webClient
            .get()
            .uri("$baseUrl$url$getCompanyDataEndpoint/$companyId")
            .retrieve()
            .bodyToMono(CompanyDataDto::class.java)

    override fun getContactPersonByCompanyId(companyId: Long): Mono<ContactPersonDto> =
        webClient
            .get()
            .uri("$baseUrl$url$getContactPersonByCompanyIdEndpoint/$companyId")
            .retrieve()
            .bodyToMono(ContactPersonDto::class.java)
}