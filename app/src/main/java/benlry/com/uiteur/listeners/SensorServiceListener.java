package benlry.com.uiteur.listeners;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SensorServiceListener implements SensorEventListener {

    private static final String SENSOR_FILTER = "benlry.com.uiter.SENSOR";
    private Context context;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float currentValue = sensorEvent.values[2];

        Intent intentSensor = new Intent(SENSOR_FILTER);
        intentSensor.putExtra("currentValue",currentValue);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intentSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
