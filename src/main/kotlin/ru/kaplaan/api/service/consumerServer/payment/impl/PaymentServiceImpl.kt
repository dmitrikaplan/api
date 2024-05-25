package ru.kaplaan.api.service.consumerServer.payment.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.kaplaan.api.service.consumerServer.payment.PaymentService
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentInfoDto
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentOrderDto

@Service
class PaymentServiceImpl(
    private val webClient: WebClient
): PaymentService {

    @Value("\${consumer-server.base-url}")
    private lateinit var baseUrl: String

    @Value("\${consumer-server.payment.url}")
    private lateinit var url: String

    @Value("\${consumer-server.payment.save-payment-info}")
    private lateinit var savePaymentInfoEndpoint: String

    @Value("\${consumer-server.payment.update-payment-info}")
    private lateinit var updatePaymentInfoEndpoint: String

    @Value("\${consumer-server.payment.get-payment-info-by-company-id}")
    private lateinit var getPaymentInfoByCompanyIdEndpoint: String

    @Value("\${consumer-server.payment.get-payment-orders-by-company-id}")
    private lateinit var getPaymentOrdersByCompanyId: String

    @Value("\${consumer-server.payment.set-payment-order-completed}")
    private lateinit var setPaymentOrderCompletedEndpoint: String

    override fun savePaymentInfo(paymentInfoDto: Mono<PaymentInfoDto>): Mono<PaymentInfoDto> =
        webClient
            .post()
            .uri("$baseUrl$url$savePaymentInfoEndpoint")
            .body(paymentInfoDto)
            .retrieve()
            .bodyToMono(PaymentInfoDto::class.java)

    override fun updatePaymentInfo(paymentInfoDto: Mono<PaymentInfoDto>): Mono<PaymentInfoDto> =
        webClient
            .put()
            .uri("$baseUrl$url$updatePaymentInfoEndpoint")
            .body(paymentInfoDto)
            .retrieve()
            .bodyToMono(PaymentInfoDto::class.java)

    override fun getPaymentInfoByCompanyId(companyId: Long): Mono<PaymentInfoDto> =
        webClient
            .get()
            .uri("$baseUrl$url$getPaymentInfoByCompanyIdEndpoint/$companyId")
            .retrieve()
            .bodyToMono(PaymentInfoDto::class.java)

    override fun getPaymentOrdersByCompanyId(companyId: Long, pageNumber: Int): Flux<PaymentOrderDto> =
        webClient
            .get()
            .uri("$baseUrl$url$getPaymentOrdersByCompanyId/$companyId/$pageNumber")
            .retrieve()
            .bodyToFlux(PaymentOrderDto::class.java)

    override fun setPaymentOrderCompleted(paymentOrderId: Long): Mono<Any> =
        webClient
            .put()
            .uri("$baseUrl$url$setPaymentOrderCompletedEndpoint/$paymentOrderId")
            .retrieve()
            .bodyToMono(Any::class.java)
}