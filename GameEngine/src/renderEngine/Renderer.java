package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); //Clears previous colour
		GL11.glClearColor(1, 0, 0, 1); //'Paint screen' RGB, alpha (This case solid red)
	}
	
	public void render(RawModel model) { //'RawModel' in replace of 'int', 'float' or the like
		GL30.glBindVertexArray(model.getVaoID()); //Bind VAO so we can read it
		GL20.glEnableVertexAttribArray(0); //Activate attrib array 0 (vertex co-ordinates)
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draw the triangles based on the VAO starting at position '0' in the Attrib array (VBO~)
		GL20.glDisableVertexAttribArray(0); //Disable attrib list
		GL30.glBindVertexArray(0); //Unbind VAO
	}

}
