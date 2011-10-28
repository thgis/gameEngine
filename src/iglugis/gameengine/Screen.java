package iglugis.gameengine;

import iglugis.gameengine.interfaces.IGame;

public abstract class Screen {
	protected final IGame game;
	
	public Screen(IGame game)
	{
		this.game = game;
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void present(float deltaTime);
	
	public abstract void pause();
	
	public abstract void resume();
	
	public abstract void dispose();
}
