package brentmaas.buildguide;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEllipse;
import brentmaas.buildguide.shapes.ShapeEllipsoid;
import brentmaas.buildguide.shapes.ShapeEmpty;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapePolygon;
import brentmaas.buildguide.shapes.ShapeSphere;
import brentmaas.buildguide.shapes.ShapeTorus;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.text.TranslatableText;

public class State {
	//TODO: Config stuff
	public boolean debugGenerationTimingsEnabled = true; //TODO: change to depend on config
	
	public Shape[] shapeStore = {new ShapeEmpty(), new ShapeCircle(), new ShapeCuboid(), new ShapeEllipse(), new ShapeEllipsoid(), new ShapeLine(), new ShapePolygon(), new ShapeSphere(), new ShapeTorus()};
	public int i_shape = 0;
	public Vector3d basePos = null;
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 80, true, new TranslatableText("screen.buildguide.depthtest"), null);
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
	public static Shape getCurrentShape() {
		return BuildGuide.state.shapeStore[BuildGuide.state.i_shape];
	}
	
	public static void updateCurrentShape() {
		BuildGuide.state.shapeStore[BuildGuide.state.i_shape].update();
	}
}
