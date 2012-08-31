package iglugis.GameScreens;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import iglugis.gamedev2d.Car;
import iglugis.gameengine.Camera2D;
import iglugis.gameengine.FPSCounter;
import iglugis.gameengine.GLGame;
import iglugis.gameengine.GLGraphics;
import iglugis.gameengine.GLText;
import iglugis.gameengine.Screen;
import iglugis.gameengine.SpriteBatch;
import iglugis.gameengine.Texture;
import iglugis.gameengine.TextureRegion;
import iglugis.gameengine.interfaces.IGame;
import iglugis.gameengine.interfaces.IInput.TouchEvent;
import iglugis.math.Vector2;

public class GTAGame extends GLGame {
	
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new GTAGameScreen(this);
	}

}


class GTAGameScreen extends Screen {
	private static final float WORLD_WIDTH = 320;
	private static final float WORLD_HEIGHT = 480;
	Car car;
	TextureRegion carTexture;
	TextureRegion backGroundTextureRegion;
	Texture texture;
	Texture textureBackGround;
	SpriteBatch batcher;
	GLText text;
	GLGraphics glGraphics;
	FPSCounter fpsCounter;
	Camera2D camera;
	Vector2 touchPos = new Vector2();

	public GTAGameScreen(IGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		texture = new Texture((GLGame)game, "cars2.png");
		textureBackGround = new Texture((GLGame)game, "basemap.png");
		
		carTexture = new TextureRegion(texture, 0, 0, 210, 135);
		backGroundTextureRegion = new TextureRegion(textureBackGround, 0, 0, 512, 512);
		
		batcher = new SpriteBatch(glGraphics, 4);
		
		text = new GLText((GLGame)game, glGraphics);
		
		text.load("Roboto-Regular.ttf", 12, 2, 2);
		
		camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT); 
		
		fpsCounter = new FPSCounter();
		car = new Car(100,100,100,50);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			touchPos.x = (event.x / (float) glGraphics.getWidth());// *
																	// WORLD_WIDTH;
			touchPos.y = (1 - event.y / (float) glGraphics.getHeight());// *
																		// WORLD_HEIGHT;
		}
		
		float accY = game.getInput().getAccelY() * 0.5f;
		if (-1 < accY && accY < 1)
			accY = 0;
		car.updateWheel(-accY);

		// check om vi hælder fremad
		float acc = game.getInput().getAccelX() * 10;
		if (-10 < acc && acc < 10)
			acc = 0;
		car.updateSpeed(acc);
		car.update(deltaTime);

		camera.position.set(car.position);
		float zoomAdd = Math.abs(car.getSpeed()) * 0.5f;
		if(zoomAdd > 5)
			zoomAdd = 5;
		camera.zoom = 1 + zoomAdd;
	}

	@Override
	public void present(float deltaTime) {
		Draw3D();
		Draw2D();
	}
	
	private void Prepare2D()
	{
		GL10 gl = glGraphics.getGL();
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl,WORLD_WIDTH, 0.0f, WORLD_HEIGHT, 0.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);

		gl.glDisable(GL10.GL_DEPTH_TEST);
	}
	
	private void Draw2D()
	{
		Prepare2D();
		
		fpsCounter.logFrame();
		text.begin(1.0f,1.0f,0.0f,1.0f);
		text.draw(String.format("Speed: %f",car.getSpeed()), 0.0f, 0.0f);
		text.end();
	}
	
	private void Prepare3D()
	{
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices(); 
		
		gl.glEnable(GL10.GL_BLEND); 
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 
		gl.glEnable(GL10.GL_TEXTURE_2D); 
	}
	
	private void Draw3D()
	{
		Prepare3D();
		
		batcher.beginBatch(textureBackGround);
		for(int x = 0; x< 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				batcher.drawSprite(x*1024, y*1024, 1024, 1024, backGroundTextureRegion);				
			}
		}
		batcher.endBatch();
		batcher.beginBatch(texture);
		batcher.drawSprite(car.position.x, car.position.y, car.bounds.width, car.bounds.height, car.angle * Vector2.TO_DEGREES, carTexture);
		batcher.endBatch();
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