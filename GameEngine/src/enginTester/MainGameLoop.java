package enginTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

	public static void main(String[] args) { //***THIS RUNS FIRST
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		
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
			shader.start();
			renderer.render(model); //Render model
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		//Clean up and close
		shader.cleanUp(); //Clean up shader
		loader.cleanUp(); //Clean up loader
		DisplayManager.closeDisplay(); //Close display
		
	}

}
