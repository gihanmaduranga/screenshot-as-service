/*
 * ***************************************************************
         *                                                             *                          
         *                           NOTICE                            *
         *                                                             *
         *   THIS SOFTWARE IS THE PROPERTY OF AND CONTAINS             *
         *   CONFIDENTIAL INFORMATION OF INFOR AND/OR ITS AFFILIATES   *
         *   OR SUBSIDIARIES AND SHALL NOT BE DISCLOSED WITHOUT PRIOR  *
         *   WRITTEN PERMISSION. LICENSED CUSTOMERS MAY COPY AND       *
         *   ADAPT THIS SOFTWARE FOR THEIR OWN USE IN ACCORDANCE WITH  *
         *   THE TERMS OF THEIR SOFTWARE LICENSE AGREEMENT.            *
         *   ALL OTHER RIGHTS RESERVED.                                *
         *                                                             *
         *   (c) COPYRIGHT 2019 INFOR.  ALL RIGHTS RESERVED.           *
         *   THE WORD AND DESIGN MARKS SET FORTH HEREIN ARE            *
         *   TRADEMARKS AND/OR REGISTERED TRADEMARKS OF INFOR          *
         *   AND/OR ITS AFFILIATES AND SUBSIDIARIES. ALL RIGHTS        *
         *   RESERVED.  ALL OTHER TRADEMARKS LISTED HEREIN ARE         *
         *   THE PROPERTY OF THEIR RESPECTIVE OWNERS.                  *
         *                                                             *
         ***********************************************************************

 */


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


