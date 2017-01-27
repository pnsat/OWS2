package com.daydr3am.lib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import com.daydr3am.lib.ServiceOperationListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.json.JSONException;

public class ServiceOperation extends AsyncTask {
   public static String AppName;
   public static boolean debug = true;
   public static int loadingIcon;
   public Activity ac;
   public boolean canResumeDownload = false;
   public String dataPath;
   ProgressDialog dialog;
   public String entity;
   public ServiceOperationListener listener;
   public List nameValuePairs;
   boolean networkError = false;
   int oldSizeMB;
   String responseBody;
   private String url;

   public ServiceOperation(String var1) {
      this.url = var1;
   }

   public static void dialogExit(String var0, String var1, Context var2) {
      if(var2 != null) {
         try {
            Builder var3 = new Builder(var2);
            var3.setMessage(var1).setTitle(var0).setCancelable(false).setPositiveButton("OK", new OnClickListener() {
               public void onClick(DialogInterface var1, int var2) {
                  var1.cancel();
               }
            });
            var3.create().show();
         } catch (Exception var5) {
            var5.printStackTrace();
            return;
         }
      }

   }

   public static void dialogSelect(String var0, String var1, Context var2, OnClickListener var3) {
      Builder var4 = new Builder(var2);
      var4.setMessage(var1).setTitle(var0).setCancelable(false).setPositiveButton("OK", var3).setNegativeButton("Cancel", new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var1.cancel();
         }
      });
      var4.create().show();
   }

   public static ProgressDialog drawProgressDialogs(Context var0) {
      ProgressDialog var1 = new ProgressDialog(var0);
      var1.setCancelable(false);
      var1.setTitle(2131230720);
      var1.setIcon(2130837619);
      var1.setMessage("Loading");
      var1.show();
      return var1;
   }

   private void fileDownload(HttpResponse param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static File getDirectoryPath() {
      if(debug) {
         (new File(Environment.getExternalStorageDirectory(), "/OMS")).mkdir();
         return new File(Environment.getExternalStorageDirectory(), "/OMS/");
      } else {
         return new File(Environment.getDataDirectory(), "");
      }
   }

   public static String getJsonResource(Activity param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isTablet(Context var0) {
      return (15 & var0.getResources().getConfiguration().screenLayout) >= 3;
   }

   public static String readJsonFile(String var0) throws IOException, JSONException {
      String var1 = getDirectoryPath().getAbsolutePath() + "/";
      StringBuffer var2 = new StringBuffer(1000);
      Log.v("hello", "json path " + var1 + var0 + "/" + var0 + ".json");
      Log.v("hello", "json path " + Environment.getDataDirectory());
      BufferedReader var5 = new BufferedReader(new FileReader(var1 + var0 + "/" + var0 + ".json"));
      char[] var6 = new char[1024];

      while(true) {
         int var7 = var5.read(var6);
         if(var7 == -1) {
            var5.close();
            return var2.toString();
         }

         var2.append(String.valueOf(var6, 0, var7));
         var6 = new char[1024];
      }
   }

   public void beforeExcute(String... var1) {
      if(this.dialog != null) {
         this.dialog.cancel();
      }

      if(this.ac != null) {
         this.dialog = drawProgressDialogs(this.ac);
      }

      this.execute(var1);
   }

   protected String doInBackground(String... param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onPostExecute(String var1) {
      Log.v("hello", "post exc");
      if(this.dialog != null) {
         try {
            this.dialog.cancel();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      if(this.networkError) {
         dialogExit(AppName, "Can not connect to the Internet.", this.ac);
         if(this.listener != null) {
            this.listener.ServiceconnectionFail(var1);
         }
      } else if(this.listener != null) {
         this.listener.ServiceconnectionFinish(this.responseBody, var1);
         return;
      }

   }

   protected void onPreExecute() {
   }

   protected void onProgressUpdate(Void... var1) {
   }
}
