package brentmaas.buildguide;

import org.lwjgl.glfw.GLFW;

import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.screen.ShapelistScreen;
import brentmaas.buildguide.screen.VisualisationScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class InputHandler {
	private static KeyBinding openBuildGuide;
	private static KeyBinding openShapelist;
	private static KeyBinding openVisualisation;
	
	public static void register() {
		openBuildGuide = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openbuildguide", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category"));
		openShapelist = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openshapelist", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category"));
		openVisualisation = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openvisualisation", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category"));
		
		ClientTickEvents.END_CLIENT_TICK.register(InputHandler::onKeyInput);
	}
	
	public static void onKeyInput(MinecraftClient client) {
		if(openBuildGuide.wasPressed()) {
			MinecraftClient.getInstance().setScreen(new BuildGuideScreen());
		}
		
		if(openShapelist.wasPressed() && StateManager.getState().propertyAdvancedMode.value) {
			MinecraftClient.getInstance().setScreen(new ShapelistScreen());
		}
		
		if(openVisualisation.wasPressed()) {
			MinecraftClient.getInstance().setScreen(new VisualisationScreen());
		}
	}
}
