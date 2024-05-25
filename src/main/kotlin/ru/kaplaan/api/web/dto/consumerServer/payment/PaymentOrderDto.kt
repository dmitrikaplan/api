package ru.kaplaan.api.web.dto.consumerServer.payment

import jakarta.validation.constraints.*
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate
import java.time.LocalDate

data class PaymentOrderDto(
    @field:Pattern(regexp = "^\\d{10}\$", message = "ИНН плательщика должен иметь 10 цифр", groups = [OnCreate::class, OnUpdate::class])
    val payerInn: String,

    @field:Pattern(regexp = "^\\d{9}\$", message = "КПП плательщика должен иметь 9 цифр", groups = [OnCreate::class, OnUpdate::class])
    val payerKpp: String,

    @field:NotBlank(message = "Название компании плательщика не должно быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val payerCompanyName: String,

    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета плательщика должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val payerCompanyAccountNumber: String,

    @field:Pattern(regexp = "^\\d{9}\$", message = "БИК банка плательщика должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val payerBankBik: String,

    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета банка плательщика должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val payerBankAccountNumber: String,

    @field:NotBlank(message = "Название банка плательщика не должно быть пустым", groups = [OnCreate::class, OnUpdate::class])
    val payerBankName: String,

    @field:Min(0, message = "Минимальное Id компании - 0!", groups = [OnCreate::class, OnUpdate::class])
    val payerCompanyId: Long,

    @field:Pattern(regexp = "^\\d{10}\$", message = "ИНН получателя должен иметь 10 цифр", groups = [OnCreate::class, OnUpdate::class])
    val recipientInn: String,

    @field:Pattern(regexp = "^\\d{9}\$", message = "КПП получателя должен иметь 9 цифр", groups = [OnCreate::class, OnUpdate::class])
    val recipientKpp: String,

    @field:NotBlank(message = "Название компании получателя не должно быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val recipientCompanyName: String,

    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета получателя должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val recipientCompanyAccountNumber: String,

    @field:Pattern(regexp = "^\\d{9}\$", message = "БИК банка получателя должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val recipientBankBik: String,

    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета банка получателя должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val recipientBankAccountNumber: String,

    @field:NotBlank(message = "Название банка получателя не должно быть пустым", groups = [OnCreate::class, OnUpdate::class])
    val recipientBankName: String,

    @field:Past(message = "Дата должна быть в прошлом!", groups = [OnCreate::class, OnUpdate::class])
    val creationDate: LocalDate,

    val isCompleted: Boolean,

    @field:Min(0, message = "Сумма не может быть меньше 0!", groups = [OnCreate::class, OnUpdate::class])
    val sum: Long,

    @field:NotBlank(message = "Название платежа не может быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val purposeOfPayment: String
){

    @field:Null(message = "Id платежного поручения не должно быть заполнено!", groups = [OnCreate::class])
    @field:NotNull(message = "Id платежного поручения должно быть заполнено!", groups = [OnUpdate::class])
    var id: Long? = null
}