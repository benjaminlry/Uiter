package benlry.com.uiteur.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import benlry.com.uiteur.listeners.LocationServiceListener;
import benlry.com.uiteur.listeners.SensorServiceListener;

public class HardwareService extends Service {

    public static final String ACTION_LOCATION = "benlry.com.localisation.LOCATION";
    public static final String ACTION_SENSOR = "benlry.com.localisation.SENSOR";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SensorServiceListener sensorServiceListener = new SensorServiceListener();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(sensorServiceListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        LocationServiceListener locationServiceListener = new LocationServiceListener(this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationServiceListener);
        } catch (SecurityException ex){
            Log.d("test", ex.getMessage());
        }

        return START_STICKY;
    }

    public void notifyLocation(Location location) {

        Intent intent = new Intent(ACTION_LOCATION);
        intent.putExtra("provider",location.getProvider());
        intent.putExtra("lat",location.getLatitude());
        intent.putExtra("long",location.getLongitude());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void notifyOrientation(SensorEvent sensorEvent) {

        float currentValue = sensorEvent.values[2];

        Intent intentSensor = new Intent(ACTION_SENSOR);
        intentSensor.putExtra("currentValue",currentValue);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intentSensor);
    }
}
