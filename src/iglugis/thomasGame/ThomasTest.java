package iglugis.thomasGame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import android.util.Log;

import iglugis.gameengine.FPSCounter;
import iglugis.gameengine.GLGame;
import iglugis.gameengine.GLGraphics;
import iglugis.gameengine.Screen;
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
	Car car;
	TextureRegion carTexture;
	Texture texture;
	GLGraphics glGraphics;
	FloatBuffer vertices;
	FPSCounter fpsCounter;
	final int VERTEX_SIZE = (2+4)*4;
	ShortBuffer indices;
	boolean startedShop = false;
	
	Vector2 touchPos = new Vector2();

	public ThomasTestScreen(IGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		texture = new Texture((GLGame)game, "car.jpg");
		carTexture = new TextureRegion(texture, x, y, width, height)
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*VERTEX_SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		//vertices.put( new float[] { 0.0f,	0.0f,	1,0,0,1,
		//							319.0f,	0.0f,	0,1,0,1,
		//							160.0f,	479.0f,	0,0,1,1});
		//short[] indices ={ 0,1,2,2,3,0};
		vertices.put( new float[] { 0.0f,	0.0f,	1,1,1,1,
				0f,	10.0f,	1,1,1,1,
				7.0f,	10.0f,	1,1,1,1,
				7.0f, 0.0f, 1,1,1,1
				});
		vertices.flip();
		byteBuffer = ByteBuffer.allocateDirect(6*2);
		byteBuffer.order(ByteOrder.nativeOrder());
		indices = byteBuffer.asShortBuffer();
		indices.put(new short[] { 0,1,2,2,3,0});
		indices.flip();
		
		
		fpsCounter = new FPSCounter();
		car = new Car(100,100,7,10);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i); 
	         
	        touchPos.x = (event.x / (float) glGraphics.getWidth());//* WORLD_WIDTH; 
	        touchPos.y = (1 - event.y / (float) glGraphics.getHeight());// * WORLD_HEIGHT;
		
			// check om vi hælder fremad?
			 if(event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED ) {
				 float change = (float)event.x / (float)glGraphics.getWidth() - 0.5f;
				 Log.d("Car update", "Updating with: " + String.valueOf(change));
				car.updateWheel(-change);
				//Log.d("Car update:","X : " + String.valueOf(car.accel.x) + "Y :" + String.valueOf(car.accel.y));
			}
		}
		 // check om vi hælder fremad
		 float acc = game.getInput().getAccelY() * 10;
		 if(acc!=0)
		 {
			 if(-0.1 < acc && acc < 0.1)
				 acc = 0;
			 car.updateSpeed(acc);
			 Log.d("Car update", "Updating acc: " + String.valueOf(acc));
		 }
		car.update(deltaTime);
		
//		if(car.position.y > 300 && !startedShop)
//		{
//			startedShop = true;
//			Intent intent = new Intent(game.getMyContext(),TestMenu.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			game.getMyContext().startActivity(intent);
//			car.position.y = 0;
//		}
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
		
		
		gl.glTranslatef(car.position.x, car.position.y, 0);
		gl.glRotatef(car.angle * Vector2.TO_DEGREES + 90, 0, 0, 1);
		//Log.d("Present car angle","Angle: " + String.valueOf(car.angle));
		
		vertices.position(0);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		vertices.position(2);
		gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		
		//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);
		
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