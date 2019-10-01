

package com.screenshot.database.service.dao;

import java.util.List;

import com.screenshot.database.service.model.ScreenshotBO;

/**
 * 
 * GGamage
 *
 */
public interface ScreenshotDBService {

	/**
	 * Persist {@link ScreenshotBO} into the database
	 * 
	 * @param screenshot
	 */
	void saveScreenshot(ScreenshotBO screenshot);


	/**
	 * Get {@link ScreenshotBO} by url
	 * 
	 * @param url
	 * @return
	 */
	List<ScreenshotBO> getScreenshotByURL(String url);

	/**
	 * Get {@link ScreenshotBO} by id
	 * 
	 * @param id
	 * @return
	 */
	ScreenshotBO getScreenshotById(Long id);
}


