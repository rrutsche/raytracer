package cg2.raytracer;

import cg2.vecmath.Vector;

/**
 * This class represents a vector from the camera into the scene.
 * 
 * @author Richard Rutsche, Oliver Schobel
 * 
 */
public class Ray {
	
	private Vector origin;
	private Vector direction;
	
	public Ray(Vector origin, Vector normDirection) {
		this.origin = origin;
		this.direction = normDirection;
	}

	public Vector getDirection() {
		return direction;
	}

	public Vector getOrigin() {
		return origin;
	}

	public Vector getNormDirection() {
		return direction.normalize();
	}
	
	public Vector getPoint(float f){
		return origin.add(getNormDirection().mult(f));
	}
	

}
