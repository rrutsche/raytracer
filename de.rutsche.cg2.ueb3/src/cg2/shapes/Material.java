package cg2.shapes;

import java.util.ArrayList;

import cg2.raytracer.Hit;
import cg2.raytracer.LightSource;
import cg2.raytracer.Ray;
import cg2.raytracer.Scene;
import cg2.vecmath.Color;
import cg2.vecmath.Vector;

public class Material {
	
	private float phongExponent;
	private float epsilon;
	private float intensity;
	private Color kAmbient;
	private Color kDiffuse;
	private Color kSpecular;
	private Color kReflect;
	private Color c;
	private ArrayList<LightSource> lightSources;
	private Vector s;
	private Vector r;
	private Vector v;
	private Vector normal;
	private Vector point;
	private Ray rayShadow;
	private Ray rayReflect;
	private Ray rayRefract;
	
	public Material(Color kAmbient, Color kDiffuse, Color kSpecular, Color kReflect, float phongExponent) {
		this.kAmbient = kAmbient;
		this.kDiffuse = kDiffuse;
		this.kSpecular = kSpecular;
		this.phongExponent = phongExponent;
		this.kReflect = kReflect;
	}

	public Color getkAmbient() {
		return kAmbient;
	}

	public Color getkDiffuse() {
		return kDiffuse;
	}

	public Color getkSpecular() {
		return kSpecular;
	}
	
	public Color getkReflect(){
		return kReflect;
	}

	public float getPhongExponent() {
		return phongExponent;
	}
	
	/**
	 * Calculates the color at the actual point
	 * 
	 * @param ray
	 * @param hit
	 * @param scene
	 * @param depth
	 * @return Color
	 */
	public Color shade(Ray ray, Hit hit, Scene scene, int depth){
		
		//some variables
		epsilon = 0.001f;
		float n1 = 1.00029f;
		float n2 = 1.2f;
		normal = hit.getNormal();
		point = ray.getPoint(hit.getRayParam());
		lightSources = scene.getLightSources();
		c = kAmbient.modulate(scene.getAmbientLight());
		
		//variables for the refraction
		float cosOi = normal.dot(ray.getNormDirection().mult(-1));
		float rR0 = (float) Math.pow(((n1-n2)/(n1+n2)), 2);
		float rR = rR0 + (1 - rR0) * (float) Math.pow((1 - Math.pow(cosOi, 2)), 5);
		float tT = 1 - rR;
		
		//do for all lights in the scene
		for(LightSource light: lightSources){
			
			s = light.getPosition().sub(point);
			r = ((normal.mult(2 * (normal.dot(s)))).sub(s)).normalize();
			v = (ray.getNormDirection().mult(-1)).normalize();
			intensity = light.getIntensity();
			
			//calculate reflection
			if(rR > epsilon) {
				c = c.add(calcReflection(ray, normal, point, scene, depth).modulate(rR));
			}
			//calculate refraction
			if(1-rR > epsilon){
				c = c.add(calcRefraction(scene, ray, n1, n2, point, normal, cosOi, depth, hit).modulate(tT));	
			}
			
			//calculate shadow
			rayShadow = new Ray(point, light.getPosition().sub(point).normalize());
			int shadow = calcShadow(rayShadow, scene);
			
			//calculate specular and diffuse color
			if(normal.dot(s) > epsilon){
				Color cSpecular = kSpecular.modulate(light.getColor().modulate(intensity).modulate((float) Math.pow(v.dot(r), phongExponent))).modulate(shadow);
				Color cDiffuse = kDiffuse.modulate(light.getColor().modulate(intensity).modulate(normal.dot(r))).modulate(shadow);
				c = c.add(cSpecular.add(cDiffuse));
			}
		}
		return c;
	}

	
	
	/**
	 * Calculates the reflection at this hit point.
	 * 
	 * @param ray
	 * @param normal
	 * @param point
	 * @param scene
	 * @param depth
	 * @return Color - the calculated reflection color
	 */
	private Color calcReflection(Ray ray, Vector normal, Vector point, Scene scene, int depth) {

		Color cReflect = new Color(0, 0, 0);
		
		if(depth > 1){
		
			Vector vR = ray.getNormDirection()
					.sub(normal.mult(2 * (ray.getNormDirection().dot(normal))))
					.normalize();
			rayReflect = new Ray(point, vR);
			Hit hitReflect = scene.intersect(rayReflect, epsilon,
					Float.POSITIVE_INFINITY);
			if (depth > 0 && hitReflect != null && rayReflect.getNormDirection().mult(-1).dot(hitReflect.getNormal()) > epsilon) {
				cReflect = hitReflect.getShape().getMaterial().shade(rayReflect, hitReflect, scene, depth - 1).modulate(kReflect);
			}
		}
		return cReflect;
	}
	
	
	
	/**
	 * Calculates the refraction at this hit point.
	 * 
	 * @param scene
	 * @param ray
	 * @param n1
	 * @param n2
	 * @param point
	 * @param normal
	 * @param cosOi
	 * @param depth - recursion depth
	 * @return Color - the refraction color
	 */
	private Color calcRefraction(Scene scene, Ray ray, float n1, float n2, Vector point, Vector normal, float cosOi, int depth, Hit hit){
		
		Color cRefract = new Color(0,0,0);
		
		if(depth > 0){

			float sinOt = (float) (Math.pow((n1/n2), 2) * (1 - Math.pow(cosOi, 2)));
			float root = (float) Math.sqrt(1 - Math.pow(sinOt, 2));
			
			if(root >= 0.001 && n1 <= n2){
				Vector vT = ray.getNormDirection().mult(n1/n2).add(normal.mult((float)(n1/n2 * cosOi - Math.sqrt(1- sinOt))));
				rayRefract = new Ray(point, vT);
				Hit hitRefract = scene.intersect(rayRefract, epsilon, Float.POSITIVE_INFINITY);
				float tMin = epsilon;
				
				//if the ray hits the current shape itself, tMin is set to this rayParam 
				if(hitRefract != null && hitRefract.getShape().equals(hit.getShape()) 
						&&	rayRefract.getNormDirection().mult(-1).dot(hitRefract.getNormal()) > 0.00001f){
					tMin = hitRefract.getRayParam();
					hitRefract = scene.intersect(rayRefract, tMin+epsilon, Float.POSITIVE_INFINITY);
				}
				if(hitRefract != null && rayRefract.getNormDirection().mult(-1).dot(hitRefract.getNormal()) > 0.00001f){
					cRefract = hitRefract.getShape().getMaterial().shade(rayRefract, hitRefract, scene, depth-1).modulate(kReflect);
				}
			}
		}
		return cRefract;
	}
	
	
	
	/**
	 * Calculates the shadow for this point.
	 * 
	 * @param ray
	 * @param scene
	 * @return int - 0 if there is an object between this shape and the light source, 1 if not
	 */
	private int calcShadow(Ray ray, Scene scene){
		Hit hit = scene.intersect(ray, epsilon, Float.POSITIVE_INFINITY);
		if(hit != null){
			return 0;
		}else{
			return 1;
		}		
	}
}
