package brentmaas.buildguide.screen;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class BuildGuideScreen extends PropertyScreen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private ButtonWidget buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private ButtonWidget buttonShapePrevious = new ButtonWidget(60, 25, 20, 20, new LiteralText("<-"), button -> updateShape(-1));
	private ButtonWidget buttonShapeNext = new ButtonWidget(140, 25, 20, 20, new LiteralText("->"), button -> updateShape(1));
	private ButtonWidget buttonShapelist = new ButtonWidget(140, 25, 20, 20, new LiteralText("..."), button -> MinecraftClient.getInstance().openScreen(new ShapelistScreen()));
	private ButtonWidget buttonBasepos = new ButtonWidget(185, 25, 120, 20, new TranslatableText("screen.buildguide.setbasepos"), button -> StateManager.getState().resetBasepos());
	private ButtonWidget buttonVisualisation = new ButtonWidget(0, 65, 160, 20, new TranslatableText("screen.buildguide.visualisation"), button -> MinecraftClient.getInstance().openScreen(new VisualisationScreen()));
	//It's better off as custom buttons instead of PropertyInt
	private ButtonWidget buttonBaseposXDecrease = new ButtonWidget(185, 45, 20, 20, new LiteralText("-"), button -> shiftBasePos(-1, 0, 0));
	private ButtonWidget buttonBaseposXIncrease = new ButtonWidget(285, 45, 20, 20, new LiteralText("+"), button -> shiftBasePos(1, 0, 0));
	private ButtonWidget buttonBaseposYDecrease = new ButtonWidget(185, 65, 20, 20, new LiteralText("-"), button -> shiftBasePos(0, -1, 0));
	private ButtonWidget buttonBaseposYIncrease = new ButtonWidget(285, 65, 20, 20, new LiteralText("+"), button -> shiftBasePos(0, 1, 0));
	private ButtonWidget buttonBaseposZDecrease = new ButtonWidget(185, 85, 20, 20, new LiteralText("-"), button -> shiftBasePos(0, 0, -1));
	private ButtonWidget buttonBaseposZIncrease = new ButtonWidget(285, 85, 20, 20, new LiteralText("+"), button -> shiftBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private ButtonWidget buttonSetX = new ButtonWidget(255, 45, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			StateManager.getState().setBaseposX(newval);
			textFieldX.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetY = new ButtonWidget(255, 65, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			StateManager.getState().setBaseposY(newval);
			textFieldY.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetZ = new ButtonWidget(255, 85, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			StateManager.getState().setBaseposZ(newval);
			textFieldZ.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setEditableColor(0xFF0000);
		}
	});
	
	public BuildGuideScreen() {
		super(new TranslatableText("screen.buildguide.title"));
	}
	
	@Override
	protected void init() {
		titleGlobalProperties = new TranslatableText("screen.buildguide.globalproperties").getString();
		titleShapeProperties = new TranslatableText("screen.buildguide.shapeproperties").getString();
		titleBasepos = new TranslatableText("screen.buildguide.basepos").getString();
		titleNumberOfBlocks = new TranslatableText("screen.buildguide.numberofblocks").getString();
		textShape = new TranslatableText("screen.buildguide.shape").getString();
		
		if(StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			StateManager.getState().resetBasepos();
			for(Shape shape: StateManager.getState().simpleModeShapes) {
				shape.update();
			}
			//Advanced mode shapes should be empty
		}
		
		buttonClose = new ButtonWidget(this.width - 20, 0, 20, 20, new LiteralText("X"), button -> MinecraftClient.getInstance().openScreen(null));
		
		if(!StateManager.getState().isShapeAvailable()) {
			buttonBasepos.active = false;
			buttonVisualisation.active = false;
			buttonBaseposXDecrease.active = false;
			buttonBaseposXIncrease.active = false;
			buttonBaseposYDecrease.active = false;
			buttonBaseposYIncrease.active = false;
			buttonBaseposZDecrease.active = false;
			buttonBaseposZIncrease.active = false;
			buttonSetX.active = false;
			buttonSetY.active = false;
			buttonSetZ.active = false;
		}
		
		addButton(buttonClose);
		if(!StateManager.getState().propertyAdvancedMode.value) {
			addButton(buttonShapePrevious);
			addButton(buttonShapeNext);
		}else {
			addButton(buttonShapelist);
		}
		addButton(buttonBasepos);
		addButton(buttonVisualisation);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		textFieldX = new TextFieldWidget(textRenderer, 205, 45, 50, 20, new LiteralText(""));
		textFieldX.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setEditableColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(textRenderer, 205, 65, 50, 20, new LiteralText(""));
		textFieldY.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setEditableColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(textRenderer, 205, 85, 50, 20, new LiteralText(""));
		textFieldZ.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setEditableColor(0xFFFFFF);
		children.add(textFieldZ);
		
		addProperty(StateManager.getState().propertyEnable);
		addProperty(StateManager.getState().propertyAdvancedMode);
		
		if(StateManager.getState().propertyAdvancedMode.value) {
			for(Shape s: StateManager.getState().advancedModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}else {
			for(Shape s: StateManager.getState().simpleModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}
		
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleGlobalProperties, (160 - textRenderer.getWidth(titleGlobalProperties)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShapeProperties, (160 - textRenderer.getWidth(titleShapeProperties)) / 2, 115, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleBasepos, 185 + (120 - textRenderer.getWidth(titleBasepos)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - textRenderer.getWidth(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		
		int n = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		String numberOfBlocks = "" + n;
		String numberOfStacks = "(" + (n / 64) + " x 64 + " + (n % 64) + ")";
		textRenderer.drawWithShadow(matrixStack, numberOfBlocks, 305 + (100 - textRenderer.getWidth(numberOfBlocks)) / 2, 30, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, numberOfStacks, 305 + (100 - textRenderer.getWidth(numberOfStacks)) / 2, 45, 0xFFFFFF);
		
		textRenderer.drawWithShadow(matrixStack, textShape, 5, 30, 0xFFFFFF);
		String shapeName = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getTranslatedName() : new TranslatableText("shape.buildguide.none").getString();
		textRenderer.drawWithShadow(matrixStack, shapeName, 80 + (60 - textRenderer.getWidth(shapeName)) / 2, 30, 0xFFFFFF);
		
		textRenderer.drawWithShadow(matrixStack, "X", 170, 50, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Y", 170, 70, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Z", 170, 90, 0xFFFFFF);
		
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateShape(int di) {
		StateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		StateManager.getState().iSimple = Math.floorMod(StateManager.getState().iSimple + di, StateManager.getState().simpleModeShapes.length);
		
		StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		StateManager.getState().shiftBasepos(dx, dy, dz);
		textFieldX.setText("" + (int) StateManager.getState().getCurrentShape().basePos.x);
		textFieldY.setText("" + (int) StateManager.getState().getCurrentShape().basePos.y);
		textFieldZ.setText("" + (int) StateManager.getState().getCurrentShape().basePos.z);
		textFieldX.setEditableColor(0xFFFFFF);
		textFieldY.setEditableColor(0xFFFFFF);
		textFieldZ.setEditableColor(0xFFFFFF);
	}
}
