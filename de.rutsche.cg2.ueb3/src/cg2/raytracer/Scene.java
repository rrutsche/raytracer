package cg2.raytracer;

import java.util.ArrayList;

import cg2.shapes.Shape;
import cg2.vecmath.Color;
import cg2.vecmath.Vector;


/**
 * This class represents a collection of shapes which creates a virtual scenery.
 * 
 * @author Richard Rutsche, Oliver Schobel
 * 
 */
public class Scene {

	private ArrayList<Shape>	shapeObjects;
	private ArrayList<LightSource> lightSources;
	private Color ambientLight;

	/**
	 * Constructor
	 */
	public Scene(Color ambientLight) {
		this.ambientLight = ambientLight;
		shapeObjects = new ArrayList<Shape>();
		lightSources = new ArrayList<LightSource>();
	}

	/**
	 * @param shape
	 */
	public void addShape(Shape shape) {
		shapeObjects.add(shape);
	}
	
	public void addLightSource(LightSource lightSource){
		lightSources.add(lightSource);
	}

	/**
	 * Runs through all stored objects in the scenery and returns a hit object
	 * when the ray hits an shape object.
	 * 
	 * @param ray
	 * @return Hit
	 */
	public Hit intersect(Ray ray, float tMin, float tMax) {
		Hit hit = null;
		Hit newHit = null;

		for (Shape ob : shapeObjects) {
			if (ob.intersect(ray) != null) {
				hit = ob.intersect(ray);
			}
			if (newHit == null) {
				newHit = hit;
			}
			if (newHit != null && hit != null
					&& hit.getRayParam() < newHit.getRayParam()
					&& hit.getRayParam() > tMin && hit.getRayParam() < tMax) {
				newHit = hit;
			}
		}
		return newHit;
	}

	public Color getAmbientLight() {
		return ambientLight;
	}

	public ArrayList<LightSource> getLightSources() {
		return lightSources;
	}
	
	public void makePseudoAreaLight(Vector position, Vector direction, int width, int height, int lightsX, int lightsY, Color color){
		
		for (int i = 1; i <= lightsX; i++) {
			for (int j = 1; j <= lightsY; j++) {
				
			}
		}
	}
	
	
}
