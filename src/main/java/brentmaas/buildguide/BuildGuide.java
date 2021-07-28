package brentmaas.buildguide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.shapes.ShapeEmpty;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class BuildGuide implements ClientModInitializer {
	public static final Logger logger = LogManager.getLogger();
	public static State state;
	private static KeyBinding openBuildGuide;
	
	@Override
	public void onInitializeClient() {
		state = new State();
		state.loadConfig();
		
		openBuildGuide = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openbuildguide", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category"));
		
		WorldRenderEvents.LAST.register(client -> {
			MinecraftClient.getInstance().getProfiler().push("buildguide");
			
			if(BuildGuide.state.basePos != null && !(State.getCurrentShape() instanceof ShapeEmpty)) {
				//https://github.com/JackFred2/WhereIsIt/blob/master/src/main/java/red/jackf/whereisit/client/RenderUtils.java
				RenderSystem.setShader(GameRenderer::getPositionColorShader);
				
				MatrixStack stack = client.matrixStack();
				stack.push();
				Vec3d projectedView = client.camera().getPos();
				stack.translate(-projectedView.x + BuildGuide.state.basePos.x, -projectedView.y + BuildGuide.state.basePos.y, -projectedView.z + BuildGuide.state.basePos.z);
				
				boolean toggleTexture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
				
				boolean hasDepthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
				boolean toggleDepthTest = BuildGuide.state.propertyDepthTest.value ^ hasDepthTest;
				
				boolean toggleDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
				
				boolean toggleBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
				
				if(toggleTexture) RenderSystem.disableTexture();
				if(toggleDepthTest && hasDepthTest) RenderSystem.disableDepthTest();
				else if(toggleDepthTest) RenderSystem.enableDepthTest();
				if(toggleDepthMask) RenderSystem.depthMask(false);
				RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
				RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
				if(toggleBlend) RenderSystem.enableBlend();
				
				State.getCurrentShape().render(stack.peek().getModel(), client.projectionMatrix());
				
				if(toggleBlend) RenderSystem.disableBlend();
				if(toggleDepthTest && hasDepthTest) RenderSystem.enableDepthTest();
				else if(toggleDepthTest) RenderSystem.disableDepthTest();
				if(toggleDepthMask) RenderSystem.depthMask(true);
				if(toggleTexture) RenderSystem.enableTexture();
				
				stack.pop();
			}
			
			MinecraftClient.getInstance().getProfiler().pop();
		});
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(openBuildGuide.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new BuildGuideScreen());
			}
		});
	}
}
