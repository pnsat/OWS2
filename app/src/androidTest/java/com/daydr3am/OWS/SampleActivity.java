package com.daydr3am.OWS;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.InputPassword;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;
import com.daydr3am.lib.IOService;
import com.daydr3am.lib.IOServiceBV20;
import com.daydr3am.lib.LogChecker;
import com.daydr3am.lib.LogController;
import com.daydr3am.lib.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class SampleActivity extends IORootActivity implements OnTouchListener {
   public static boolean showAlert = false;
   public static boolean stopIO = false;
   float X = 0.0F;
   float Y = 0.0F;
   String error = "แจ้งปัญหา 084-555-7930";
   Boolean isBV20 = Boolean.valueOf(false);
   public ServiceConnection service = new ServiceConnection() {
      public void onServiceConnected(ComponentName var1, IBinder var2) {
         SampleActivity.this.setting = (IOService.SettingService)var2;
         SampleActivity.this.setting.serviceSetup(SampleActivity.this);
      }

      public void onServiceDisconnected(ComponentName var1) {
      }
   };
   IOService.SettingService setting;
   TextView text_MS3;
   Thread thread;

   public static String convertStreamToString(InputStream var0) throws Exception {
      BufferedReader var1 = new BufferedReader(new InputStreamReader(var0, Charset.forName("UTF-8")));
      StringBuilder var2 = new StringBuilder();

      while(true) {
         String var3 = var1.readLine();
         if(var3 == null) {
            var1.close();
            return var2.toString();
         }

         var2.append(var3).append("\n");
      }
   }

   public static String getStringFromFile(String var0) throws Exception {
      FileInputStream var1 = new FileInputStream(new File(var0));
      String var2 = convertStreamToString(var1);
      var1.close();
      return var2;
   }

   public void onCreate(Bundle var1) {
      this.getWindow().addFlags(128);
      View var2 = this.getLayoutInflater().inflate(2130903055, (ViewGroup)null);
      this.setContentView(var2);
      Service.pref = this.getPreferences(0);
      var2.setOnTouchListener(this);
      super.onCreate(var1);
      this.findViewById(2131361871).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Intent var2 = new Intent(SampleActivity.this, InputPassword.class);
            Bundle var3 = new Bundle();
            var3.putInt("PassType", 1);
            var2.putExtras(var3);
            SampleActivity.this.startActivity(var2);
         }
      });

      try {
         this.error = getStringFromFile("/mnt/sdcard/Resource/Error.txt");
      } catch (Exception var4) {
         this.error = "�?�?�?�? 084-555-7930";
      }

      Log.v("hello", "error message " + this.error);
      this.back.setVisibility(8);
      this.next.setVisibility(8);
      this.cancel.setVisibility(8);
      Log.v("hello", "preview create");
      if(!this.getSharedPreferences("hello", 0).getBoolean("isICT", false)) {
         this.isBV20 = Boolean.valueOf(true);
      }

      if(this.isBV20.booleanValue()) {
         IOServiceBV20.uselight = true;
      } else {
         IOService.uselight = true;
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      System.gc();
   }

   protected void onPause() {
      super.onPause();
      LogChecker.isSplash = false;
      Log.v("hello", "preview pause");
      this.thread.interrupt();
      this.unbindService(this.service);
      if(!this.getSharedPreferences("hello", 0).getBoolean("isICT", false)) {
         this.isBV20 = Boolean.valueOf(true);
      }

      if(this.isBV20.booleanValue()) {
         IOServiceBV20.uselight = false;
      } else {
         IOService.uselight = false;
      }
   }

   protected void onResume() {
      super.onResume();
      Log.v("hello", "preview resume");
      LogChecker.isSplash = true;
      final SharedPreferences var2 = this.getSharedPreferences("hello", 0);
      int var3 = var2.getInt("count", 0);
      final int[] var4 = new int[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = var2.getInt("timeURL" + var5, 0);
      }

      if(!this.getSharedPreferences("hello", 0).getBoolean("isICT", false)) {
         this.isBV20 = Boolean.valueOf(true);
         Log.v("hello", "isbv20");
         this.bindService(new Intent(this, IOServiceBV20.class), this.service, 1);
      } else {
         Log.v("hello", "isnotbv20");
         this.bindService(new Intent(this, IOService.class), this.service, 1);
      }

      this.findViewById(2131361870).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Intent var2 = new Intent(SampleActivity.this, InputPassword.class);
            Bundle var3 = new Bundle();
            var3.putInt("PassType", 1);
            var2.putExtras(var3);
            SampleActivity.this.startActivity(var2);
         }
      });
      this.thread = new Thread(new Runnable() {
         int index = 0;

         public void run() {
            while(true) {
               while(true) {
                  while(true) {
                     try {
                        try {
                           while(var4.length != 0) {
                              Log.v("hello", "test 1");
                              String[] var4x = var2.getString("bannerURL" + this.index, "").split("/");
                              String var5 = var4x[-1 + var4x.length];
                              (SampleActivity.this.new LongOperation((LongOperation)null)).execute(new String[]{var5});
                              Log.v("hello", "test 2");
                              String[] var8 = var2.getString("soundURL" + this.index, "").split("/");
                              String var9 = var8[-1 + var8.length];
                              if(var9.length() > 0) {
                                 AudioDemo.Sound().playAdSound(var9);
                              }

                              Log.v("hello", "test 3 " + var2.getString("soundURL" + this.index, ""));
                              if(var4.length > this.index) {
                                 Thread.sleep((long)(1000 * var4[this.index]));
                              }

                              ++this.index;
                              if(this.index >= var4.length) {
                                 this.index = 0;
                              }
                           }

                           Thread.sleep(30000L);
                        } catch (ArrayIndexOutOfBoundsException var10) {
                           var10.printStackTrace();
                           Thread.sleep(5000L);
                        }
                     } catch (InterruptedException var11) {
                        var11.printStackTrace();
                        return;
                     }
                  }
               }
            }
         }
      });
      this.thread.start();
      TextView var8 = (TextView)this.findViewById(2131361873);
      Log.v("hello", "final " + var8.isFocused());
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      switch(var2.getAction()) {
      case 0:
         this.X = var2.getX();
         this.Y = var2.getY();
         break;
      case 1:
         (new Handler()).post(new Runnable() {
            public void run() {
               if(LogController.checkFileList() != 0) {
                  SampleActivity.showAlert = true;
                  (new Thread(new Runnable() {
                     // $FF: synthetic field
                     private final AlertDialog val$dialog;

                     {
                        this.val$dialog = var2;
                     }

                     public void run() {
                        try {
                           Thread.sleep(13000L);
                        } catch (InterruptedException var2) {
                           var2.printStackTrace();
                        }

                        if(this.val$dialog.isShowing()) {
                           this.val$dialog.dismiss();
                        }

                        SampleActivity.showAlert = false;
                     }
                  })).start();
               } else {
                  SampleActivity.this.finish();
               }
            }
         });
      case 2:
      }

      return true;
   }

   private class LongOperation extends AsyncTask {
      String name;

      private LongOperation() {
         this.name = "";
      }

      // $FF: synthetic method
      LongOperation(LongOperation var2) {
         this();
      }

      protected String doInBackground(String... var1) {
         this.name = var1[0];
         Log.v("hello", "doin " + this.name);
         this.publishProgress(new Void[0]);
         return "Executed";
      }

      protected void onPostExecute(String var1) {
      }

      protected void onPreExecute() {
      }

      protected void onProgressUpdate(Void... var1) {
         ((ImageView)SampleActivity.this.findViewById(2131361794)).setImageDrawable(CallImage.getBackground(this.name));
      }
   }
}
