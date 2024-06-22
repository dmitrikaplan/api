package ru.kaplaan.api.domain.config

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.atomic.AtomicLong

@Component
class VacancyPrometheusMetric(
    meterRegistry: MeterRegistry,
    private val webClient: WebClient
) {

    @Value("\${consumer-server.base-url}")
    lateinit var baseUrl: String

    @Value("\${consumer-server.metrics.url}")
    lateinit var url: String

    @Value("\${consumer-server.metrics.count-non-archive-vacancies}")
    lateinit var countNonArchiveVacanciesEndpoint: String

    @Value("\${consumer-server.metrics.count-archive-vacancies}")
    lateinit var countArchiveVacanciesEndpoint: String

    private val countNonArchiveVacancies: AtomicLong =
        meterRegistry.gauge("non-archive-vacancies", AtomicLong(0))!!

    private val countArchiveVacancies: AtomicLong =
        meterRegistry.gauge("archive-vacancies", AtomicLong(0))!!

    @Scheduled(fixedDelay = 5000L, initialDelay = 0)
    fun scheduleCountNonArchiveVacancies(){
        webClient
            .get()
            .uri("$baseUrl$url$countNonArchiveVacanciesEndpoint")
            .retrieve()
            .bodyToMono(Long::class.java)
            .doOnNext {
                countNonArchiveVacancies.set(it)
            }
            .subscribe()
    }


    @Scheduled(fixedDelay = 5000L, initialDelay = 0)
    fun scheduleCountArchiveVacancies(){
        webClient
            .get()
            .uri("$baseUrl$url$countArchiveVacanciesEndpoint")
            .retrieve()
            .bodyToMono(Long::class.java)
            .doOnNext {
                countArchiveVacancies.set(it)
            }
            .subscribe()
    }
}