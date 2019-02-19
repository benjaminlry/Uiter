package benlry.com.uiteur;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import benlry.com.uiteur.receivers.MediaPlayerServiceReceiver;
import benlry.com.uiteur.receivers.MyBroadcastReceiver;
import benlry.com.uiteur.services.HardwareService;
import benlry.com.uiteur.services.MediaLauncher;
import benlry.com.uiteur.services.MediaPlayerService;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

import static benlry.com.uiteur.services.HardwareService.ACTION_LOCATION;
import static benlry.com.uiteur.services.HardwareService.ACTION_SENSOR;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mp;
    private MediaLauncher ml;
    private static final int PERMISSIONS_REQUEST_CODE = 101;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MyBroadcastReceiver mBroadcastReceiver;


    private AudioManager audio;
    private int maxVolume;

    private static final String STATE_FILTER = "benlry.com.uiter.STATE";
    private Context context = this;
    private TextView playlistv;
    //private boolean isRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("isRun", "Create !");

        mp = MediaPlayer.create(this, R.raw.a);
        ml = new MediaLauncher();
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.playAndPause);
        final Button playlist = findViewById(R.id.getPlaylist);
        playlistv = findViewById(R.id.playlisttv);

        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("mp_play"));
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ml.isPlaying(mp)) {
                    ml.pause(mp);
                } else {
                    ml.start(mp);
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Log.d("Permissions", "ACCESS_FINE_LOCATION authorization granted");
        }

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.FOREGROUND_SERVICE},
                PERMISSIONS_REQUEST_CODE);

       MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver(this);

       MediaPlayerService mediaPlayerService = new MediaPlayerService();

       MediaPlayerServiceReceiver mediaPlayerServiceReceiver = new MediaPlayerServiceReceiver(mediaPlayerService,this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mediaPlayerServiceReceiver, new IntentFilter("mp_play"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mediaPlayerServiceReceiver, new IntentFilter("playlist"));

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(ACTION_SENSOR));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(ACTION_LOCATION));

        Intent intentHardwareService = new Intent(this, HardwareService.class);

        startService(intentHardwareService);

        Intent mediaPlayer = new Intent(this, MediaPlayerService.class);

        startService(mediaPlayer);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        //mp.start();
        Log.i("isRun", "Start !");
        super.onStart();

        //isRun = true;
    }

    @Override
    protected void onPause() {
        Log.i("isRun", "Pause !");
        //mp.pause();

        //isRun = true;
        super.onPause();
    }


    public void setVolume(float currentValue) {
        //if (isRun){
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
        //}
    }

    public void displayLocation(double Lat, double Long) {
        TextView txtLong = (TextView) findViewById(R.id.txtLong);
        TextView txtLat = (TextView) findViewById(R.id.txtLat);

        txtLong.setText("Longitude :"+Long);
        txtLat.setText("Latitude :"+Lat);
    }

    public void displayPlaylist(String playlist) {
        playlistv.setText(playlist);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i("test","permissions");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length == 3) {
                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                        // FOREGROUND_SERVICE only available from API 28.
                        Log.d(LOG_TAG, "Overriding FOREGROUND_SERVICE permission");
                        grantResults[2] = PackageManager.PERMISSION_GRANTED;
                    }

                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                        Log.i(LOG_TAG, "Authorizations granted");

                        this.mBroadcastReceiver = new MyBroadcastReceiver(this);
                        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver,
                                new IntentFilter(LocationService.ACTION_LOCATION));

                        Intent intent = new Intent(this, LocationService.class);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        } else {
                            startService(intent);
                        }
                    } else if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Log.e(LOG_TAG, "ACCESS_COARSE_LOCATION not granted");
                    } else if(grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        Log.e(LOG_TAG, "ACCESS_FINE_LOCATION not granted");
                    } else if(grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                        Log.e(LOG_TAG, "FOREGROUND_SERVICE not granted");
                    } else  {
                        Log.e(LOG_TAG, "Unknown authorization not granted");
                    }
                } else {
                    Log.e(LOG_TAG, "Bad response format to authorization request");
                }
                break;

            default:
                Log.e(LOG_TAG, "Authorization request not answered");
                break;
        }
    }*/

}
