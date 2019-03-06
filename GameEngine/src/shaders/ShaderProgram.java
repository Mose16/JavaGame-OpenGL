package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram { //Abstract means it represents what all shader programs should have. Other shader classes inherit from this?
	
	//These are for the next gen class. Inherited
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); //4x4 matrices
	
	public ShaderProgram(String vertexFile, String fragmentFile) { //Constructor
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER); //Creates vertex shader and returns ID for that file, loads file into OpenGL
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER); //Creates fragment shader and returns ID for that file, loads file into OpenGL
		programID = GL20.glCreateProgram(); //Creates program to tie vertex and fragment together
		GL20.glAttachShader(programID, vertexShaderID); //Attach shader's to program
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID); //Link
		GL20.glValidateProgram(programID); //Validate program
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) { //As it says
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	public void start() { //Start program
		GL20.glUseProgram(programID);
	}
	
	public void stop() { //Stop program
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() { //Clean memory
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes(); //Bind inputs to attributes of VAO. Protected = sub classes may access, each one may vary in sub class so there is no implementation, only definition
	
	protected void bindAttribute(int attribute, String variableName) { //Bind attribute, must be done within this class as it need programID
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	
	//Load different type of variables to shader's
	protected void loadFloat(int location, float value) { //Load float uniform at location passing uniform value
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x,vector.y,vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) { //Shader's don't actually have boolean so just load 0 or 1
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer); //Store into matrix buffer
		matrixBuffer.flip(); //Make it readable
		GL20.glUniformMatrix4(location, false, matrixBuffer);
		
	}
	
	private static int loadShader(String file, int type) { //Loads up the vertexShader and fragmentShader for each subclass. Passing it name of file and type of file (vert or frag)
		//Shouldn't need to change anything ever again. Copy paste. dw
		//Opens source files
		//Read all lines
		//Connects together in one long string
		//Creates new vertex or fragment shader 
		//Attach string of source code to it
		//Compiling
		//Print any errors
		//Return ID of newly created shader
		StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
		
	}

}
