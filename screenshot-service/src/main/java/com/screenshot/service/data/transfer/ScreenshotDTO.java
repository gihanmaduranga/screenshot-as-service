


package com.screenshot.service.data.transfer;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * GGamage
 *
 */
public class ScreenshotDTO implements Serializable{

	private static final long serialVersionUID = 3109590157208678304L;

	private Set<String> urls;

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

}


