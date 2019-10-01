
package com.screenshot.service.resource;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.screenshot.service.commands.ScreenshotRequestCommander;
import com.screenshot.service.data.transfer.ScreenshotDAODTO;
import com.screenshot.service.data.transfer.ScreenshotDTO;

/**
 * 
 * GGamage
 *
 */

@RestController
@RequestMapping("/api/screenshot")
public class ScreenshotResource {

	final static String IMAGE_EXTENSION = ".png";
	@Autowired
	private ScreenshotRequestCommander screenshotCommander;

	@PostMapping()
	public ResponseEntity<Void> publishMessageIntoMessageQueue(@RequestBody ScreenshotDTO request) {

		if (Objects.isNull(request))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		return screenshotCommander.publishMessageIntoQueue(request);

	}

	@GetMapping()
	public HttpEntity<byte[]> getScreenshotByUrl(@RequestParam("url") String url, HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		response.setHeader("Content-Disposition",
				"attachment; filename=" + UUID.randomUUID().toString().concat(IMAGE_EXTENSION));
		ScreenshotDAODTO screenshotDAODTO = screenshotCommander.getScreenshotByUrl(url);
		if (Objects.isNull(screenshotDAODTO))
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		headers.setContentLength(screenshotDAODTO.getData().length);
		return new HttpEntity<byte[]>(screenshotDAODTO.getData(), headers);
	}
}


