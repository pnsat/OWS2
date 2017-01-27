package com.daydr3am.lib;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.daydr3am.lib.GPSTracker;
import com.daydr3am.lib.ServiceOperation;
import com.daydr3am.lib.ServiceOperationListener;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectService extends android.app.Service implements ServiceOperationListener, Runnable {
   static int connectionLostCount = 0;
   static Thread single = null;
   long MIN_TIME_BW_UPDATES = 60000L;
   long TOGGLE_MIN_TIME_BW_UPDATES = 5000L;
   GPSTracker gps;
   Handler handler = new Handler();
   double lat = 0.0D;
   double lng = 0.0D;
   Runnable runSend = null;
   Runnable runToggle = null;

   private void CheckSoftware() {
      Log.v("hello", "CheckSoftware");

      try {
         ServiceOperation var2 = new ServiceOperation("http://www.owsth.com/owstopup/api_software/softwareapi.php");
         var2.listener = this;
         SharedPreferences var4 = this.getSharedPreferences("hello", 0);
         String var5 = var4.getString("HARDWARE_ID", "new");
         String var6 = var4.getString("KEYTABLE", "new");
         ArrayList var7 = new ArrayList();
         var7.add(new BasicNameValuePair("username", var5));
         var7.add(new BasicNameValuePair("password", var6));
         var7.add(new BasicNameValuePair("type", "kiosk"));
         var2.nameValuePairs = var7;
         var2.beforeExcute(new String[]{"check"});
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }

   private void GetGPS(boolean var1) {
      if(this.gps.canGetLocation()) {
         this.gps.getLocation();
         double var3 = this.gps.getLatitude();
         double var5 = this.gps.getLongitude();

         try {
            Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
         } catch (Exception var8) {
            ;
         }

         this.lat = var3;
         this.lng = var5;
      } else {
         this.gps.showSettingsAlert();
      }
   }

   private void InitServiceTracking() {
      this.gps = new GPSTracker(this);
      final Handler var1 = new Handler();
      this.runToggle = new Runnable() {
         public void run() {
            ConnectService.this.GetGPS(false);
            var1.postDelayed(ConnectService.this.runToggle, ConnectService.this.TOGGLE_MIN_TIME_BW_UPDATES);
         }
      };
      var1.postDelayed(this.runToggle, this.TOGGLE_MIN_TIME_BW_UPDATES);
      final Handler var3 = new Handler();
      this.runSend = new Runnable() {
         public void run() {
            ConnectService.this.GetGPS(true);
            var3.postDelayed(ConnectService.this.runSend, ConnectService.this.MIN_TIME_BW_UPDATES);
         }
      };
      var3.postDelayed(this.runSend, this.MIN_TIME_BW_UPDATES);
   }

   private int compareVersionNames(String var1, String var2) {
      String[] var3 = var1.split("\\.");
      String[] var4 = var2.split("\\.");
      int var5 = Math.min(var3.length, var4.length);
      int var6 = 0;

      byte var7;
      while(true) {
         var7 = 0;
         if(var6 >= var5) {
            break;
         }

         int var8 = Integer.valueOf(var3[var6]).intValue();
         int var9 = Integer.valueOf(var4[var6]).intValue();
         if(var8 < var9) {
            var7 = -1;
            break;
         }

         if(var8 > var9) {
            var7 = 1;
            break;
         }

         ++var6;
      }

      if(var7 == 0 && var3.length != var4.length) {
         if(var3.length <= var4.length) {
            return -1;
         }

         var7 = 1;
      }

      return var7;
   }

   private void rebootDevice() {
      try {
         Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"}).waitFor();
      } catch (Exception var2) {
         Log.d("debug", "Could not reboot", var2);
      }
   }

   public void ServiceconnectionFail(String var1) {
      if(!var1.equalsIgnoreCase("check")) {
         Log.v("hello", "return check clear");
         Editor var3 = this.getSharedPreferences("hello", 0).edit();
         var3.putString("DownloadVersion", (String)null);
         var3.commit();
      }

   }

   public void ServiceconnectionFinish(String var1, String var2) {
      Editor var3 = this.getSharedPreferences("hello", 0).edit();
      SharedPreferences var4 = this.getSharedPreferences("hello", 0);
      if(var2.equalsIgnoreCase("check")) {
         Log.v("hello", "return check" + var1);

         try {
            JSONObject var9 = new JSONObject(var1);
            String var12 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            Log.v("hello", "return check" + var4.getString("DownloadVersion", (String)null));
            if(var9.optString("status").equalsIgnoreCase("OK") && this.compareVersionNames(var12, var9.optString("setversion")) == -1 && var4.getString("DownloadVersion", (String)null) == null) {
               ServiceOperation var16 = new ServiceOperation(var9.optString("link").replace("https", "http"));
               var16.listener = this;
               var16.dataPath = "/mnt/sdcard/Resource/" + var9.optString("setversion") + ".apk";
               Log.v("hello", "set path check " + var16.dataPath);
               String[] var18 = new String[]{var9.optString("setversion")};
               var16.beforeExcute(var18);
               var3.putString("DownloadVersion", var9.optString("setversion"));
               var3.commit();
               return;
            }

            if(this.compareVersionNames(var12, var9.optString("setversion")) == 0) {
               var3.putString("DownloadVersion", (String)null);
               var3.commit();
               return;
            }
         } catch (JSONException var19) {
            var19.printStackTrace();
            return;
         } catch (NameNotFoundException var20) {
            var20.printStackTrace();
            return;
         }
      } else {
         Log.v("hello", "check save finish");
         var3.putBoolean("DownloadFinish", true);
         var3.commit();
      }

   }

   public void callUSSD(int var1) {
      String var2 = this.getSimOperator();
      Uri var4;
      if(var2.length() == 0) {
         if(var1 == 1) {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "101" + Uri.encode("#"));
         } else {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "102" + Uri.encode("#"));
         }
      } else if(var2.equalsIgnoreCase("ais")) {
         if(var1 == 1) {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "121" + Uri.encode("#"));
         } else {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "545" + Uri.encode("#"));
         }
      } else {
         boolean var3 = var2.equalsIgnoreCase("true-h");
         var4 = null;
         if(var3) {
            if(var1 == 1) {
               var4 = Uri.parse("tel:" + Uri.encode("#") + "123" + Uri.encode("#"));
            } else {
               var4 = Uri.parse("tel:" + Uri.encode("*") + "933" + Uri.encode("#"));
            }
         }
      }

      if(var4 != null) {
         Intent var5 = new Intent("android.intent.action.CALL");
         var5.setData(var4);
         var5.addFlags(268435456);
         var5.addFlags(4);
         this.startActivity(var5);
      } else {
         Log.e("hello", "unknown operator");
      }
   }

   public String getSimOperator() {
      return ((TelephonyManager)this.getSystemService("phone")).getSimOperatorName();
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate(Bundle var1) {
      super.onCreate();
      Log.v("hello", "create thread");
   }

   public void onStart(Intent var1, int var2) {
      Log.v("hello", "start command");
      if(single == null) {
         this.InitServiceTracking();
         single = new Thread(this);
         single.start();
      }

   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
