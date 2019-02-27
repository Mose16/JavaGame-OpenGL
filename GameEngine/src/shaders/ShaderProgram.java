package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram { //Abstract means it represents what all shader programs should have. Other shader classes inherit from this?
	
	//These are for the next gen class. Inherited
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram(String vertexFile, String fragmentFile) { //Constructor
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER); //Creates vertex shader and returns ID for that file, loads file into OpenGL
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER); //Creates fragment shader and returns ID for that file, loads file into OpenGL
		programID = GL20.glCreateProgram(); //Creates program to tie vert and frag together
		GL20.glAttachShader(programID, vertexShaderID); //Attach shaders to program
		GL20.glAttachShader(programID, fragmentShaderID);
		GL20.glLinkProgram(programID); //Link
		GL20.glValidateProgram(programID); //Validate program
		bindAttributes();
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
