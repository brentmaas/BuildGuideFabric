package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.Property;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	private VertexBuffer buffer;
	private int nBlocks = 0;
	
	public Shape() {
		//buffer = new VertexBuffer(VertexFormats.POSITION_COLOR);
	}
	
	protected abstract void updateShape(BufferBuilder builder);
	public abstract String getTranslationKey();
	
	public void update() {
		nBlocks = -1; //Counteract the add from the base position
		long t = System.currentTimeMillis();
		BufferBuilder builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
		this.updateShape(builder);
		addCube(builder, 0.4, 0.4, 0.4, 0.2, BuildGuide.state.colourBaseposR, BuildGuide.state.colourBaseposG, BuildGuide.state.colourBaseposB, BuildGuide.state.colourBaseposA); //Base position
		builder.end();
		if(buffer != null) buffer.close();
		buffer = new VertexBuffer(VertexFormats.POSITION_COLOR);
		buffer.upload(builder);
		if(BuildGuide.state.debugGenerationTimingsEnabled) {
			BuildGuide.logger.debug("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	public void render(Matrix4f matrix) {
		this.buffer.bind();
		VertexFormats.POSITION_COLOR.startDrawing(0);
		this.buffer.draw(matrix, GL11.GL_QUADS);
		VertexBuffer.unbind();
		VertexFormats.POSITION_COLOR.endDrawing();
	}
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s, float r, float g, float b, float a) {
		//-X
		buffer.vertex(x, y, z).color(r, g, b, a).next();
		buffer.vertex(x, y, z+s).color(r, g, b, a).next();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).next();
		buffer.vertex(x, y+s, z).color(r, g, b, a).next();
		
		//-Y
		buffer.vertex(x, y, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).next();
		buffer.vertex(x, y, z+s).color(r, g, b, a).next();
		
		//-Z
		buffer.vertex(x, y, z).color(r, g, b, a).next();
		buffer.vertex(x, y+s, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y, z).color(r, g, b, a).next();
		
		//+X
		buffer.vertex(x+s, y, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).next();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).next();
		
		//+Y
		buffer.vertex(x, y+s, z).color(r, g, b, a).next();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).next();
		
		//+Z
		buffer.vertex(x, y, z+s).color(r, g, b, a).next();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).next();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).next();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).next();
		
		nBlocks++;
	}
	
	public void onSelectedInGUI() {
		for(Property<?> p: properties) {
			p.onSelectedInGUI();
		}
	}
	
	public void onDeselectedInGUI() {
		for(Property<?> p: properties) {
			p.onDeselectedInGUI();
		}
	}
	
	public String getTranslatedName() {
		return new TranslatableText(getTranslationKey()).getString();
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
}
