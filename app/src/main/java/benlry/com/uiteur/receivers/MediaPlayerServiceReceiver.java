package benlry.com.uiteur.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import benlry.com.uiteur.MainActivity;
import benlry.com.uiteur.services.MediaPlayerService;

public class MediaPlayerServiceReceiver extends BroadcastReceiver {

    private MediaPlayerService mService;
    private MainActivity mainActivity;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == "mp_play"){
            mService.play();
        } else {
            String playlist = intent.getStringExtra("playlist");
            if (playlist == null){
                Log.i("test","nok");
            }
            mainActivity.displayPlaylist(playlist);
        }
    }


    public MediaPlayerServiceReceiver(MediaPlayerService service,MainActivity mainActivity) {
        this.mService = service;
        this.mainActivity = mainActivity;
    }
}
