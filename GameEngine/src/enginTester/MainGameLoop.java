package enginTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) { //***THIS RUNS FIRST
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		
		//OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {
				//Bottom left triangle
			    -0.5f, 0.5f, 0f,
			    -0.5f, -0.5f, 0f,
			    0.5f, -0.5f, 0f,
			    //Top right triangle
			    0.5f, -0.5f, 0f,
			    0.5f, 0.5f, 0f,
			    -0.5f, 0.5f, 0f
		};
		
		RawModel model = loader.loadToVAO(vertices); //Load it to raw model using loadToVao method
		
		
		while(!Display.isCloseRequested()) { //Loop until close is clicked
			renderer.prepare(); //Prepare renderer each frame
			//Game logic
			//Render
			renderer.render(model); //Render model
			DisplayManager.updateDisplay();
		}
		
		loader.cleanUp(); //Clean up
		DisplayManager.closeDisplay(); //Close display
		
	}

}
