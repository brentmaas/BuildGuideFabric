package brentmaas.buildguide.shapes;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.text.TranslatableText;

public class ShapeCircle extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslatableText("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 165, 3, new TranslatableText("property.buildguide.radius"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 185, 1, new TranslatableText("property.buildguide.height"), () -> {this.update();});
	
	public ShapeCircle() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int dx = propertyRadius.value, dy = propertyRadius.value, dz = propertyRadius.value;
		switch(propertyDir.value) {
		case X:
			dx = 0;
			break;
		case Y:
			dy = 0;
			break;
		case Z:
			dz = 0;
			break;
		}
		
		for(int x = -dx; x <= dx;++x) {
			for(int y = -dy; y <= dy;++y) {
				for(int z = -dz; z <= dz;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (propertyRadius.value - 0.5) * (propertyRadius.value - 0.5) && r2 <= (propertyRadius.value + 0.5) * (propertyRadius.value + 0.5)) {
						for(int s = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);s < (propertyHeight.value > 0 ? propertyHeight.value : 1);++s) {
							addCube(builder, x + (propertyDir.value == direction.X ? s : 0) + 0.2, y + (propertyDir.value == direction.Y ? s : 0) + 0.2, z + (propertyDir.value == direction.Z ? s : 0) + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.circle";
	}
}
