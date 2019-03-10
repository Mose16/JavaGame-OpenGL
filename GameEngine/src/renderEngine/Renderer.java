package renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import toolBox.Maths;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;
	
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader) { //Constructor
		createProjectionMatrix(); //Matrix only needs to be set up once
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix); //Give it to the shader permanently
		shader.stop();
	}
	
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT); //Clears previous colour
		GL11.glClearColor(1, 1, 1, 1); //'Paint screen' RGB, alpha (This case solid red)
	}
	
	public void render(Entity entity, StaticShader shader) { //Pass an entity to render and a shader, also pass a staticShader so that we can upload the entity transformation
		TexturedModel texturedModel = entity.getModel(); //Get the textured model from the entity
		RawModel model = texturedModel.getRawModel(); //Get the raw model data
		GL30.glBindVertexArray(model.getVaoID()); //Bind VAO so we can read it
		GL20.glEnableVertexAttribArray(0); //Activate attrib array 0 (vertex co-ordinates)
		GL20.glEnableVertexAttribArray(1); //Activate attrib array 1 (textureCoords)
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()); //Get values for transforamatuion matrix
		shader.loadTransformationMatrix(transformationMatrix); //Send matrix to shader
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//Activates the texture bank 0 so that fragment shader has access to it (sampler2D)
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID()); //Bind texture to texture bank by giving it texture ID
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draw the triangles based on the VAO starting at position '0' in the Attrib array (VBO~)
		GL20.glDisableVertexAttribArray(0); //Disable attrib list
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0); //Unbind VAO
	}
	
	private void createProjectionMatrix() { //Do projection math (dw about it)
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
	}

}
