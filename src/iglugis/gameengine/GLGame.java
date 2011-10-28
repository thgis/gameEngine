package iglugis.gameengine;

import iglugis.gameengine.Input.AndInput;
import iglugis.gameengine.interfaces.IAudio;
import iglugis.gameengine.interfaces.IFileIO;
import iglugis.gameengine.interfaces.IGame;
import iglugis.gameengine.interfaces.IInput;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GLGame extends Activity implements IGame, Renderer {
	enum GLGameState{
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	GLGraphics glGraphics;
	IAudio audio;
	IInput input;
	IFileIO fileIO;
	Screen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		
		glGraphics = new GLGraphics(glView);
		fileIO = new AndFileIO(getAssets());
		audio = new AndAudio(this);
		input = new AndInput(this,glView,1,1);
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"GLGame");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glGraphics.setGL(gl);
		
		synchronized (stateChanged) {
			if(state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		GLGameState state = null;
		
		synchronized (stateChanged) {
			state = this.state;
		}
		
		if(state == GLGameState.Running)
		{
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		
		if(state == GLGameState.Paused)
		{
			screen.pause();
			synchronized (stateChanged) {
			this.state = GLGameState.Idle;
			stateChanged.notifyAll();
			}
		}
		
		if(state == GLGameState.Finished){
			screen.pause();
			screen.dispose();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}
	
	@Override
	protected void onPause() {
		synchronized (stateChanged) {
			if(isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			while(true)
			{
				try{
					stateChanged.wait();
					break;
				}catch(InterruptedException e)
				{
				}
			}
		}
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}
	
	public GLGraphics getGLGraphics(){
		return glGraphics;
	}
	
	public IInput getInput() {
		return input;
	}
	
	public IFileIO getFileIO() {
		return fileIO;
	}
	
	public IAudio getAudio() {
		return audio;
	}
	
	public Context getMyContext()
	{
		return this.getApplicationContext();
	}
	
	public void setScreen(Screen screen) {
		if(screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
		this.screen = screen;
	}
	
	public Screen getCurrentScreen(){
		return screen;
	}
}