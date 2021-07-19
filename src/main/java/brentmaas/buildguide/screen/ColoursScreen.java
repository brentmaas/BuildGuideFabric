package brentmaas.buildguide.screen;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ColoursScreen extends Screen{
	private String titleShape;
	private String titleBasepos;
	
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
	
	public ColoursScreen() {
		super(new TranslatableText("screen.buildguide.colours"));
	}
	
	@Override
	protected void init() {
		titleShape = new TranslatableText("screen.buildguide.shape").getString();
		titleBasepos = new TranslatableText("screen.buildguide.basepos").getString();
		
		sliderShapeR = new Slider(0, 40, 100, 20, new LiteralText("R"), 0.0, 1.0, BuildGuide.state.colourShapeR);
		sliderShapeG = new Slider(0, 60, 100, 20, new LiteralText("G"), 0.0, 1.0, BuildGuide.state.colourShapeG);
		sliderShapeB = new Slider(0, 80, 100, 20, new LiteralText("B"), 0.0, 1.0, BuildGuide.state.colourShapeB);
		sliderShapeA = new Slider(0, 100, 100, 20, new LiteralText("A"), 0.0, 1.0, BuildGuide.state.colourShapeA);
		sliderBaseposR = new Slider(120, 40, 100, 20, new LiteralText("R"), 0.0, 1.0, BuildGuide.state.colourBaseposR);
		sliderBaseposG = new Slider(120, 60, 100, 20, new LiteralText("G"), 0.0, 1.0, BuildGuide.state.colourBaseposG);
		sliderBaseposB = new Slider(120, 80, 100, 20, new LiteralText("B"), 0.0, 1.0, BuildGuide.state.colourBaseposB);
		sliderBaseposA = new Slider(120, 100, 100, 20, new LiteralText("A"), 0.0, 1.0, BuildGuide.state.colourBaseposA);
		
		buttonSetShape = new ButtonWidget(0, 120, 100, 20, new TranslatableText("screen.buildguide.set"), ButtonWidget -> State.updateCurrentShape());
		buttonSetBasepos = new ButtonWidget(120, 120, 100, 20, new TranslatableText("screen.buildguide.set"), ButtonWidget -> State.updateCurrentShape());
		
		buttonDefaultShape = new ButtonWidget(0, 140, 100, 20, new TranslatableText("screen.buildguide.default"), ButtonWidget -> {
			sliderShapeR.setManualValue(1.0);
			sliderShapeG.setManualValue(1.0);
			sliderShapeB.setManualValue(1.0);
			sliderShapeA.setManualValue(0.5);
			sliderShapeR.updateSlider();
			sliderShapeG.updateSlider();
			sliderShapeB.updateSlider();
			sliderShapeA.updateSlider();
			BuildGuide.state.colourShapeR = 1.0f;
			BuildGuide.state.colourShapeG = 1.0f;
			BuildGuide.state.colourShapeB = 1.0f;
			BuildGuide.state.colourShapeA = 0.5f;
			State.updateCurrentShape();
		});
		buttonDefaultBasepos = new ButtonWidget(120, 140, 100, 20, new TranslatableText("screen.buildguide.default"), ButtonWidget -> {
			sliderBaseposR.setManualValue(1.0);
			sliderBaseposG.setManualValue(0.0);
			sliderBaseposB.setManualValue(0.0);
			sliderBaseposA.setManualValue(0.5);
			sliderBaseposR.updateSlider();
			sliderBaseposG.updateSlider();
			sliderBaseposB.updateSlider();
			sliderBaseposA.updateSlider();
			BuildGuide.state.colourBaseposR = 1.0f;
			BuildGuide.state.colourBaseposG = 0.0f;
			BuildGuide.state.colourBaseposB = 0.0f;
			BuildGuide.state.colourBaseposA = 0.5f;
			State.updateCurrentShape();
		});
		
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
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShape, (100 - textRenderer.getWidth(titleShape)) / 2, 25, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleBasepos, 120 + (100 - textRenderer.getWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
		
		BuildGuide.state.colourShapeR = (float) sliderShapeR.getValue();
		BuildGuide.state.colourShapeG = (float) sliderShapeG.getValue();
		BuildGuide.state.colourShapeB = (float) sliderShapeB.getValue();
		BuildGuide.state.colourShapeA = (float) sliderShapeA.getValue();
		BuildGuide.state.colourBaseposR = (float) sliderBaseposR.getValue();
		BuildGuide.state.colourBaseposG = (float) sliderBaseposG.getValue();
		BuildGuide.state.colourBaseposB = (float) sliderBaseposB.getValue();
		BuildGuide.state.colourBaseposA = (float) sliderBaseposA.getValue();
	}
	
	@Override
	public void onClose() {
		BuildGuide.state.colourShapeR = (float) sliderShapeR.getValue();
		BuildGuide.state.colourShapeG = (float) sliderShapeG.getValue();
		BuildGuide.state.colourShapeB = (float) sliderShapeB.getValue();
		BuildGuide.state.colourShapeA = (float) sliderShapeA.getValue();
		BuildGuide.state.colourBaseposR = (float) sliderBaseposR.getValue();
		BuildGuide.state.colourBaseposG = (float) sliderBaseposG.getValue();
		BuildGuide.state.colourBaseposB = (float) sliderBaseposB.getValue();
		BuildGuide.state.colourBaseposA = (float) sliderBaseposA.getValue();
		
		super.onClose();
	}
	
	class Slider extends SliderWidget {
		private double min, max;
		private String prefix;
		
		public Slider(int x, int y, int width, int height, Text name, double min, double max, double value) {
			super(x, y, width, height, new LiteralText(name.getString() + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
			this.min = min;
			this.max = max;
			prefix = name.getString() + ": ";
		}
		
		protected void updateMessage() {
			setMessage(new LiteralText(prefix + Math.round(10.0 * getValue()) / 10.0));
		}
		
		protected void applyValue() {
			
		}
		
		public void updateSlider() {
			updateMessage();
		}
		
		public void setManualValue(double value) {
			this.value = (value - min) / (max - min);
		}
		
		public double getValue() {
			return value * (max - min) + min;
		}
	}
}
