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
			    -0.5f, 0.5f, 0f,  //V0
			    -0.5f, -0.5f, 0f, //V1
			    0.5f, -0.5f, 0f,  //V2
			    0.5f, 0.5f, 0f    //V3
		};
		
		int[] indices = {
				0,1,3, //Top left triangle (V0,V1,V3)
				3,1,2  //Bottom right triangle (V3,V1,V2)
		};
		
		
		RawModel model = loader.loadToVAO(vertices, indices); //Load it to raw model using loadToVao method
		
		
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
