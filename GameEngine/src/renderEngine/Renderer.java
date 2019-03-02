package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import models.TexturedModel;

public class Renderer {
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); //Clears previous colour
		GL11.glClearColor(1, 1, 1, 1); //'Paint screen' RGB, alpha (This case solid red)
	}
	
	public void render(TexturedModel texturedModel) { //'RawModel' ||| 'TexturedModel' in replace of 'int', 'float' or the like
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID()); //Bind VAO so we can read it
		GL20.glEnableVertexAttribArray(0); //Activate attrib array 0 (vertex co-ordinates)
		GL20.glEnableVertexAttribArray(1); //Activate attrib array 1 (textureCoords)
		GL13.glActiveTexture(GL13.GL_TEXTURE0);//Activates the texture bank 0 so that fragment shader has access to it (sampler2D)
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID()); //Bind texture to texture bank by giving it texture ID
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draw the triangles based on the VAO starting at position '0' in the Attrib array (VBO~)
		GL20.glDisableVertexAttribArray(0); //Disable attrib list
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0); //Unbind VAO
	}

}
