


package com.screenshot.service.data.model;

import java.util.Map;
import java.util.Set;

/**
 * 
 * GGamage
 * 
 * This interface define the contract for screenshot which can be
 * web,mobile,desktop in the future.
 */
public interface Screenshot {

	/**
	 * Get a screenshot of a given URL
	 * 
	 * @param url
	 * @return byte[]
	 */
	Map<String, byte[]> getWebpageScreenshot(Set<String> urlList);
}


