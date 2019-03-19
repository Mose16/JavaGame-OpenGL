package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolBox.Maths;

public class StaticShader extends ShaderProgram{
	
	//Hard code location rather than passing it through
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;

	public StaticShader() { //Constructor
		super(VERTEX_FILE, FRAGMENT_FILE); //Run super constructor
	}

	@Override //??
	protected void bindAttributes() { //Protected abstract from super
		super.bindAttribute(0, "position"); //Runs method to bind positional data in VAO position 0 to program. Cannot do all that here because we do not have access to programID
		super.bindAttribute(1, "texutreCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix"); //Get location of unfirom variable
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightColour = super.getUniformLocation("lightColour");
		location_lightPosition = super.getUniformLocation("lightPosition");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix); //Load matrix to uniform variable
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition()); //Load vector to uniform variable
		super.loadVector(location_lightColour, light.getColour());
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix); //Load matrix to uniform variable
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix); //Load matrix to uniform variable
	}
	
}
