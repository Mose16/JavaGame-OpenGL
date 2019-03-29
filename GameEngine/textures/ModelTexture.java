package textures;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1; //How far can the camera be from reflection to see difference
	private float reflectivity = 0; //How reflective is the surface (how bright is the reflection?)
	
	public ModelTexture(int id) { //Constructor
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	

}
