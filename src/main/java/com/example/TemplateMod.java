package com.example;

import com.example.database.DatabaseManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer {
	public static final String MOD_ID = "template-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		try {
			DatabaseManager.initialize();
			LOGGER.info("Database initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Database initialization failed", e);
		}
	}
}