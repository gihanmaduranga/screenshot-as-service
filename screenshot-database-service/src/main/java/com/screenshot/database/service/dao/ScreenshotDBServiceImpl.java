

package com.screenshot.database.service.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.screenshot.database.service.model.ScreenshotBO;

/**
 * 
 * GGamage
 *
 */
@Service
public class ScreenshotDBServiceImpl implements ScreenshotDBService {

	@Autowired
	private ScreenshotRepository screenshotRepository;

	@Override
	public void saveScreenshot(ScreenshotBO screenshot) {
		if (Objects.isNull(screenshot))
			throw new IllegalArgumentException("screenshot cannot be empty");
		screenshotRepository.save(screenshot);
		
	}

	@Override
	public List<ScreenshotBO> getScreenshotByURL(String url) {
		if (Objects.isNull(url))
			throw new IllegalArgumentException("url cannot be empty");
		ScreenshotBO screenshotBO = new ScreenshotBO();
		screenshotBO.setUrl(url);
		Example<ScreenshotBO> findAllMatchingUrls = Example.of(screenshotBO);
		List<ScreenshotBO> screenshotBOList = screenshotRepository.findAll(findAllMatchingUrls);
		return screenshotBOList;
	}

	@Override
	public ScreenshotBO getScreenshotById(Long id) {
		if (Objects.isNull(id))
			throw new IllegalArgumentException("id cannot be empty");
		Optional<ScreenshotBO> screenshotOpt = screenshotRepository.findById(id);
		if (screenshotOpt.isPresent())
			return screenshotOpt.get();
		return null;
	}

}


