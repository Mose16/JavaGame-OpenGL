package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import toolBox.Maths;

public class StaticShader extends ShaderProgram{
	
	//Hard code location rather than passing it through
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	public StaticShader() { //Constructor
		super(VERTEX_FILE, FRAGMENT_FILE); //Run super constructor
	}

	@Override //??
	protected void bindAttributes() { //Protected abstract from super
		super.bindAttribute(0, "position"); //Runs method to bind positional data in VAO position 0 to program. Cannot do all that here because we do not have access to programID
		super.bindAttribute(1, "texutreCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix"); //Get location of unfirom variable
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix); //Load matrix to uniform variable
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix); //Load matrix to uniform variable
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix); //Load matrix to uniform variable
	}
	
}
