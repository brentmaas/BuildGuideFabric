package brentmaas.buildguide.property;

import net.minecraft.text.Text;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int x, int y, int value, Text name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate, 1);
	}
}
