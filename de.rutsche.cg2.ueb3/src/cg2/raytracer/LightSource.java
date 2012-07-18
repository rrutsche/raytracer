package cg2.raytracer;

import cg2.vecmath.Color;
import cg2.vecmath.Vector;

public class LightSource {
	
	private Vector position;
	private float intensity;
	private Color color;
	
	public LightSource(Vector position, float intensity, Color color) {
		this.position = position;
		this.intensity = intensity;
		this.color = color;
	}

	public Vector getPosition() {
		return position;
	}

	public float getIntensity() {
		return intensity;
	}
	
	public Color getColor(){
		return color;
	}
}
