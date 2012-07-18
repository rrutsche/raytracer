package cg2.shapes;

import java.util.ArrayList;

import cg2.raytracer.Hit;
import cg2.raytracer.Ray;
import cg2.vecmath.Vector;

public class AxisAlignedBox extends Shape{
	
	private Vector pMin;
	private Vector pMax;
	
	
	public AxisAlignedBox(Vector pMin, Vector pMax, Material material) {
		this.material = material;
		this.pMin = pMin;
		this.pMax = pMax;
	}


	@Override
	public Hit intersect(Ray ray) {
		
		ArrayList<Plane> planesRaw = new ArrayList<Plane>();
		ArrayList<Plane> planesNorm = new ArrayList<Plane>();
		Vector normal = new Vector(0, 0, 0);
		float t = 0;
		
		//create 6 planes
		planesRaw.add(new Plane(new Vector(pMin.x, pMin.y, pMin.z), new Vector(-1,  0,  0), material));//yz1
		planesRaw.add(new Plane(new Vector(pMax.x, pMax.y, pMax.z), new Vector( 1,  0,  0), material));//yz2
		planesRaw.add(new Plane(new Vector(pMin.x, pMin.y, pMin.z), new Vector( 0,  0, -1), material));//xy1
		planesRaw.add(new Plane(new Vector(pMax.x, pMax.y, pMax.z), new Vector( 0,  0,  1), material));//xy2		
		planesRaw.add(new Plane(new Vector(pMin.x, pMin.y, pMin.z), new Vector( 0, -1,  0), material));//xz1
		planesRaw.add(new Plane(new Vector(pMax.x, pMax.y, pMax.z), new Vector( 0,  1,  0), material));//xz2
		
		//check which plane faces the camera
		
		for(Plane plane: planesRaw){
				Vector xo = ray.getOrigin();
				//calculate the direction of the normal
				if(plane.getNormal().dot((xo).sub(plane.getAnchorPoint())) >= 0){
					planesNorm.add(plane);
				}
		}
		
		for (Plane plane : planesNorm) {
			if (plane.intersect(ray) != null && plane.intersect(ray).getRayParam() > 0.1f) {
				if (t <= plane.intersect(ray).getRayParam()) {
					t = plane.intersect(ray).getRayParam();
					normal = plane.getNormal();
				}
			}
		}
		Vector iPoint = ray.getPoint(t);
		float eps = 0.1f;
		
		if(iPoint.x <= pMax.x+eps && iPoint.x >= pMin.x-eps){
			if(iPoint.y <= pMax.y+eps && iPoint.y >= pMin.y-eps){
				if(iPoint.z <= pMax.z+eps && iPoint.z >= pMin.z-eps){
					return new Hit(ray, this, t, normal);
				}
			}
		}						
		return null;
	}

}
