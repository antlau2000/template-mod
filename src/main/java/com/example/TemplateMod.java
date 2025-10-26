package com.example;

import com.example.database.DatabaseManager;
import com.example.network.MessagePayload;
import com.example.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer {
	public static final String MOD_ID = "template-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Template Mod initialized");
		PayloadTypeRegistry.playC2S().register(MessagePayload.ID, MessagePayload.CODEC);
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
			initializeServer();
		}
	}

	@Environment(EnvType.SERVER)
	private void initializeServer() {
		try {
			DatabaseManager.initialize();
			NetworkHandler.registerServerReceivers();
			LOGGER.info("Database initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Database initialization failed", e);
		}
	}
}