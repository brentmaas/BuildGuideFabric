package brentmaas.buildguide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.text.TranslatableText;

public class State {
	private final File configFile = new File(FabricLoader.getInstance().getConfigDir().toString() + "/buildguide-client.toml");
	//Initial value is default
	public boolean debugGenerationTimingsEnabled = false;
	
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
	
	//TODO: Need better config stuff, currently mimics Forge config
	private void initConfig() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write("\n[Debug]\n\t#Enable debug output telling you how long it took for a shape to generate. It's spams a lot in the debug log.\n\tdebugGenerationTimingsEnabled = " + debugGenerationTimingsEnabled + "\n\n");
			writer.close();
		} catch (IOException e) {
			BuildGuide.logger.error("Could not initialise config file");
			e.printStackTrace();
		}
	}
	
	//TODO: Need better config stuff, currently mimics Forge config
	public void loadConfig() {
		if(!configFile.exists()) initConfig();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String line = reader.readLine().trim();
			while(reader.ready()) {
				if(line.length() > 0 && line.charAt(0) != '[' && line.charAt(0) != '#') {
					String[] splitted = line.split(" = ", 2);
					if(splitted.length == 2) {
						switch(splitted[0]) {
						case "debugGenerationTimingsEnabled":
							debugGenerationTimingsEnabled = Boolean.parseBoolean(splitted[1]);
							break;
						}
					}
				}
				line = reader.readLine().trim();
			}
			reader.close();
		} catch (IOException e) {
			BuildGuide.logger.error("Could not read config file");
			e.printStackTrace();
		}
	}
}
