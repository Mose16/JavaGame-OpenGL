package shaders;

public class StaticShader extends ShaderProgram{
	
	//Hard code location rather than passing it through
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

	public StaticShader() { //Constructor
		super(VERTEX_FILE, FRAGMENT_FILE); //Run super constructor
	}

	@Override //??
	protected void bindAttributes() { //Protected abstract from super
		super.bindAttribute(0, "position"); //Runs method to bind positional data in VAO position 0 to program. Cannot do all that here because we do not have access to programID
		super.bindAttribute(1, "texutreCoords");
	}
	
}
