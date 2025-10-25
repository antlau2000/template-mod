package com.example.client;

import com.example.gui.MessageScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
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
}