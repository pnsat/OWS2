package ioio.lib.util.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.IOIOLooperProvider;
import ioio.lib.util.android.IOIOAndroidApplicationHelper;

public abstract class IOIOActivity extends Activity implements IOIOLooperProvider {
   private final IOIOAndroidApplicationHelper helper_ = new IOIOAndroidApplicationHelper(this, this);

   protected IOIOLooper createIOIOLooper() {
      throw new RuntimeException("Client must override one of the createIOIOLooper overloads!");
   }

   public IOIOLooper createIOIOLooper(String var1, Object var2) {
      return this.createIOIOLooper();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.helper_.create();
   }

   protected void onDestroy() {
      this.helper_.destroy();
      super.onDestroy();
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      if((268435456 & var1.getFlags()) != 0) {
         this.helper_.restart();
      }

   }

   protected void onStart() {
      super.onStart();
      this.helper_.start();
   }

   protected void onStop() {
      this.helper_.stop();
      super.onStop();
   }
}
