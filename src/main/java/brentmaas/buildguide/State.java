package brentmaas.buildguide;

import java.util.ArrayList;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class State {
	public Shape[] simpleModeShapes;
	public int iSimple = 0;
	public ArrayList<Shape> advancedModeShapes = new ArrayList<Shape>();
	public int iAdvanced = 0;
	public PropertyBoolean propertyEnable = new PropertyBoolean(-4, false, new TranslatableText("screen.buildguide.enable"), null);
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(2, true, new TranslatableText("screen.buildguide.depthtest"), null);
	public PropertyBoolean propertyAdvancedMode = new PropertyBoolean(-2, false, new TranslatableText("screen.buildguide.advancedmode"), () -> MinecraftClient.getInstance().setScreen(new BuildGuideScreen()));
	
	public State() {
		ArrayList<String> classIdentifiers = ShapeRegistry.getClassIdentifiers();
		simpleModeShapes = new Shape[classIdentifiers.size()];
		for(int i = 0;i < classIdentifiers.size();++i) {
			simpleModeShapes[i] = ShapeRegistry.getNewInstance(classIdentifiers.get(i));
		}
	}
	
	public Shape getCurrentShape() {
		if(propertyAdvancedMode.value) {
			return advancedModeShapes.size() > 0 ? advancedModeShapes.get(iAdvanced) : null;
		}
		return simpleModeShapes[iSimple];
	}
	
	public void updateCurrentShape() {
		if(propertyAdvancedMode.value) {
			for(int i = 0;i < advancedModeShapes.size();++i) {
				advancedModeShapes.get(i).update();
			}
		}else {
			simpleModeShapes[iSimple].update();
		}
	}
	
	public boolean isShapeAvailable() {
		return !propertyAdvancedMode.value || advancedModeShapes.size() > 0;
	}
	
	public void resetBasepos() {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).resetBasepos();
		}else {
			for(Shape s: simpleModeShapes) s.resetBasepos();
		}
	}
	
	public void resetBasepos(int advancedModeId) {
		advancedModeShapes.get(advancedModeId).resetBasepos();
	}
	
	public void setBasepos(int x, int y, int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos(x, y, z);
		}else {
			for(Shape s: simpleModeShapes) s.setBasepos(x, y, z);
		}
	}
	
	public void setBaseposX(int x) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos(x, (int) advancedModeShapes.get(iAdvanced).basePos.y, (int) advancedModeShapes.get(iAdvanced).basePos.z);
		} else {
			for(Shape s: simpleModeShapes) s.setBasepos(x, (int) s.basePos.y, (int) s.basePos.z);
		}
	}
	
	public void setBaseposY(int y) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos((int) advancedModeShapes.get(iAdvanced).basePos.x, y, (int) advancedModeShapes.get(iAdvanced).basePos.z);
		} else {
			for(Shape s: simpleModeShapes) s.setBasepos((int) s.basePos.x, y, (int) s.basePos.z);
		}
	}
	
	public void setBaseposZ(int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos((int) advancedModeShapes.get(iAdvanced).basePos.x, (int) advancedModeShapes.get(iAdvanced).basePos.y, z);
		} else {
			for(Shape s: simpleModeShapes) s.setBasepos((int) s.basePos.x, (int) s.basePos.y, z);
		}
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).shiftBasepos(dx, dy, dz);
		} else {
			for(Shape s: simpleModeShapes) s.shiftBasepos(dx, dy, dz);
		}
	}
}
