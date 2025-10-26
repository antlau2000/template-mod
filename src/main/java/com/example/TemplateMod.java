package com.example;

import com.example.database.DatabaseManager;
import com.example.network.MessagePayload;
import com.example.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class TemplateMod implements ModInitializer {
	public static final String MOD_ID = "template-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Template Mod initialized");
		PayloadTypeRegistry.playC2S().register(MessagePayload.ID, MessagePayload.CODEC);
		NetworkHandler.registerServerReceivers();
		try {
			DatabaseManager.initialize();
			LOGGER.info("Database initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Database initialization failed", e);
		}

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			TemplateMod.LOGGER.info(">>> SERVER STOPPING - triggered by: " +
					Arrays.toString(Thread.currentThread().getStackTrace())); // Кто вызвал остановку
		});

		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			TemplateMod.LOGGER.info(">>> SERVER STOPPED");
		});
	}
}