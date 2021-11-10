package com.org.intrestcalculator.IntrestCalculationApplication.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.org.intrestcalculator.IntrestCalculationApplication.model.AccountOpening;
import com.org.intrestcalculator.IntrestCalculationApplication.model.DailyBalance;
import com.org.intrestcalculator.IntrestCalculationApplication.model.MonthData;
import com.org.intrestcalculator.IntrestCalculationApplication.service.IntrestCalculatorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class IntrestCalculatorController {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final IntrestCalculatorService interestCalculationService;

	public IntrestCalculatorController(final KafkaTemplate<String, Object> kafkaTemplate,
			final IntrestCalculatorService interestCalculationService) {
		this.kafkaTemplate = kafkaTemplate;
		this.interestCalculationService = interestCalculationService;
	}

	@KafkaListener(topics = "openAccount", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
	public void ConsumeAccountOpening(AccountOpening accountOpening) {
		Mono.just(accountOpening).doOnNext(interestCalculationService::initiateAccount);
	}

	@KafkaListener(topics = "DailyAccountData", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
	public void processMessage(DailyBalance dailyBalance) {
		interestCalculationService.updateDailyBalance(dailyBalance);
	}

	@Scheduled(cron = "0 15 10 28-31 * ?")
	public void publishMessage() {
		LocalDate last = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		LocalDate first = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

		if (last.equals(LocalDate.now())) {
			Set<MonthData> monthDataSet = interestCalculationService.MonthEndData(first, last);
			Flux.fromIterable(monthDataSet).map(monthData -> kafkaTemplate.send("MonthEndData", monthData));
		}
	}
}
