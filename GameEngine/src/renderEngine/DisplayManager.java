package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	public static void createDisplay() { //~~*Create display
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true); //For OpenGL Ver-3.2
		
		try { //Try catch is filled in auto
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //Set up display
			Display.create(new PixelFormat(), attribs); //Create display
			Display.setTitle("New game"); //Give header title
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT); //Bottom left to top right. Tells what area to render
		
	}
	
	
	public static void updateDisplay() { //~~*Update display
		
		Display.sync(FPS_CAP); //Sync's the screen to given FPS
		Display.update();
	}
	
	
	public static void closeDisplay() { //~~*Close display
		Display.destroy(); //As it says
	}

}
