

package com.screenshot.service.jms.consumer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.screenshot.service.ScreenshotServiceApplication;
import com.screenshot.service.data.model.Screenshot;
import com.screenshot.service.data.transfer.ScreenshotDAODTO;
import com.screenshot.service.data.transfer.ScreenshotDTO;

/**
 * 
 * GGamage
 * 
 * This class is responsible for consuming messages from JMS message queue and
 * process the request and store the data into data base.
 */

@Component
public class ScreenshotMessageConsumer {
	Logger logger = LoggerFactory.getLogger(ScreenshotMessageConsumer.class);
	
	@Autowired
	WebClient.Builder webClientBuilder;

	/**
	 * Consume messages from the
	 * {@link ScreenshotServiceApplication#JMS_MESSAGE_QUEUE_NAME}
	 * @param request
	 */
	@JmsListener(destination = ScreenshotServiceApplication.JMS_MESSAGE_QUEUE_NAME)
	public void receiveMessage(ScreenshotDTO request) {

		Screenshot screenshot = new WebScreenshotTaker();
		Map<String, byte[]> urlWithScreenshotDataMap = screenshot.getWebpageScreenshot(request.getUrls());
		urlWithScreenshotDataMap.forEach((k, v) -> {
			ScreenshotDAODTO screenshotPersistDTO = new ScreenshotDAODTO();
			screenshotPersistDTO.setUrl(k);
			screenshotPersistDTO.setData(v);
			webClientBuilder.build().post()
					.uri("http://SCREENSHOT-DATABASE-SERVICE/api/data/screenshot")
					.body(BodyInserters.fromObject(screenshotPersistDTO)).retrieve().bodyToMono(String.class).block();
			logger.info("Successfully pushed data into  database service");
		});

	}
}


