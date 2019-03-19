package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader { //Creates data for VAO and VBO to be stored in RawModel object
	
	private List<Integer> vaos = new ArrayList<Integer>(); //Keep track of VAO and VBO's. Not sure about the syntax
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) { //Creating RawModel object? 'float[] position' creates an array of float variables called position
		int vaoID = createVAO(); //Storing vaoID in vaoID variable through 'createVAO' return function
		bindIndicesBuffer(indices); //Bind to VAO
		storeDataInAttributeList(0, 3, positions); //Store positions in attribute list 0. 3 = size
		storeDataInAttributeList(1, 2, textureCoords); //Store textureCoords in attribute list 1. 2 = size
		storeDataInAttributeList(2, 3, normals); //Store nomrals in attribute list 2. 3 = size
		unbindVAO(); //Clean it up?
		return new RawModel(vaoID, indices.length); //Create the new RawModel object and give it the vaoID and number of vertex's
	}
	
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png")); //Load texture into ram
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	public void cleanUp() { //Delete VB and VAO's
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	private int createVAO() { //Create empty VAO
		int vaoID = GL30.glGenVertexArrays(); //Creates VAO and returns the ID to vaoID
		vaos.add(vaoID); //Add to VAO list
		GL30.glBindVertexArray(vaoID); //Activate VAO (vaoID to tell it which one)
		return vaoID; //Refer to above function to see use
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) { //Store data in VAO attribute list, passing the function list number and the data
		int vboID = GL15.glGenBuffers(); //Create empty VBO
		vbos.add(vboID); //Add to vbo list
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Activate VBO (type of VBO, ID)
		FloatBuffer buffer = storeDataInFloatBuffer(data); //Create float buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Put buffer in VBO, (type, data, usage - we aren't going to edit it so it is STATIC_DRAW)
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0); //(Attribute position, size (xyz = 3), type of data, is normalized?, distance between each vert (space between), offset
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0); //Unbind currently bound VAO -- only 1 VAO at a time?
	}
	
	private void bindIndicesBuffer(int[] indices) { //Bind indices buffer and store in VAO
		int vboID = GL15.glGenBuffers(); //Create empty VBO
		vbos.add(vboID); //Add to VBO list
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); //Bind VBO with type 'GL15.GL_ELEMENT_ARRAY_BUFFER'
		IntBuffer buffer = storeDataInIntBuffer(indices); //Convert indices array into IntBuffer
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Put buffer in VBO, (type, data, usage - we aren't going to edit it so it is STATIC_DRAW)
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) { //Store data in an IntBuffer, converts array -> IntBuffer
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length); //Create's empty IntBuffer. Requires a size variable
		buffer.put(data); //Put data in buffer
		buffer.flip(); //Finish writing, set it to read
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) { //VBO data must be in buffer form, not array. This converts array -> buffer
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); //Create's empty FlaotBuffer. Requires a size variable
		buffer.put(data); //Put data in buffer
		buffer.flip(); //Finish writing, set it to read
		return buffer;
	}

}
