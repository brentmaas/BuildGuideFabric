package brentmaas.buildguide.property;

import java.util.ArrayList;

import brentmaas.buildguide.screen.BuildGuideScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class Property<T> {
	protected int x, y;
	public T value;
	protected Text name;
	public ArrayList<AbstractButtonWidget> buttonList = new ArrayList<AbstractButtonWidget>();
	public ArrayList<TextFieldWidget> textFieldList = new ArrayList<TextFieldWidget>();
	
	public Property(int x, int y, T value, Text name, Runnable onUpdate){
		this.x = x;
		this.y = y;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(AbstractButtonWidget b: buttonList) {
			b.visible = true;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = true;
		}
	}
	
	public void onDeselectedInGUI() {
		for(AbstractButtonWidget b: buttonList) {
			b.visible = false;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = false;
		}
	}
	
	public void addToBuildGuideScreen(BuildGuideScreen screen) {
		for(AbstractButtonWidget b: buttonList) {
			screen.addButtonExternal(b);
		}
		for(TextFieldWidget tfw: textFieldList) {
			screen.addTextFieldExternal(tfw);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(Text name) {
		this.name = name;
	}
	
	public boolean mightNeedTextFields() {
		return textFieldList.size() == 0;
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, TextRenderer font) {
		for(TextFieldWidget tfw: textFieldList) {
			tfw.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}
	
	public abstract void addTextFields(TextRenderer fr);
}
