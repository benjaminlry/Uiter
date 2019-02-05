package benlry.com.uiteur;

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Creating");

        mp = MediaPlayer.create(this, R.raw.a);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.playAndPause);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                }else{
                    mp.start();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG,getString(R.string.debug_start_msg));
        mp.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mp.pause();
        super.onPause();
    }
}
