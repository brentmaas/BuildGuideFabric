package brentmaas.buildguide.screen;

import java.util.ArrayList;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

public class BuildGuideScreen extends Screen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private ButtonWidget buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private ButtonWidget buttonShapePrevious = new ButtonWidget(60, 40, 20, 20, new LiteralText("<-"), button -> updateShape(-1));
	private ButtonWidget buttonShapeNext = new ButtonWidget(140, 40, 20, 20, new LiteralText("->"), button -> updateShape(1));
	private ButtonWidget buttonBasepos = new ButtonWidget(0, 60, 160, 20, new TranslatableText("screen.buildguide.setbasepos"), button -> setBasePos());
	private ButtonWidget buttonColours = new ButtonWidget(0, 100, 160, 20, new TranslatableText("screen.buildguide.colours"), button -> MinecraftClient.getInstance().openScreen(new ColoursScreen()));
	//It's better off as custom buttons instead of PropertyInt
	private ButtonWidget buttonBaseposXDecrease = new ButtonWidget(200, 40, 20, 20, new LiteralText("-"), button -> shiftBasePos(-1, 0, 0));
	private ButtonWidget buttonBaseposXIncrease = new ButtonWidget(300, 40, 20, 20, new LiteralText("+"), button -> shiftBasePos(1, 0, 0));
	private ButtonWidget buttonBaseposYDecrease = new ButtonWidget(200, 60, 20, 20, new LiteralText("-"), button -> shiftBasePos(0, -1, 0));
	private ButtonWidget buttonBaseposYIncrease = new ButtonWidget(300, 60, 20, 20, new LiteralText("+"), button -> shiftBasePos(0, 1, 0));
	private ButtonWidget buttonBaseposZDecrease = new ButtonWidget(200, 80, 20, 20, new LiteralText("-"), button -> shiftBasePos(0, 0, -1));
	private ButtonWidget buttonBaseposZIncrease = new ButtonWidget(300, 80, 20, 20, new LiteralText("+"), button -> shiftBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private ButtonWidget buttonSetX = new ButtonWidget(270, 40, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			BuildGuide.state.basePos = new Vector3d(newval, BuildGuide.state.basePos.y, BuildGuide.state.basePos.z);
			textFieldX.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetY = new ButtonWidget(270, 60, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, newval, BuildGuide.state.basePos.z);
			textFieldY.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetZ = new ButtonWidget(270, 80, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, BuildGuide.state.basePos.y, newval);
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
		
		if(BuildGuide.state.basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			setBasePos();
			for(Shape shape: BuildGuide.state.shapeStore) shape.update();
		}
		
		buttonClose = new ButtonWidget(this.width - 20, 0, 20, 20, new LiteralText("X"), button -> MinecraftClient.getInstance().openScreen(null));
		
		addButton(buttonClose);
		addButton(buttonShapePrevious);
		addButton(buttonShapeNext);
		addButton(buttonBasepos);
		addButton(buttonColours);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		
		textFieldX = new TextFieldWidget(textRenderer, 220, 40, 50, 20, new LiteralText(""));
		textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
		textFieldX.setEditableColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(textRenderer, 220, 60, 50, 20, new LiteralText(""));
		textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
		textFieldY.setEditableColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(textRenderer, 220, 80, 50, 20, new LiteralText(""));
		textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
		textFieldZ.setEditableColor(0xFFFFFF);
		children.add(textFieldZ);
		
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		properties.add(BuildGuide.state.propertyDepthTest);
		
		for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}
		for(Shape s: BuildGuide.state.shapeStore) {
			s.onDeselectedInGUI();
			for(Property<?> p: s.properties) {
				if(p.mightNeedTextFields()) p.addTextFields(textRenderer);
				p.addToBuildGuideScreen(this);
			}
		}
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleGlobalProperties, (160 - textRenderer.getWidth(titleGlobalProperties)) / 2, 25, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShapeProperties, (160 - textRenderer.getWidth(titleShapeProperties)) / 2, 130, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleBasepos, 160 + (160 - textRenderer.getWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleNumberOfBlocks, 340 + (100 - textRenderer.getWidth(titleNumberOfBlocks)) / 2, 25, 0xFFFFFF);
		String numberOfBlocks = "" + State.getCurrentShape().getNumberOfBlocks();
		String numberOfStacks = "(" + (State.getCurrentShape().getNumberOfBlocks() / 64) + " x 64 + " + (State.getCurrentShape().getNumberOfBlocks() % 64) + ")";
		textRenderer.drawWithShadow(matrixStack, numberOfBlocks, 340 + (100 - textRenderer.getWidth(numberOfBlocks)) / 2, 45, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, numberOfStacks, 340 + (100 - textRenderer.getWidth(numberOfStacks)) / 2, 65, 0xFFFFFF);
		
		textRenderer.drawWithShadow(matrixStack, textShape, 5, 45, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, State.getCurrentShape().getTranslatedName(), 80 + (60 - textRenderer.getWidth(State.getCurrentShape().getTranslatedName())) / 2, 45, 0xFFFFFF);
		
		textRenderer.drawWithShadow(matrixStack, "X", 185, 45, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Y", 185, 65, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Z", 185, 85, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
		
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, textRenderer);
		}
		for(Property<?> p: State.getCurrentShape().properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, textRenderer);
		}
	}
	
	private void updateShape(int di) {
		State.getCurrentShape().onDeselectedInGUI();
		
		if(BuildGuide.state.basePos == null) setBasePos();
		
		BuildGuide.state.i_shape = Math.floorMod(BuildGuide.state.i_shape + di, BuildGuide.state.shapeStore.length);
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasePos() {
		Vec3d pos = MinecraftClient.getInstance().player.getPos();
		BuildGuide.state.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		if(textFieldX != null) {
			textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
			textFieldX.setEditableColor(0xFFFFFF);
		}
		if(textFieldY != null) {
			textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
			textFieldY.setEditableColor(0xFFFFFF);
		}
		if(textFieldZ != null) {
			textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
			textFieldZ.setEditableColor(0xFFFFFF);
		}
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x + dx, BuildGuide.state.basePos.y + dy, BuildGuide.state.basePos.z + dz);
		textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
		textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
		textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
		textFieldX.setEditableColor(0xFFFFFF);
		textFieldY.setEditableColor(0xFFFFFF);
		textFieldZ.setEditableColor(0xFFFFFF);
	}
	
	public void addButtonExternal(AbstractButtonWidget button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
