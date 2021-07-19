package brentmaas.buildguide.property;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class PropertyBoolean extends Property<Boolean>{
	private CheckboxWidget button;
	
	public PropertyBoolean(int x, int y, Boolean value, Text name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate);
		button = new CheckboxWidget(x + 140, y, 20, 20, new LiteralText(""), value, false); //Definitely not this value so the UI lines up nicely
		buttonList.add(button);
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, TextRenderer font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		value = button.isChecked();
		font.drawWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void addTextFields(TextRenderer fr) {
		
	}
}
