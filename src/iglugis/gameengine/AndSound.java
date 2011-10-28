package iglugis.gameengine;

import android.media.SoundPool;
import iglugis.gameengine.interfaces.ISound;

public class AndSound implements ISound{
	int soundId;
	SoundPool soundPool;
	
	public AndSound(SoundPool soundPool, int soundId)
	{
		this.soundId = soundId;
		this.soundPool = soundPool;
	}
	
	@Override
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		soundPool.unload(soundId);
	}

}
