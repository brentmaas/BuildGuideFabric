package brentmaas.buildguide.property;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class PropertyInt extends Property<Integer>{
	private TextFieldWidget valueWidget;
	
	public PropertyInt(int x, int y, int value, Text name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate);
		buttonList.add(new ButtonWidget(x + 90, y, 20, 20, new LiteralText("-"), button -> {
			--this.value;
			valueWidget.setText("" + this.value);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new ButtonWidget(x + 190, y, 20, 20, new LiteralText("+"), button -> {
			++this.value;
			valueWidget.setText("" + this.value);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new ButtonWidget(x + 160, y, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
			try {
				int newval = Integer.parseInt(valueWidget.getText());
				this.value = newval;
				valueWidget.setEditableColor(0xFFFFFF);
			}catch(NumberFormatException e) {
				valueWidget.setEditableColor(0xFF0000);
			}
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, TextRenderer font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		font.drawWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueWidget.setText("" + value);
	}
	
	public void addTextFields(TextRenderer fr) {
		valueWidget = new TextFieldWidget(fr, x + 110, y, 50, 20, new LiteralText(""));
		valueWidget.setText("" + value);
		valueWidget.setEditableColor(0xFFFFFF);
		textFieldList.add(valueWidget);
	}
}
