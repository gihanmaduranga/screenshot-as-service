

package com.screenshot.database.service.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.screenshot.database.service.ScreenshotDatabaseServiceApplication;
import com.screenshot.database.service.data.transfer.ScreenshotDTO;
import com.screenshot.database.service.model.ScreenshotBO;

/**
 * 
 * GGamage
 *
 */
@Component
public class ScreenshotDAOCommander {
	Logger logger = LoggerFactory.getLogger(ScreenshotDAOCommander.class);
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private ScreenshotDBServiceImpl screenshotDBServiceImpl;

	/**
	 * Publish messages into DB queue
	 * 
	 * @param request
	 * @return
	 */
	public ResponseEntity<Void> publishMessageInToQueue(ScreenshotDTO request) {

		if (Objects.isNull(request))
			throw new IllegalArgumentException("request cannot be empty");
		logger.info("Request is accepted and publising messages into the DB queue...");
		jmsTemplate.convertAndSend(ScreenshotDatabaseServiceApplication.JMS_MESSAGE_QUEUE_NAME, request);

		logger.info("Succesfully published the message into the DB queue");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Get screenshotBO by URL
	 * 
	 * @param url
	 * @return
	 */
	public ScreenshotDTO getScreenshotByUrl(String url) {
		if (Objects.isNull(url))
			throw new IllegalArgumentException("url cannot be empty");

		List<ScreenshotBO> screenshotBOList = screenshotDBServiceImpl.getScreenshotByURL(url);
		Optional<ScreenshotBO> screenshotBOOpt = screenshotBOList.stream()
				.max(Comparator.comparing(ScreenshotBO::getTimestamp));
		if (screenshotBOOpt.isPresent()) {
			ScreenshotDTO screenshotDTO = new ScreenshotDTO();
			screenshotDTO.setUrl(screenshotBOOpt.get().getUrl());
			screenshotDTO.setData(screenshotBOOpt.get().getData());
			return screenshotDTO;
		}
		return null;

	}

}


