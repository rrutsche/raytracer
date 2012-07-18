package cg2.movies;

import cg2.raytracer.Camera;
import cg2.raytracer.ImageGenerator;
import cg2.raytracer.LightSource;
import cg2.raytracer.Raytracer;
import cg2.raytracer.Scene;
import cg2.shapes.Material;
import cg2.shapes.Plane;
import cg2.shapes.Sphere;
import cg2.vecmath.Color;
import cg2.vecmath.Vector;

public class Movie2 {
	
	public static void main(String[] args) {

		for(int q=90; q < 100; q++){
			Scene scene = new Scene(new Color(0.1f, 0.1f, 0.1f));
			
			//set materials 
			Material materialGreen = new Material(new Color(0.5f, 1f, 0), new Color(0.5f, 1f, 0), new Color(0.5f, 1f, 0), new Color(0.3f,0.3f,0.3f), 600);
			Material materialIceBlue = new Material(new Color(0.5f, 0.5f, 1), new Color(0.5f, 0.5f, 1), new Color(0.5f, 0.5f, 1), new Color(0.3f,0.3f,0.3f), 200);
			Material materialGlas = new Material(new Color(0.01f,0.01f,0.01f), new Color(0.01f,0.01f,0.01f), new Color(1, 1, 1), new Color(0.5f,0.5f,0.5f), 300);
			Material materialPlane = new Material(new Color(0,0,0), new Color(0.9f, 0.9f, 0.9f), new Color(0,0,0), new Color(0.7f, 0.7f, 0.7f), 4);
			
			//set light sources
			float lightInt = 1.0f;
			for(int i=0; i<2; i++){			
					scene.addLightSource(new LightSource(new Vector(-100+i*5, 150, 50), lightInt*0.2f, new Color(1, 1, 1)));
					scene.addLightSource(new LightSource(new Vector(-100+i*5, 100, 70), lightInt*0.4f, new Color(1, 1, 1)));		
			}
			
			//add shapes to the scene
			scene.addShape(new Sphere(new Vector(-50, 0, -80), 8f, materialIceBlue));
			scene.addShape(new Sphere(new Vector(-48, 0 ,-120), 8f, materialIceBlue));
			scene.addShape(new Sphere(new Vector(-46, 0,-160), 8f, materialIceBlue));
			
			scene.addShape(new Sphere(new Vector(20, 5,-115), 30f, materialGlas));
			scene.addShape(new Sphere(new Vector(0, 5,-155), 30f, materialGreen));
			
			scene.addShape(new Sphere(new Vector(30, 20, -50), 4f, materialGlas));
			scene.addShape(new Sphere(new Vector(40, 10, -50), 4f, materialGlas));
			
			scene.addShape(new Sphere(new Vector(40, -10, -50), 8f, materialGlas));
			scene.addShape(new Sphere(new Vector(40, -10, -50), 3f, materialGlas));
			
			scene.addShape(new Plane(new Vector(0, -10, 0), new Vector(0.15f, 1.0f, 0), materialPlane));
			
			Camera cam = new Camera(new Vector(0,q,0), new Vector(0,0,-1), 80);		
			
			String path = System.getProperty("user.home");
	
			String filename = path + "/raytracemovie2/" + "raytracermovie2_"+q+".png";
			Raytracer raytracer=new Raytracer(scene, cam);
			new ImageGenerator(raytracer, 300, 200, filename, "png");
//			ImageGenerator.showImage(filename);
	
		}
	}
}
