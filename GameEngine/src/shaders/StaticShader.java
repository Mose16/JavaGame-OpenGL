package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	//Hard code location rather than passing it through
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;

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
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix); //Load matrix to uniform variable
	}
	
}
