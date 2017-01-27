package com.daydr3am.OWS;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.IOService;
import com.daydr3am.lib.IOServiceBV20;

public class Barcode extends IORootActivity implements IOService.ReceiveMoney {
   EditText barcode;
   Boolean isDisplay;
   public ServiceConnection service = new ServiceConnection() {
      public void onServiceConnected(ComponentName var1, IBinder var2) {
         Barcode.this.setting = (IOService.SettingService)var2;
         Barcode.this.setting.serviceSetup(Barcode.this);
         Barcode.this.setting.setupReceiveMoney(Barcode.this);
      }

      public void onServiceDisconnected(ComponentName var1) {
         Log.v("hello", "serviced");
      }
   };
   IOService.SettingService setting;

   public void changeLanguage(int var1) {
      if(this.landTextShow != null) {
         this.landTextShow.setText("สแกนบาร์โค้ด");
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903040);
      super.onCreate(var1);
      this.barcode = (EditText)this.findViewById(2131361795);
      this.bindService(new Intent(this, IOServiceBV20.class), this.service, 1);
      this.next.setVisibility(8);
   }

   public void onDestroy() {
      super.onDestroy();
      this.unbindService(this.service);
   }

   protected void onPause() {
      super.onPause();
      this.isDisplay = Boolean.valueOf(false);
   }

   protected void onResume() {
      super.onResume();
      this.barcode.setText("");
      this.isDisplay = Boolean.valueOf(true);
      AudioDemo.Sound().playSound("f60");
   }

   public void receiveBank(int var1) {
   }

   public void receiveCoin(int var1) {
   }

   public void receiveText(final String var1) {
      this.barcode.setText(var1);
      Log.v("hello", "text " + var1);
      (new Handler()).postDelayed(new Runnable() {
         public void run() {
            if(Barcode.this.isDisplay.booleanValue()) {
               Bundle var1x = new Bundle();
               var1x.putInt("Service", 18);
               var1x.putString("DataBarcode", var1);
               Intent var2 = new Intent(Barcode.this.getBaseContext(), Loading.class);
               var2.putExtras(var1x);
               Barcode.this.startActivity(var2);
            }

         }
      }, 1000L);
   }
}
