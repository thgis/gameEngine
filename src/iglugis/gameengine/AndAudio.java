package iglugis.gameengine;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import iglugis.gameengine.interfaces.IAudio;
import iglugis.gameengine.interfaces.IMusic;
import iglugis.gameengine.interfaces.ISound;

public class AndAudio implements IAudio {
	AssetManager assets;
	SoundPool soundPool;

	public AndAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);
	}
	
	@Override
	public IMusic newMusic(String fileName) {
		try{
			AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
			return new AndMusic(assetDescriptor);
		}catch (IOException e){
			throw new RuntimeException("Couldn't load music" + fileName + "'");
		}
	}

	@Override
	public ISound newSound(String fileName) {
		try{
			AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
			int soundID = soundPool.load(assetDescriptor, 0);
			return new AndSound(soundPool,soundID);
		}catch (IOException e){
			throw new RuntimeException("Couldn't load music" + fileName + "'");
		}
	}

}
