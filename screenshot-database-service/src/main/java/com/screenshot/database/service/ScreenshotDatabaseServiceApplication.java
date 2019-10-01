package com.screenshot.database.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@EnableEurekaClient
@SpringBootApplication
public class ScreenshotDatabaseServiceApplication {

	public static final String JMS_MESSAGE_QUEUE_NAME = "ScreenshotMessageDBQueue";

	public static void main(String[] args) {
		SpringApplication.run(ScreenshotDatabaseServiceApplication.class, args);
	}

	/**
	 * Serialize message content to JSON using TextMessage
	 * 
	 * @return MessageConverter
	 */
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
