package cg2.shapes;

import cg2.raytracer.Hit;
import cg2.raytracer.Ray;
import cg2.vecmath.Vector;

/**
 * This class represents a plane to be added into a scene.
 * 
 * @author Richard Rutsche, Oliver Schobel
 * 
 */
public class Plane extends Shape {

	private Vector	anchorPoint;
	private Vector	normal;

	public Plane(Vector anchorPoint, Vector normal, Material material) {
		this.anchorPoint = anchorPoint;
		this.normal = normal.normalize();
		this.material = material;
	}

	public Vector getAnchorPoint() {
		return anchorPoint;
	}

	public Vector getNormal() {
		return normal;
	}

	@Override
	public Hit intersect(Ray ray) {

		Vector d = ray.getNormDirection();
		Vector xo = ray.getOrigin().sub(anchorPoint);

		// check if plane and ray are parallel to each other
		if (normal.dot(d) == 0) {
			return null;
		}
		float dk = normal.dot(anchorPoint);
		float t = (dk - normal.dot(xo)) / normal.dot(d);

		// assure that intersect didn't take place behind the viewing direction
		if (t > 0.01f) {
			return new Hit(ray, this, t, normal.normalize());
		}
		return null;
	}

	


}
