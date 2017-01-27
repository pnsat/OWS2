package com.daydr3am.OWS;

import android.os.Bundle;
import android.util.Log;
import com.daydr3am.OWS.IORootActivity;

public class FinishInputCoin extends IORootActivity {
   public void onCreate(Bundle var1) {
      this.setContentView(2130903047);
      super.onCreate(var1);
   }

   protected void onDestroy() {
      super.onDestroy();
      System.gc();
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
   }
}
