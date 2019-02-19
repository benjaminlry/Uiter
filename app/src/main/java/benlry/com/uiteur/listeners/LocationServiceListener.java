package benlry.com.uiteur.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import benlry.com.uiteur.services.HardwareService;


public class LocationServiceListener implements LocationListener {

    private HardwareService mService;

    public LocationServiceListener(HardwareService service) {
        this.mService = service;
    }

    public void onLocationChanged(Location location) {
        this.mService.notifyLocation(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }

}
