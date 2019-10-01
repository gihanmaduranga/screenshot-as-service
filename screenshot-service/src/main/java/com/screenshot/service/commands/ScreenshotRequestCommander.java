

package com.screenshot.service.commands;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.screenshot.service.ScreenshotServiceApplication;
import com.screenshot.service.data.transfer.ScreenshotDAODTO;
import com.screenshot.service.data.transfer.ScreenshotDTO;

/**
 * 
 * GGamage
 *
 */
@Component
public class ScreenshotRequestCommander {
	Logger logger = LoggerFactory.getLogger(ScreenshotRequestCommander.class);
	final String BASE_URL = "http://SCREENSHOT-DATABASE-SERVICE/api/data/screenshot";
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	WebClient.Builder webClientBuilder;
	
	/**
	 * Publish messages into queue
	 * 
	 * @param request
	 * @return
	 */
	public ResponseEntity<Void> publishMessageIntoQueue(ScreenshotDTO request) {
		if (Objects.isNull(request))
			throw new IllegalArgumentException("request cannot be empty");
		logger.info("Request is accepted and publising messages into the queue...");
		jmsTemplate.convertAndSend(ScreenshotServiceApplication.JMS_MESSAGE_QUEUE_NAME, request);

		logger.info("Succesfully published the message into the queue");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Get screenshot by URL
	 * 
	 * @param url
	 * @return
	 */
	public ScreenshotDAODTO getScreenshotByUrl(String url) {
		if (Objects.isNull(url))
			throw new IllegalArgumentException("url cannot be empty");


		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
		webClientBuilder.uriBuilderFactory(factory).baseUrl(BASE_URL).build();

		ScreenshotDAODTO screenshotDAODTO = webClientBuilder.build().get()
				.uri(uriBuilder -> uriBuilder.queryParam("url", url).build()).retrieve()
				.bodyToMono(ScreenshotDAODTO.class).block();
		logger.info("Successfully get the screenshot from database service for URL -> " + url);


		return screenshotDAODTO;
	}

}


