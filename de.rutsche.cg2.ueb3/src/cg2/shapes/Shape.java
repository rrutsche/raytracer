package cg2.shapes;

import cg2.raytracer.Hit;
import cg2.raytracer.Ray;

/**
 * Interface for all shapes in the scene.
 * @author Richard Rutsche, Oliver Schobel
 *
 */
public abstract class Shape {
	
	Material material;
	
	public Material getMaterial(){
		return material;
	}
	
	public abstract Hit intersect(Ray ray);

}
