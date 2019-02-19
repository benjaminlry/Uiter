package benlry.com.uiteur.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MediaLauncher extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void start(MediaPlayer mp) {
        mp.start();
    }

    public void pause(MediaPlayer mp) {
        mp.pause();
    }

    public Boolean isPlaying(MediaPlayer mp) {
        if(mp.isPlaying()) {
            return true;
        }
        else {
            return false;
        }
    }
}
