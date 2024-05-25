package ru.kaplaan.api.web.dto.consumerServer.payment

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.Pattern
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@Schema(description = "Сущность платежной информации о компании")
data class PaymentInfoDto(
    @Schema(description = "ИНН компании")
    @field:Pattern(regexp = "^\\d{10}\$", message = "ИНН должен иметь 10 цифр", groups = [OnCreate::class, OnUpdate::class])
    val inn: String,

    @Schema(description = "КПП компании")
    @field:Pattern(regexp = "^\\d{9}\$", message = "КПП должен иметь 9 цифр", groups = [OnCreate::class, OnUpdate::class])
    val kpp: String,

    @Schema(description = "Название компании для платежного поручения")
    @field:NotBlank(message = "Название компании не должно быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val companyName: String,

    @Schema(description = "Номер счета компании")
    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета плательщика должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val companyAccountNumber: String,

    @Schema(description = "БИК банка")
    @field:Pattern(regexp = "^\\d{9}\$", message = "БИК банка должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val bankBik: String,

    @Schema(description = "Номер счета банка")
    @field:Pattern(regexp = "^\\d{20}\$", message = "Номер счета банка должен иметь 20 цифр", groups = [OnCreate::class, OnUpdate::class])
    val bankAccountNumber: String,

    @Schema(description = "Название банка для платежного поручения")
    @field:NotBlank(message = "Название банка не должно быть пустым", groups = [OnCreate::class, OnUpdate::class])
    val bankName: String,

){
    @Schema(description = "Id компании. Заполняется при обновлении")
    @field:Null(message = "Id информации о плательщике не должна быть заполнена!", groups = [OnCreate::class])
    @field:NotNull(message = "Id информации о плательщике должна быть заполнена!", groups = [OnUpdate::class])
    var id: Long? = null

    @Schema(description = "Id компании", hidden = true)
    @field:Null(message = "Id компании не должно быть заполнено!", groups = [OnCreate::class, OnUpdate::class])
    var companyId: Long? = null
}