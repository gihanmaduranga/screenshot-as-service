

package com.screenshot.database.service.data.transfer;

import java.io.Serializable;

/**
 * 
 * GGamage
 *
 */
public class ScreenshotDTO implements Serializable{

	private static final long serialVersionUID = -9003636111262873172L;
	private String url;
	private byte[] data;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}


