package iglugis.gameengine.interfaces;

public interface IMusic {
	public void play();
	
	public void stop();
	
	public void pause();
	
	public void setLooping(boolean looping);
	
	public void setVolume(float volume);
	
	public boolean isPlaying();
	
	public boolean isStopped();
	
	public boolean isLooping();
	
	public void dispose();
}
