package com.daydr3am.lib;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class GPSTracker extends android.app.Service implements LocationListener {
   private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10L;
   private static final long MIN_TIME_BW_UPDATES = 60000L;
   float accuracy = 0.0F;
   float bearing = 0.0F;
   boolean canGetLocation = false;
   boolean isGPSEnabled = true;
   boolean isNetworkEnabled = true;
   double latitude;
   Location location;
   protected LocationManager locationManager;
   double longitude;
   private final Context mContext;
   int satelliteCount = 0;
   float speed;

   public GPSTracker(Context var1) {
      this.mContext = var1;
      this.getLocation();
   }

   public boolean canGetLocation() {
      return this.canGetLocation;
   }

   public float getAccuracy() {
      return this.accuracy;
   }

   public float getBearing() {
      return this.bearing;
   }

   public String getLat() {
      return Data.SAVE_LOCATION_LAT;
   }

   public double getLatitude() {
      if(this.location != null) {
         this.latitude = this.location.getLatitude();
      }

      return this.latitude;
   }

   public Location getLocation() {
      // $FF: Couldn't be decompiled
   }

   public String getLong() {
      return Data.SAVE_LOCATION_LONG;
   }

   public double getLongitude() {
      if(this.location != null) {
         this.longitude = this.location.getLongitude();
      }

      return this.longitude;
   }

   public int getSatelliteCount() {
      return this.satelliteCount;
   }

   public float getSpeed() {
      return this.speed;
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onLocationChanged(Location var1) {
   }

   public void onProviderDisabled(String var1) {
   }

   public void onProviderEnabled(String var1) {
   }

   public void onStatusChanged(String var1, int var2, Bundle var3) {
   }

   public void setSatelliteCount(int var1) {
      this.satelliteCount = var1;
   }

   public void setSpeed(float var1) {
      this.speed = var1;
   }

   public void showSettingsAlert() {
      Builder var1 = new Builder(this.mContext);
      var1.setTitle("GPS is settings");
      var1.setMessage("GPS is not enabled. Do you want to go to settings menu?");
      var1.setPositiveButton("Settings", new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            Intent var3 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            GPSTracker.this.mContext.startActivity(var3);
         }
      });
      var1.setNegativeButton("Cancel", new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var1.cancel();
         }
      });
      var1.show();
   }

   public void stopUsingGPS() {
      if(this.locationManager != null) {
         this.locationManager.removeUpdates(this);
      }

   }

   private static class Data {
      public static String SAVE_LOCATION_LAT;
      public static String SAVE_LOCATION_LONG;
      public static String SAVE_LOCATION_SPEED;
      public static String SAVE_LOCATION_TYPE;
   }
}
