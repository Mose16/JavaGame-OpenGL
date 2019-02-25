package renderEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader { //Creates data for VAO and VBO to be stored in RawModel object
	
	private List<Integer> vaos = new ArrayList<Integer>(); //Keep track of VAO and VBO's. Not sure about the syntax
	private List<Integer> vbos = new ArrayList<Integer>();
	
	
	public RawModel loadToVAO(float[] positions) { //Creating RawModel object? 'float[] position' creates an array of float variables called position
		int vaoID = createVAO(); //Storing vaoID in vaoID variable through 'createVAO' return function
		storeDataInAttributeList(0, positions); //Store positions in attribute list 0
		unbindVAO(); //Clean it up?
		return new RawModel(vaoID, positions.length/3); //Create the new RawModel object and give it the vaoID and number of vertex's. -- length of positions /3 because there are 3 floats: x,y,z
	}
	
	public void cleanUp() { //Delete VB and VAO's
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	private int createVAO() { //Create empty VAO
		int vaoID = GL30.glGenVertexArrays(); //Creates VAO and returns the ID to vaoID
		vaos.add(vaoID); //Add to vao list
		GL30.glBindVertexArray(vaoID); //Activate VAO (vaoID to tell it which one)
		return vaoID; //Refer to above function to see use
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data) { //Store data in VAO attribute list, passing the function list number and the data
		int vboID = GL15.glGenBuffers(); //Create empty VBO
		vbos.add(vboID); //Add to vbo list
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Activate VBO (type of VBO, ID)
		FloatBuffer buffer = storeDataInFloatBuffer(data); //Create float buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Put buffer in VBO, (type, data, usage - we aren't going to edit it so it is STATIC_DRAW)
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0); //(Attribute position, size (xyz = 3), type of data, is normalized?, distance between each vert (space between), offset
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0); //Unbind currently bound VAO -- only 1 VAO at a time?
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) { //VBO data must be in buffer form not array. This converts array -> buffer
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); //Create's empty buffer. Requires a size variable
		buffer.put(data); //Put data in buffer
		buffer.flip(); //Finish writing, set it to read
		return buffer;
	}

}
