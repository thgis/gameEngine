package iglugis.glbasics;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import iglugis.gameengine.GLGame;
import iglugis.gameengine.GLGraphics;
import iglugis.gameengine.Screen;
import iglugis.gameengine.interfaces.IGame;

public class GLGameTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new TestScreen(this);
	}

}

class TestScreen extends Screen {
	GLGraphics glGraphics;
	Random rand = new Random();
	
	public TestScreen(IGame game)
	{
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClearColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
