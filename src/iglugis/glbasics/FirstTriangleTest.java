package iglugis.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import iglugis.gameengine.FPSCounter;
import iglugis.gameengine.GLGame;
import iglugis.gameengine.GLGraphics;
import iglugis.gameengine.Screen;
import iglugis.gameengine.interfaces.IGame;

public class FirstTriangleTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		//return new TriangleScreen(this);
		return new ColorTriangleScreen(this);
	}

}

class TriangleScreen extends Screen{
	GLGraphics glGraphics;
	FloatBuffer vertices;

	public TriangleScreen(IGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3*2*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		vertices.put( new float[] { 0.0f,	0.0f,
									319.0f,	0.0f,
									160.0f,	479.0f,});
		vertices.flip();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
		gl.glColor4f(1, 0, 0, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}

class ColorTriangleScreen extends Screen{
	final int VERTEX_SIZE = (2+4)*4;
	GLGraphics glGraphics;
	FloatBuffer vertices;
	FPSCounter fpsCounter;
	
	public ColorTriangleScreen(IGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3*VERTEX_SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		vertices.put( new float[] { 0.0f,	0.0f,	1,0,0,1,
									319.0f,	0.0f,	0,1,0,1,
									160.0f,	479.0f,	0,0,1,1});
		vertices.flip();
		
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 320, 0, 480, 1, -1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		vertices.position(0);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		vertices.position(2);
		gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		fpsCounter.logFrame();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
