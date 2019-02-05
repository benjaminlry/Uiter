package benlry.com.uiteur;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import benlry.com.uiteur.receivers.MyBroadcastReceiver;
import benlry.com.uiteur.services.SensorService;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mp;
    private static final String ACTION_SENSOR = "benlry.com.uiter.SENSOR";

    private AudioManager audio;
    private int maxVolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Creating");

        mp = MediaPlayer.create(this, R.raw.a);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.playAndPause);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();
                } else {
                    mp.start();
                }
            }
        });

        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(ACTION_SENSOR));

        Intent intentService = new Intent (this, SensorService.class);
        startService(intentService);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, getString(R.string.debug_start_msg));
        //mp.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        //mp.pause();
        super.onPause();
    }


    public void setVolume(float currentValue) {

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audio.getStreamMaxVolume(audio.STREAM_MUSIC);

        float valueOutPercent = 0;

        if(currentValue >= 0) {
            valueOutPercent = (currentValue*maxVolume)/170;
            valueOutPercent += (maxVolume/2);
            Log.d("testVolume", String.valueOf(Math.round(valueOutPercent)));
        }
        else {
            float valuePos = currentValue * -1;
            valueOutPercent = (valuePos*maxVolume)/170;
            valueOutPercent = (maxVolume/2) - valueOutPercent;
            Log.d("testVolume", String.valueOf(Math.round(valueOutPercent)));
        }
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, Math.round(valueOutPercent), AudioManager.FLAG_SHOW_UI);

    }

}
