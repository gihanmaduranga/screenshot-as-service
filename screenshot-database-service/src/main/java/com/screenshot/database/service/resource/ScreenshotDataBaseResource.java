


package com.screenshot.database.service.resource;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.screenshot.database.service.dao.ScreenshotDAOCommander;
import com.screenshot.database.service.data.transfer.ScreenshotDTO;

/**
 * 
 * GGamage
 *
 */

@RestController
@RequestMapping("/api/data/screenshot")
public class ScreenshotDataBaseResource {

	@Autowired
	private ScreenshotDAOCommander screenshotDAOCommander;

	@PostMapping()
	public ResponseEntity<Void> publishMessageIntoDBMessageQueue(@RequestBody ScreenshotDTO request) {

		if (Objects.isNull(request))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		return screenshotDAOCommander.publishMessageInToQueue(request);

	}

	@GetMapping()
	public ScreenshotDTO getScreenshotByUrl(@RequestParam("url") String url) {
		return screenshotDAOCommander.getScreenshotByUrl(url);
	}
}


