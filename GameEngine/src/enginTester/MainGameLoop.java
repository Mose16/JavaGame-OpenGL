package enginTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) { //***THIS RUNS FIRST
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		
		//OpenGL expects vertices to be defined counter clockwise by default
		RawModel model = OBJLoader.loadObjectModel("dragon", loader);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("boxTexture")); //Create a new model texture after passing it the textureID created through the loader class
		TexturedModel texturedModel = new TexturedModel(model,texture); //Combine both model and texture
		Entity entity = new Entity(texturedModel, new Vector3f(0,0,-25), 0, 0, 0, 1); //Create one object
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1,1,1)); //Position and colour
		Camera camera = new Camera();
		
		
		while(!Display.isCloseRequested()) { //Loop until close is clicked
			//Game logic
			
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 1, 0);
			camera.move();
			
			//Render
			renderer.prepare(); //Prepare renderer each frame
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera); //Move whole world
			renderer.render(entity, shader); //Render model
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		//Clean up and close
		shader.cleanUp(); //Clean up shader
		loader.cleanUp(); //Clean up loader
		DisplayManager.closeDisplay(); //Close display
		
	}

}
