package cg2.shapes;

import cg2.raytracer.Hit;
import cg2.raytracer.Ray;
import cg2.vecmath.Vector;


/**
 * This represents a sphere with two parameters center and radius.
 * @author Richard Rutsche, Oliver Schobel
 *
 */
public class Sphere extends Shape {

	private Vector center;
	private float radius;
	private Vector normal;
	
	public Sphere(Vector center, float radius, Material material) {
		this.material = material;
		this.center = center;
		this.radius = radius;
	}

	@Override
	public Hit intersect(Ray ray) {
		Vector d = ray.getNormDirection();
		Vector xo = ray.getOrigin().sub(center);
		
		//the term underneath the square root in the pq-formula
		double root = Math.pow(xo.dot(d), 2) - (xo.dot(xo) - Math.pow(radius, 2));	
		
		
		if(root == 0){//ray touches the sphere
			float t = -(xo.dot(d));
			normal = center.add(ray.getNormDirection().mult(t)).normalize();
				//ray.getOrigin().add((ray.getNormDirection()).mult(t));
			return new Hit(ray, this, t, normal);			
		}else if(root > 0){//there is two results
			
			//calculate first intersect
			float t1 = -(xo.dot(d)) + (float)Math.sqrt(root);
			float t2 = -(xo.dot(d)) - (float)Math.sqrt(root);
			float t;
			if(t1 > 0.01f && t2 > 0.01f){
				t = Math.min(t1, t2);
				normal = (ray.getPoint(t).sub(center)).normalize();
			}else{
				return null;
			}			
			return new Hit(ray, this, t, normal);
		}
		
		//if there is no intersection: return null
		return null;
	}
}
