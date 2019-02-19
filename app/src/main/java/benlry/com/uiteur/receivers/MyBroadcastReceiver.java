package benlry.com.uiteur.receivers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import benlry.com.uiteur.MainActivity;
import benlry.com.uiteur.services.HardwareService;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {

    private MainActivity mActivity;


    public MyBroadcastReceiver(MainActivity main){
        this.mActivity = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction() == HardwareService.ACTION_LOCATION) {
            this.mActivity.displayLocation(intent.getDoubleExtra("lat",0.0), intent.getDoubleExtra("long",0.0));
        } else {
            float currentValue = intent.getFloatExtra("currentValue",0);

            if(mActivity != null){
                mActivity.setVolume(currentValue);
            } else {
                Log.i("test","failed");
            }
        }


    }
}
