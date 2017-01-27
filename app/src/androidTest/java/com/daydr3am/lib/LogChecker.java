package com.daydr3am.lib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import com.daydr3am.lib.LogController;
import com.daydr3am.lib.Service;
import java.io.File;

public class LogChecker extends android.app.Service implements Runnable {
   public static boolean isSplash;
   static Thread single = null;
   File file;
   String[] oldListFile;

   public void cancelInput(Bundle var1) {
      try {
         Log.v("hello", "do cancel");
         String var4 = Service.cancelInputMoney(var1);
         Log.v("hello", "do cancel" + var4);
         if(var4.split("&")[0].split("=")[1].equals("OK")) {
            LogController.deletefile(var1.getString("Mobile"));
         } else {
            this.logCardFail(var1, var4);
            LogController.deletefile(var1.getString("Mobile"));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   public void cancelInputUtility(Bundle var1) {
      try {
         Log.v("hello", "do cancel");
         String var4 = Service.cancelInputMoneyUtil(var1);
         Log.v("hello", "do cancel" + var4);
         if(var4.split("&")[0].split("=")[1].equals("OK")) {
            LogController.deletefile(var1.getString("Mobile"));
         } else {
            this.logUtilFail(var1, var4);
            LogController.deletefile(var1.getString("Mobile"));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   public void logCardFail(Bundle var1, String var2) throws Exception {
      Log.v("hello", "card " + (-1 + Integer.parseInt(var1.getString("Network"))));
      Bundle var4 = new Bundle();
      var4.putString("Mobile", var1.getString("Mobile"));
      var4.putString("Word", "Log Card  " + var1.getString("Network") + " T" + var1.getString("TotalPrice") + " B" + var1.getString("TotalBank1") + " C" + var1.getString("TotalCoin1") + " " + var2);
      var4.putInt("Service", 10);
      Service.sendProblem(var4);
   }

   public void logUtilFail(Bundle var1, String var2) throws Exception {
      Bundle var3 = new Bundle();
      var3.putString("Mobile", var1.getString("Mobile"));
      var3.putString("Word", "Log Utility " + var1.getString("Network") + " T" + var1.getString("TotalPrice") + " B" + var1.getString("TotalBank1") + " C" + var1.getString("TotalCoin1") + " " + var2);
      var3.putInt("Service", 10);
      Service.sendProblem(var3);
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate(Bundle var1) {
      super.onCreate();
   }

   public void onStart(Intent var1, int var2) {
      Log.v("hello", "start command");
      if(single == null) {
         single = new Thread(this);
         single.start();
         this.file = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/");
      }

   }

   public void payMoney(Bundle var1) {
      try {
         Log.v("hello", "do pay");
         String var4 = Service.inputMoney(var1);
         Log.v("hello", "return message " + var4);
         if(var4.split("&")[0].split("=")[1].equals("OK")) {
            LogController.deletefile(var1.getString("Mobile"));
         } else {
            this.logCardFail(var1, var4);
            LogController.deletefile(var1.getString("Mobile"));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   public void payMoneyBarcode(Bundle var1) {
      try {
         Log.e("hello", "call by log");
         String var4 = Service.inputMoneyBarcode(var1);
         Log.e("hello", "return message " + var4);
         if(var4.split("&")[0].split("=")[1].equals("OK")) {
            LogController.deletefile(var1.getString("Mobile"));
         } else {
            this.logUtilFail(var1, var4);
            LogController.deletefile(var1.getString("Mobile"));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   public void payMoneyUtility(Bundle var1) {
      try {
         Log.v("hello", "do pay");
         String var4 = Service.inputMoneyUtil(var1);
         Log.v("hello", "return message " + var4);
         if(var4.split("&")[0].split("=")[1].equals("OK")) {
            LogController.deletefile(var1.getString("Mobile"));
         } else {
            this.logUtilFail(var1, var4);
            LogController.deletefile(var1.getString("Mobile"));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
