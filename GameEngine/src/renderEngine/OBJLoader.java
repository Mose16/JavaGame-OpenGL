package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {
	
	public static RawModel loadObjectModel(String fileName, Loader loader) { //Take in file name of obj file, and a loader to put into VAO
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj")); //Open a new file using FileReader and passing it a File
		} catch (FileNotFoundException e) {
			System.err.println("Couldn;t load file!");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr); //Allows us to read from the file
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>(); //Array of vertices
		List<Vector2f> textures = new ArrayList<Vector2f>(); //Array of texture coords
		List<Vector3f> normals = new ArrayList<Vector3f>(); //Array of normals
		List<Integer> indices = new ArrayList<Integer>(); //Array of indices
		float[] verticesArray = null; //VAO needs them as floats and ints
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		try {
			
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])); //parseFloat reads the string and converts it to float
					vertices.add(vertex);
				}else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2])); //parseFloat reads the string and converts it to float
					textures.add(texture);
					
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])); //parseFloat reads the string and converts it to float
					normals.add(normal);
					
				}else if(line.startsWith("f ")) {
					textureArray = new float[vertices.size()*2]; //Size of the arrays, *x for number of floats in each index
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}
				
			while(line != null) {
				if(!line.startsWith("f ")) { //If not f read the next line and repeat
					line = reader.readLine();
					continue;
				}
				
				//Remember this is a triangle so there are 3 indices
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine(); //Read next line and repeat
			}
			reader.close();	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//Convert vertex list into vertex array
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()]; //Potential error here
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i=0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesArray, textureArray, indicesArray); //Returns a rawModel
		
	}
	
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray,
			float[] normalsArray) { //Arranges according to indices
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1; //Index of vertex position in vertex positions list -1 because arrays start at 0 not 1
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y; //1- to invert from top left to bottom left
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;
	};
	
}