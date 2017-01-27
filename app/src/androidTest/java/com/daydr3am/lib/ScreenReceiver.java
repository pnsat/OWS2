package com.daydr3am.lib;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.daydr3am.OWS.MainPage;

public class ScreenReceiver extends BroadcastReceiver {
   Activity activity;

   public ScreenReceiver(Activity var1) {
      IntentFilter var2 = new IntentFilter();
      var2.addAction("android.intent.action.SCREEN_ON");
      var2.addAction("android.intent.action.SCREEN_OFF");
      var1.registerReceiver(this, var2);
      this.activity = var1;
   }

   public void onReceive(Context var1, Intent var2) {
      Log.v("test", "call");
      if(var2.getAction().equals("android.intent.action.SCREEN_OFF")) {
         Intent var4 = new Intent(this.activity.getBaseContext(), MainPage.class);
         var4.setFlags(67108864);
         this.activity.startActivity(var4);
      } else {
         var2.getAction().equals("android.intent.action.SCREEN_ON");
      }
   }
}
