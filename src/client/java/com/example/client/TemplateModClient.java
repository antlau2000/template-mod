package com.example.client;

import com.example.gui.MessageScreen;
import com.example.network.MessagePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateModClient implements ClientModInitializer {
	public static final String MOD_ID = "template-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private final KeyBinding openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.template-mod.opengui",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_M,
			"category.template-mod"
	));

	@Override
	public void onInitializeClient() {
		LOGGER.info("Template Mod Client initialized");

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openGuiKey.wasPressed()) {
				LOGGER.info("M key pressed - setting screen");
				client.setScreen(new MessageScreen());
			}
		});
	}

	public static void sendMessageToServer(byte[] data) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.getNetworkHandler() == null) {
			LOGGER.error("Not connected to server");
			return;
		}

		try {
			MessagePayload payload = new MessagePayload(data);
			ClientPlayNetworking.send(payload);
			LOGGER.info("Message sent to server");
		} catch (Exception e) {
			LOGGER.error("Failed to send message to server", e);
		}
	}
}