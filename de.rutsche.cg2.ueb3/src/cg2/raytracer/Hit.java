package cg2.raytracer;

import cg2.shapes.Shape;
import cg2.vecmath.Vector;

/**
 * Represents an intersection point of Ray and Shape.
 * @author Richard Rutsche, Oliver Schobel
 *
 */
public class Hit {
	
	private Ray ray;
	private Shape shape;
	private float rayParam;
	private Vector normal;
	
	public Hit(Ray ray, Shape shape, float rayParam, Vector normal) {
		this.ray = ray;
		this.shape = shape;
		this.rayParam = rayParam;
		this.normal = normal;
	}
	
	public Ray getRay() {
		return ray;
	}
	public Shape getShape() {
		return shape;
	}
	public float getRayParam() {
		return rayParam;
	}

	public Vector getNormal() {
		return normal;
	}
	

}
