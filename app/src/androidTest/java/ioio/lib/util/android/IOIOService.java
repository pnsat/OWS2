package ioio.lib.util.android;

import android.app.Service;
import android.content.Intent;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.IOIOLooperProvider;
import ioio.lib.util.android.IOIOAndroidApplicationHelper;

public abstract class IOIOService extends Service implements IOIOLooperProvider {
   private final IOIOAndroidApplicationHelper helper_ = new IOIOAndroidApplicationHelper(this, this);
   private boolean started_ = false;

   protected IOIOLooper createIOIOLooper() {
      throw new RuntimeException("Client must override one of the createIOIOLooper overloads!");
   }

   public IOIOLooper createIOIOLooper(String var1, Object var2) {
      return this.createIOIOLooper();
   }

   public void onCreate() {
      super.onCreate();
      this.helper_.create();
   }

   public void onDestroy() {
      this.stop();
      this.helper_.destroy();
      super.onDestroy();
   }

   public void onStart(Intent var1, int var2) {
      super.onStart(var1, var2);
      if(!this.started_) {
         this.helper_.start();
         this.started_ = true;
      } else if((268435456 & var1.getFlags()) != 0) {
         this.helper_.restart();
         return;
      }

   }

   protected void stop() {
      if(this.started_) {
         this.helper_.stop();
         this.started_ = false;
      }

   }
}
