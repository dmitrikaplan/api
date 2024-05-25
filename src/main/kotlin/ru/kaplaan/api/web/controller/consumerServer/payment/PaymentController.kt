package ru.kaplaan.api.web.controller.consumerServer.payment

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
import ru.kaplaan.api.service.consumerServer.payment.PaymentService
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentInfoDto
import ru.kaplaan.api.web.dto.consumerServer.payment.PaymentOrderDto
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@RestController
@RequestMapping("/api/v1/consumer/payment")
@Tag(name = "Payment Controller", description = "Контроллер взаимодействия с данными для оплаты")
class PaymentController(
    private val paymentService: PaymentService
) {

    @PostMapping("/info")
    @PreAuthorize("hasAuthority('CREATE_PAYMENT_INFO')")
    @Operation(summary = "Добавить платежную информацию компании")
    fun savePaymentInfo(
        @RequestBody @Validated(OnCreate::class)
        paymentInfoDto: Mono<PaymentInfoDto>,
        authentication: Authentication
    ): Mono<PaymentInfoDto> = paymentService.savePaymentInfo(
        paymentInfoDto.map {
            it.apply {
                this.companyId = (authentication.details as String).toLong()
            }
        }
    )

    @PutMapping("/info")
    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_INFO')")
    @Operation(summary = "Обновить платежную информацию компании")
    fun updatePaymentInfo(
        @RequestBody @Validated(OnUpdate::class)
        paymentInfoDto: Mono<PaymentInfoDto>,
        authentication: Authentication
    ): Mono<PaymentInfoDto> = paymentService.updatePaymentInfo(
        paymentInfoDto.map {
            it.apply {
                this.companyId = (authentication.details as String).toLong()
            }
        }
    )

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('GET_PAYMENT_INFO')")
    @Operation(summary = "Получить платежную информацию компании")
    fun getPaymentInfo(
        authentication: Authentication
    ): Mono<PaymentInfoDto> = paymentService.getPaymentInfoByCompanyId((authentication.details as String).toLong())

    @GetMapping("/order/{pageNumber}")
    @PreAuthorize("hasAuthority('GET_PAYMENT_ORDER_FOR_COMPANY')")
    @Operation(summary = "Получить платежные поручения для компании")
    fun getPaymentOrderByCompanyIdForCompany(
        @Parameter(description = "Номер страницы", required = true)
        @Validated @Min(0, message = "Минимальный номер страницы - 0!")
        @PathVariable pageNumber: Int,
        authentication: Authentication
    ): Flux<PaymentOrderDto> = paymentService.getPaymentOrdersByCompanyId((authentication.details as String).toLong(), pageNumber)

    @GetMapping("/order/{companyId}/{pageNumber}")
    @PreAuthorize("hasAuthority('GET_PAYMENT_ORDER_FOR_ADMIN')")
    @Operation(summary = "Получить платежные поручения по Id компании для бухгалтера")
    fun getPaymentOrderByCompanyIdForAdmin(
        @Parameter(description = "Id компании", required = true)
        @Validated @Min(0, message = "Минимальное Id компании - 0!")
        @PathVariable companyId: Long,
        @Parameter(description = "Номер страницы", required = true)
        @Validated @Min(0, message = "Минимальный номер страницы - 0!")
        @PathVariable pageNumber: Int
    ): Flux<PaymentOrderDto> = paymentService.getPaymentOrdersByCompanyId(companyId, pageNumber)


    @PutMapping("/order/{paymentOrderId}")
    @PreAuthorize("hasAuthority('UPDATE_PAYMENT_ORDER')")
    @Operation(summary = "Отметить, что платеж прошел для бухгалтера")
    fun markPaymentCompleted(
        @Validated @Min(0, message = "Минимальное id платежа - 0!")
        @PathVariable paymentOrderId: Long
    ): Mono<Any> = paymentService.setPaymentOrderCompleted(paymentOrderId)

}