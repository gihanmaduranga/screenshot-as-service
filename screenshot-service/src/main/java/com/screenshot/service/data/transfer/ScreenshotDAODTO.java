

package com.screenshot.service.data.transfer;

import java.io.Serializable;

/**
 * 
 * GGamage
 *
 */
public class ScreenshotDAODTO implements Serializable {

	private static final long serialVersionUID = -5076526252699321642L;
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


