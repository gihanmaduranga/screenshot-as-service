package com.screenshot.service;

import java.awt.AWTException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 
 * @author GGamage
 *
 */

@EnableJms
@EnableEurekaClient
@SpringBootApplication
public class ScreenshotServiceApplication {

	public static final String JMS_MESSAGE_QUEUE_NAME = "ScreenshotMessageQueue";

	public static void main(String[] args) throws AWTException, IOException {
		SpringApplication.run(ScreenshotServiceApplication.class, args);


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

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

}
