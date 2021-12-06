package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyMinimumInt;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.text.TranslatableText;

public class ShapePolygon extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	private enum rotation{
		ROT0,
		ROT90,
		ROT180,
		ROT270
	}

	private String[] directionNames = {"X", "Y", "Z"};
	private String[] rotationNames = {"0�", "90�", "180�", "270�"};
	
	//Constant arrays for rotation
	private static final int[] rotXX = {1, 0, -1, 0};
	private static final int[] rotXY = {0, -1, 0, 1};
	private static final int[] rotYX = {0, 1, 0, -1};
	
	private PropertyMinimumInt propertySides = new PropertyMinimumInt(0, 3, new TranslatableText("property.buildguide.sides"), () -> this.update(), 3);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(1, 3, new TranslatableText("property.buildguide.radius"), () -> this.update());
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(2, direction.X, new TranslatableText("property.buildguide.direction"), () -> this.update(), directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(3, rotation.ROT0, new TranslatableText("property.buildguide.rotation"), () -> this.update(), rotationNames);
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(4, 1, new TranslatableText("property.buildguide.height"), () -> this.update());
	
	public ShapePolygon() {
		super();
		
		properties.add(propertySides);
		properties.add(propertyRadius);
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int n = propertySides.value;
		int r = propertyRadius.value;
		int rot = propertyRot.value.ordinal();
		for(int i = 0;i < n;++i) {
			int minA = (int) Math.floor(Math.min(r * (Math.sin(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n)), r * (Math.sin(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n))));
			int minB = (int) Math.floor(Math.min(r * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), r * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))));
			int maxA = (int) Math.ceil(Math.max(r * (Math.sin(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n)), r * (Math.sin(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n))));
			int maxB = (int) Math.ceil(Math.max(r * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), r * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))));
			for(int a = minA;a < maxA + 1;++a) {
				for(int b = minB;b < maxB + 1;++b) {
					double adx = (a - r * Math.sin(2 * Math.PI * i / n)) * Math.cos(2 * Math.PI * i / n) + (b + r * Math.cos(2 * Math.PI * i / n)) * Math.sin(2 * Math.PI * i / n);
					double d2 = (a - r * Math.sin(2 * Math.PI * i / n) - adx * Math.cos(2 * Math.PI * i / n)) * (a - r * Math.sin(2 * Math.PI * i / n) - adx * Math.cos(2 * Math.PI * i / n)) + (b + r * Math.cos(2 * Math.PI * i / n) - adx * Math.sin(2 * Math.PI * i / n)) * (b + r * Math.cos(2 * Math.PI * i / n) - adx * Math.sin(2 * Math.PI * i / n));
					double theta = Math.atan2(b, a) + Math.PI / 2;
					if(theta < 0 && i > 0) theta += 2 * Math.PI;
					if(d2 <= 0.25 && theta >= (2 * i - 1) * Math.PI / n && theta < (2 * i + 1) * Math.PI / n) {
						for(int h = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);h < (propertyHeight.value > 0 ? propertyHeight.value : 1);++h) {
							switch(propertyDir.value) {
							case X:
								addShapeCube(builder, h, b * rotXX[rot] + a * rotYX[rot], a * rotXX[rot] + b * rotXY[rot]);
								break;
							case Y:
								addShapeCube(builder, b * rotXX[rot] + a * rotYX[rot], h, a * rotXX[rot] + b * rotXY[rot]);
								break;
							case Z:
								addShapeCube(builder, a * rotXX[rot] + b * rotXY[rot], b * rotXX[rot] + a * rotYX[rot], h);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.polygon";
	}
}
