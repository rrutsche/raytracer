package cg2.raytracer;

import cg2.vecmath.Color;

/**
 * This class represents the raytrace algorithm.
 * It calculates the color for a certain pixel.
 * @author Richard Rutsche, Oliver Schobel
 *
 */
public class Raytracer implements Painter{

	private Scene scene;
	private Camera cam;
	
	

	public Raytracer(Scene scene, Camera cam) {
		this.scene = scene;
		this.cam = cam;
	}

	@Override
	public Color pixelColorAt(int x, int y, int width, int height) {

		Ray ray = cam.generateRay(x, y, width, height);
		Hit hit = scene.intersect(ray, 0.0f, Float.POSITIVE_INFINITY);
		if(hit != null){
			return hit.getShape().getMaterial().shade(ray, hit, scene, 2);
		}
		return new Color(0,0,0);
	}

}
