


package com.screenshot.database.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.screenshot.database.service.model.ScreenshotBO;
/**
 * 
 * GGamage
 *
 */

@Repository
public interface ScreenshotRepository extends JpaRepository<ScreenshotBO, Long> {

}


