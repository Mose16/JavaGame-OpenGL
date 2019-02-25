package enginTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGameLoop {

	public static void main(String[] args) { //***THIS RUNS FIRST
		
		DisplayManager.createDisplay();
		
		while(!Display.isCloseRequested()) { //Loop until close is clicked
			
			//Game logic
			//Render
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
		
	}

}
