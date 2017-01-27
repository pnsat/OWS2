package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MainPage;

public class Receive extends IORootActivity {
   public int moneyInput = 20;

   public void changeLanguage(int var1) {
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903043);
      super.onCreate(var1);
   }

   protected void onResume() {
      super.onResume();
      this.changeLanguage(switchID);
      Log.d("debug", "onResume active");
      (new Handler()).postDelayed(new Runnable() {
         public void run() {
            if(Receive.this.activityActive) {
               Intent var1 = new Intent(Receive.this.getApplicationContext(), MainPage.class);
               var1.setFlags(67108864);
               Receive.this.startActivityForResult(var1, 0);
            }

         }
      }, 60000L);
   }
}
