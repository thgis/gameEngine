package iglugis.gameengine.interfaces;

import android.content.Context;
import iglugis.gameengine.Screen;

public interface IGame {
	public IInput getInput();
	
	public IFileIO getFileIO();
	
	//public Graphics getGraphics();
	
	public IAudio getAudio();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getStartScreen();
	
	public Context getMyContext();
}
