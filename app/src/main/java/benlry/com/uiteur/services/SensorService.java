package benlry.com.uiteur.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import benlry.com.uiteur.listeners.SensorServiceListener;

public class SensorService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("test","service started");
        SensorServiceListener sensorServiceListener = new SensorServiceListener();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(sensorServiceListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }
}
