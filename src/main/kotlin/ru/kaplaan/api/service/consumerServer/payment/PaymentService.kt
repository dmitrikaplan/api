package ru.kaplaan.api.service.consumerServer.payment

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentInfoDto
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentOrderDto

@Service
interface PaymentService {

    fun savePaymentInfo(paymentInfoDto: Mono<PaymentInfoDto>): Mono<PaymentInfoDto>

    fun updatePaymentInfo(paymentInfoDto: Mono<PaymentInfoDto>): Mono<PaymentInfoDto>

    fun getPaymentInfoByCompanyId(companyId: Long): Mono<PaymentInfoDto>

    fun getPaymentOrdersByCompanyId(companyId: Long, pageNumber: Int): Flux<PaymentOrderDto>

    fun setPaymentOrderCompleted(paymentOrderId: Long): Mono<Any>
}