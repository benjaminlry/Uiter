package benlry.com.uiteur.receivers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import benlry.com.uiteur.MainActivity;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {

    MainActivity main;

    public MyBroadcastReceiver(MainActivity main){
        this.main = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        float currentValue = intent.getFloatExtra("currentValue",0);

        Log.d("currentValue", String.valueOf(currentValue));

        if(main != null){
            main.setVolume(currentValue);
        } else {
            Log.i("test","failed");
        }
    }
}
