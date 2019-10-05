


package com.screenshot.database.service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.screenshot.database.service.ScreenshotDatabaseServiceApplication;
import com.screenshot.database.service.dao.ScreenshotDBServiceImpl;
import com.screenshot.database.service.data.transfer.ScreenshotDTO;
import com.screenshot.database.service.model.ScreenshotBO;


/**
 * 
 * GGamage
 *
 */
@Component
public class ScreenshotDBMessageConsumer {
	Logger logger = LoggerFactory.getLogger(ScreenshotDBMessageConsumer.class);

	@Autowired
	private ScreenshotDBServiceImpl screenshotDBServiceImpl;

	/**
	 * Consume messages from the
	 * {@link ScreenshotDatabaseServiceApplication#JMS_MESSAGE_QUEUE_NAME}
	 * 
	 * @param request
	 */
	@JmsListener(destination = ScreenshotDatabaseServiceApplication.JMS_MESSAGE_QUEUE_NAME)
	public void receiveMessage(ScreenshotDTO request) {


			ScreenshotBO screenshot = new ScreenshotBO();
		screenshot.setUrl(request.getUrl());
		screenshot.setData(request.getData());
			screenshotDBServiceImpl.saveScreenshot(screenshot);
			logger.info("Successfully saved screenshot");


	}
}


