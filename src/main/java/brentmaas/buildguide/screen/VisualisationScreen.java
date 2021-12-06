package brentmaas.buildguide.screen;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.Slider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class VisualisationScreen extends PropertyScreen{
	private String titleColours;
	private String titleShape;
	private String titleBasepos;
	
	private ButtonWidget buttonClose;
	private ButtonWidget buttonBack = new ButtonWidget(0, 0, 20, 20, new LiteralText("<-"), ButtonWidget -> MinecraftClient.getInstance().openScreen(new BuildGuideScreen()));
	private Slider sliderShapeR;
	private Slider sliderShapeG;
	private Slider sliderShapeB;
	private Slider sliderShapeA;
	private Slider sliderBaseposR;
	private Slider sliderBaseposG;
	private Slider sliderBaseposB;
	private Slider sliderBaseposA;
	private ButtonWidget buttonSetShape;
	private ButtonWidget buttonSetBasepos;
	private ButtonWidget buttonDefaultShape;
	private ButtonWidget buttonDefaultBasepos;
	
	public VisualisationScreen() {
		super(new TranslatableText("screen.buildguide.visualisation"));
	}
	
	@Override
	protected void init() {
		titleColours = new TranslatableText("screen.buildguide.colours").getString();
		titleShape = new TranslatableText("screen.buildguide.shape").getString();
		titleBasepos = new TranslatableText("screen.buildguide.basepos").getString();
		
		buttonClose = new ButtonWidget(this.width - 20, 0, 20, 20, new LiteralText("X"), button -> MinecraftClient.getInstance().openScreen(null));
		
		sliderShapeR = new Slider(0, 35, 100, 20, new LiteralText("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeR : 1.0);
		sliderShapeG = new Slider(0, 55, 100, 20, new LiteralText("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeG : 1.0);
		sliderShapeB = new Slider(0, 75, 100, 20, new LiteralText("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeB : 1.0);
		sliderShapeA = new Slider(0, 95, 100, 20, new LiteralText("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeA : 0.5);
		sliderBaseposR = new Slider(110, 35, 100, 20, new LiteralText("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposR : 1.0);
		sliderBaseposG = new Slider(110, 55, 100, 20, new LiteralText("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposG : 1.0);
		sliderBaseposB = new Slider(110, 75, 100, 20, new LiteralText("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposB : 1.0);
		sliderBaseposA = new Slider(110, 95, 100, 20, new LiteralText("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposA : 0.5);
		
		buttonSetShape = new ButtonWidget(0, 115, 100, 20, new TranslatableText("screen.buildguide.set"), ButtonWidget -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourShapeR = (float) sliderShapeR.getValue();
				StateManager.getState().getCurrentShape().colourShapeG = (float) sliderShapeG.getValue();
				StateManager.getState().getCurrentShape().colourShapeB = (float) sliderShapeB.getValue();
				StateManager.getState().getCurrentShape().colourShapeA = (float) sliderShapeA.getValue();
				
				StateManager.getState().updateCurrentShape();
			}
		});
		buttonSetBasepos = new ButtonWidget(110, 115, 100, 20, new TranslatableText("screen.buildguide.set"), ButtonWidget -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourBaseposR = (float) sliderBaseposR.getValue();
				StateManager.getState().getCurrentShape().colourBaseposG = (float) sliderBaseposG.getValue();
				StateManager.getState().getCurrentShape().colourBaseposB = (float) sliderBaseposB.getValue();
				StateManager.getState().getCurrentShape().colourBaseposA = (float) sliderBaseposA.getValue();
				
				StateManager.getState().updateCurrentShape();
			}
		});
		
		buttonDefaultShape = new ButtonWidget(0, 135, 100, 20, new TranslatableText("screen.buildguide.default"), ButtonWidget -> {
			sliderShapeR.setManualValue(1.0);
			sliderShapeG.setManualValue(1.0);
			sliderShapeB.setManualValue(1.0);
			sliderShapeA.setManualValue(0.5);
			sliderShapeR.updateSlider();
			sliderShapeG.updateSlider();
			sliderShapeB.updateSlider();
			sliderShapeA.updateSlider();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourShapeR = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeG = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeB = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeA = 0.5f;
				StateManager.getState().updateCurrentShape();
			}
		});
		buttonDefaultBasepos = new ButtonWidget(110, 135, 100, 20, new TranslatableText("screen.buildguide.default"), ButtonWidget -> {
			sliderBaseposR.setManualValue(1.0);
			sliderBaseposG.setManualValue(0.0);
			sliderBaseposB.setManualValue(0.0);
			sliderBaseposA.setManualValue(0.5);
			sliderBaseposR.updateSlider();
			sliderBaseposG.updateSlider();
			sliderBaseposB.updateSlider();
			sliderBaseposA.updateSlider();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourBaseposR = 1.0f;
				StateManager.getState().getCurrentShape().colourBaseposG = 0.0f;
				StateManager.getState().getCurrentShape().colourBaseposB = 0.0f;
				StateManager.getState().getCurrentShape().colourBaseposA = 0.5f;
				StateManager.getState().updateCurrentShape();
			}
		});
		
		if(!StateManager.getState().isShapeAvailable()) {
			sliderShapeR.active = false;
			sliderShapeG.active = false;
			sliderShapeB.active = false;
			sliderShapeA.active = false;
			sliderBaseposR.active = false;
			sliderBaseposG.active = false;
			sliderBaseposB.active = false;
			sliderBaseposA.active = false;
			buttonSetShape.active = false;
			buttonDefaultShape.active = false;
			buttonSetBasepos.active = false;
			buttonDefaultBasepos.active = false;
		}
		
		addButton(buttonClose);
		addButton(buttonBack);
		addButton(sliderShapeR);
		addButton(sliderShapeG);
		addButton(sliderShapeB);
		addButton(sliderShapeA);
		addButton(sliderBaseposR);
		addButton(sliderBaseposG);
		addButton(sliderBaseposB);
		addButton(sliderBaseposA);
		addButton(buttonSetShape);
		addButton(buttonSetBasepos);
		addButton(buttonDefaultShape);
		addButton(buttonDefaultBasepos);
		
		addProperty(StateManager.getState().propertyDepthTest);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleColours, (210 - textRenderer.getWidth(titleColours)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShape, (100 - textRenderer.getWidth(titleShape)) / 2, 25, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleBasepos, 120 + (100 - textRenderer.getWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
	}
}
