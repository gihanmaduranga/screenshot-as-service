


package com.screenshot.service.jms.consumer;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.validator.UrlValidator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.screenshot.service.data.model.Screenshot;


/**
 * 
 * GGamage
 *
 * ScreenshotImpl for web base applications
 */
public class WebScreenshotTaker implements Screenshot {

	static Logger logger = LoggerFactory.getLogger(WebScreenshotTaker.class);

	@Override
	public Map<String, byte[]> getWebpageScreenshot(Set<String> urlList) {
		
		Map<String, byte[]> urlWithScreenshotDataMap = new HashMap<>();

		URL driverPath = WebScreenshotTaker.class.getResource("/drivers/windows/chromedriver.exe");

		if (OSUtils.isWindows())
			driverPath = WebScreenshotTaker.class.getResource("/drivers/windows/chromedriver.exe");

		if (OSUtils.isMac())
			driverPath = WebScreenshotTaker.class.getResource("/drivers/mac/chromedriver");

		if (OSUtils.isUnix())
			driverPath = WebScreenshotTaker.class.getResource("/drivers/linux/chromedriver");
        
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("");
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--window-size=1300,1000");
		
		ChromeDriverService service;
		try {
			service = new ChromeDriverService.Builder()
					.usingDriverExecutable(DriverUtils.makeFileExecutable(driverPath,
					File.createTempFile("temp", Long.toString(System.nanoTime())))).usingAnyFreePort().build();
			WebDriver driver = new ChromeDriver(service, chromeOptions);
			urlList.forEach(url -> {
				
				if (isValidURL(url)) {

					driver.get(url);
					TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
					byte[] screenshotData = takesScreenshot.getScreenshotAs(OutputType.BYTES);
					urlWithScreenshotDataMap.put(url, screenshotData);
					logger.info("Captured screenshot of -> " + url);


				} else {
					logger.warn(url + "-> is not a valid URL");
				}
				

			});
			driver.quit();
		} catch (URISyntaxException | IOException e) {
			logger.error("Error in initializing the driver", e);
		}

		return urlWithScreenshotDataMap;
}

	/**
	 * Check the validity of the URL
	 * 
	 * @param url
	 * @return
	 */
	private boolean isValidURL(String url) {

		if (Objects.isNull(url))
			throw new IllegalArgumentException("Url cannot be empty ");

		return new UrlValidator().isValid(url);
	}

	/**
	 * Utility class for detect the running OS
	 *
	 */
	public static class OSUtils {
		static final String os = SystemUtils.OS_NAME;

		public static boolean isWindows() {

			return (os.indexOf("win") >= 0);

		}

		public static boolean isMac() {

			return (os.indexOf("mac") >= 0);

		}

		public static boolean isUnix() {

			return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0);

		}
	}

	public static class DriverUtils {
		/**
		 * Extract the chrome driver into temp location for execution
		 *
		 * @param source   the location
		 * @param detination the destination
		 * @return the file
		 * @throws URISyntaxException
		 */
		public static File makeFileExecutable(URL source, File destination) throws URISyntaxException {
			File exeFile = destination;
			try {

				FileUtils.copyInputStreamToFile(source.openStream(), exeFile);

				forceExecutable(exeFile);
				logger.info("Temp Dir  " + exeFile.toString());

			} catch (Exception e) {
				logger.error("Failed to extract file executable From:(" + source + ") To:" + destination, e);
			}

			return exeFile;
		}

		/**
		 * Force to executable file.
		 *
		 * @param exeFile
		 * @return the executable file
		 */
		public static File forceExecutable(File exeFile) {
			if (!exeFile.canExecute()) {
				exeFile.setExecutable(true);
			}
			return exeFile;
		}
	}
}

