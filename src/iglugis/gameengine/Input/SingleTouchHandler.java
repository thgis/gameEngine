package iglugis.gameengine.Input;

import iglugis.gameengine.interfaces.IInput.TouchEvent;

import java.util.List;

import android.view.MotionEvent;
import android.view.View;

public class SingleTouchHandler implements TouchHandler {

	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
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
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
