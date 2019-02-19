package benlry.com.uiteur.listeners;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import benlry.com.uiteur.services.HardwareService;

public class SensorServiceListener implements SensorEventListener {

    private HardwareService mService;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mService = new HardwareService();
        this.mService.notifyOrientation(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
