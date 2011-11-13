package iglugis.thomasGame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import android.util.Log;

import iglugis.gameengine.Camera2D;
import iglugis.gameengine.FPSCounter;
import iglugis.gameengine.GLGame;
import iglugis.gameengine.GLGraphics;
import iglugis.gameengine.Screen;
import iglugis.gameengine.SpriteBatcher;
import iglugis.gameengine.Texture;
import iglugis.gameengine.TextureRegion;
import iglugis.gameengine.interfaces.IGame;
import iglugis.gameengine.interfaces.IInput.TouchEvent;
import iglugis.math.Vector2;

public class ThomasTest extends GLGame {
	
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new ThomasTestScreen(this);
	}

}


class ThomasTestScreen extends Screen {
	private static final float WORLD_WIDTH = 320;
	private static final float WORLD_HEIGHT = 480;
	Car car;
	TextureRegion carTexture;
	TextureRegion backGroundTextureRegion;
	Texture texture;
	Texture textureBackGround;
	SpriteBatcher batcher;
	GLGraphics glGraphics;
	FloatBuffer vertices;
	FPSCounter fpsCounter;
	final int VERTEX_SIZE = (2+4)*4;
	ShortBuffer indices;
	boolean startedShop = false;
	
	Camera2D camera;
	
	Vector2 touchPos = new Vector2();

	public ThomasTestScreen(IGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		texture = new Texture((GLGame)game, "cars2.png");
		textureBackGround = new Texture((GLGame)game, "basemap.png");
		
		carTexture = new TextureRegion(texture, 0, 0, 210, 135);
		backGroundTextureRegion = new TextureRegion(textureBackGround, 0, 0, 512, 512);
		
		batcher = new SpriteBatcher(glGraphics, 4);
		
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

			// // check der bliver trykket på skærmen
			// if(event.type == TouchEvent.TOUCH_DOWN || event.type ==
			// TouchEvent.TOUCH_DRAGGED ) {
			// float change = (float)event.x / (float)glGraphics.getWidth() -
			// 0.5f;
			// car.updateWheel(-change);
			// //Log.d("Car update:","X : " + String.valueOf(car.accel.x) +
			// "Y :" + String.valueOf(car.accel.y));
			// }
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
		float zoomAdd = Math.abs(car.speed) * 0.5f;
		if(zoomAdd > 5)
			zoomAdd = 5;
		camera.zoom = 1 + zoomAdd;
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices(); 
		
		gl.glEnable(GL10.GL_BLEND); 
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 
		gl.glEnable(GL10.GL_TEXTURE_2D); 
		
		batcher.beginBatch(textureBackGround);
		batcher.drawSprite(0, 0, 1024, 1024, backGroundTextureRegion);
		batcher.drawSprite(1024, 0, 1024, 1024, backGroundTextureRegion);
		batcher.drawSprite(0, 1024, 1024, 1024, backGroundTextureRegion);
		batcher.drawSprite(1024, 1024, 1024, 1024, backGroundTextureRegion);
		batcher.endBatch();
		
		batcher.beginBatch(texture);
		batcher.drawSprite(car.position.x, car.position.y, car.bounds.width, car.bounds.height, car.angle * Vector2.TO_DEGREES, carTexture);
		batcher.endBatch();
		
		fpsCounter.logFrame();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		startedShop = false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}