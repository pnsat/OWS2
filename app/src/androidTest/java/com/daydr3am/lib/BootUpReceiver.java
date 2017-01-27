package com.daydr3am.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.daydr3am.OWS.SampleActivity;

public class BootUpReceiver extends BroadcastReceiver {
   public void onReceive(Context var1, Intent var2) {
      Log.v("test", "Hello");
      Intent var4 = new Intent(var1, SampleActivity.class);
      var4.addFlags(268435456);
      var1.startActivity(var4);
   }
}
