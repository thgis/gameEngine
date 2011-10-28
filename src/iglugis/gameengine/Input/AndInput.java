package iglugis.gameengine.Input;

import java.util.List;

import iglugis.gameengine.interfaces.IInput;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class AndInput implements IInput {
	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	TouchHandler touchHandler;
	
	public AndInput(Context context, View view, float scaleX, float scaleY){
		accelHandler = new AccelerometerHandler(context);
		keyHandler = new KeyboardHandler();
		if(Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new SingleTouchHandler(view,scaleX,scaleY);
		else
			touchHandler = new MultiTouchHandler(view,scaleX,scaleY);
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		//return keyHandler.isKeyPressed(keyCode);
		return false;
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAccelX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAccelY() {
		// TODO Auto-generated method stub
		
		return accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return touchHandler.getTouchEvents();
	}
}
