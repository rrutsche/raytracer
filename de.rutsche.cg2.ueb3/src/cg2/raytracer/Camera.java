package cg2.raytracer;

import cg2.vecmath.Vector;

/**
 * Virtual view into the scene.
 * @author Richard Rutsche, Oliver Schobel
 *
 */
public class Camera {

	private Vector eyePoint;
	private Vector viewingDirection;
	private float fieldOfViewX;
	
	public Vector getViewingDirection() {
		return viewingDirection;
	}

	public Camera(Vector eyepoint, Vector viewingDirection, float fieldOfViewX) {
		this.eyePoint = eyepoint;
		this.viewingDirection = viewingDirection;
		this.fieldOfViewX = fieldOfViewX;
	}

	/**
	 * @param i - Pixel x
	 * @param j - Pixel y
	 * @param width - width of the rendered image 
	 * @param height - height of the rendered image
	 * @return - Ray of a certain pixel
	 */
	public Ray generateRay(int i, int j, int width, int height){
		float x = -(width/2.0f) + (0.5f + i) * (width/width);
		float y = -(height/2.0f) + (0.5f + j) * (height/height);
		float z = (float) (width / (2.0f * Math.tan(Math.toRadians(fieldOfViewX) / 2.0f)));
		
		return new Ray(eyePoint, new Vector(x,y,-z));		
	}
}
