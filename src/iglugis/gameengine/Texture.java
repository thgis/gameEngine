package iglugis.gameengine;

import iglugis.gameengine.interfaces.IFileIO;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Texture {
	GLGraphics glGraphics;
	IFileIO fileIO;
	String fileName;
	int textureId;
	int minFilter;
	int magFilter;
	int glTextureWrapS;
	int glTextureWrapT;
	int width;
	int height;
	Bitmap bitmap;
	boolean isBitmap;
	
	public Texture(GLGame glGame, String fileName){
		this.glGraphics = glGame.getGLGraphics();
		this.fileIO = glGame.getFileIO();
		this.fileName = fileName;
		isBitmap = false;
		load();
	}
	public Texture(GLGame glGame, Bitmap bitmap)
	{
		this.glGraphics = glGame.getGLGraphics();
		this.bitmap = bitmap;
		isBitmap = true;
		load();
	}
	
	private void load(){
		GL10 gl = glGraphics.getGL();
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds,0);
		textureId = textureIds[0];
		
		InputStream in = null;
		try{
			if (!isBitmap)
			{
				in = fileIO.readAsset(fileName);
				bitmap = BitmapFactory.decodeStream(in);
			}
			height = bitmap.getHeight();
			width = bitmap.getWidth();
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			setFilters(GL10.GL_NEAREST,GL10.GL_NEAREST);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		} catch(IOException e)
		{
			throw new RuntimeException("Couldn't load texture '" + fileName + "'",e);
		} finally {
			if(in != null)
				try{in.close();} catch (IOException e){}
		}
	}
	
	public void reload(){
		load();
		bind();
		setFilters(minFilter,magFilter);
		setWrap(glTextureWrapS, glTextureWrapT);
		glGraphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D,0);
	}
	
	public void setFilters(int minFilter, int magFilter){
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GL10 gl = glGraphics.getGL();
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	public void bind() {
		GL10 gl = glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}
	
	public void dipose() {
		GL10 gl = glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId};
		gl.glDeleteTextures(1, textureIds,0);
	}
	public void setWrap(int glTextureWrapS, int glTextureWrapT) {
		this.glTextureWrapS = glTextureWrapS;
		this.glTextureWrapT = glTextureWrapT;
		GL10 gl = glGraphics.getGL();
	    gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, glTextureWrapS );  // Set U Wrapping
	    gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, glTextureWrapT );  // Set V Wrapping
	}
}
