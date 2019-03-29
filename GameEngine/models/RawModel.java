package models;

public class RawModel { //Stores model information such as xyz of the vertex's??
	
	//Private
	private int vaoID; //Basically modelID
	private int vertexCount; //How many vertex's is the model constructed from
	
	
	//Public
	public RawModel(int vaoID, int vertexCount) { //Constructor - note 'RawModel' = class name
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	
	//Getters--- {right click, source, getters and setters}
	public int getVaoID() { //When accessing vaoID outside of class - note 'private' in variable declaration
		return vaoID;
	}

	public int getVertexCount() { //When accessing vertexCount outside of class - note 'private' in variable declaration
		return vertexCount;
	}
	
	

}
