package ru.kaplaan.api.web.dto.consumerServer.vacancy

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import ru.kaplaan.api.web.validation.OnCreate
import ru.kaplaan.api.web.validation.OnUpdate

@Schema(description = "Сущность вакансии")
data class VacancyDto(

    @Schema(description = "Описание вакансии", example = "Java-разработчик")
    @field:NotBlank(message = "Заголовок вакансии не должен быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val title: String,

    @Schema(description = "Зарплатная вилка")
    val salaryRange: IntRange?,

    @Schema(description = "Валюта, в которой оплачивают зарплату", example = "RUBLES")
    val currency: Currency,

    @Schema(description = "Адрес компании", example = "Улица Пушкина, 1")
    @field:NotBlank(message = "Адрес не должен быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val address: String,

    @Schema(description = "Описание вакансии", example = "Ожидаемый опыт коммерческой разработки - 100 лет на позицию Junior")
    @field:NotBlank(message = "Описание не должно быть пустым!", groups = [OnCreate::class, OnUpdate::class])
    val description: String,

    @Schema(description = "Список хэш-тегов")
    val hashTags: List<String>,
){
    @Schema(description = "Id компании", hidden = true)
    @field:Null(message = "Id компании не должно быть заполнено!", groups = [OnCreate::class, OnUpdate::class])
    var companyId: Long? = null

    @Schema(description = "Id вакансии. Заполняется только при обновлении", example = "1", hidden = true)
    @field:Null(message = "Id вакансии не должен быть заполнен!", groups = [OnCreate::class])
    @field:NotNull(message = "Id вакансии должен быть заполнен!", groups = [OnUpdate::class])
    @field:Min(0, message = "Минимальное значение id - 0", groups = [OnUpdate::class])
    var id: Long? = null

    @Schema(description = "Архивировать ли вакансию. Необязательное поле")
    val isArchived: Boolean = false
}