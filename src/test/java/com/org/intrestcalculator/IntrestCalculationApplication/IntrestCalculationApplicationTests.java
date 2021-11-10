package com.org.intrestcalculator.IntrestCalculationApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.org.intrestcalculator.IntrestCalculationApplication.config.KafkaConfig;
import com.org.intrestcalculator.IntrestCalculationApplication.controller.IntrestCalculatorController;

@EnableKafka
@SpringBootTest(classes = {IntrestCalculatorController.class}) // Specify @KafkaListener class if its not the same class, or not loaded with test config
@EmbeddedKafka(
    partitions = 1, 
    controlledShutdown = false,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:3333", 
        "port=3333"
})
@Import({
	KafkaConfig.class
})
class IntrestCalculationApplicationTests {

@Autowired
private EmbeddedKafkaBroker kafkaEmbedded;


@Value("${topic.name}")
private String topicName;

@Autowired
private KafkaTemplate<String, Object> kafkaTemplate;

@Test
public void consume_success() {
	
}
}
