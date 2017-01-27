package ioio.lib.util.android;

import android.content.ContextWrapper;
import ioio.lib.spi.IOIOConnectionBootstrap;
import ioio.lib.util.IOIOApplicationHelper;
import ioio.lib.util.IOIOConnectionRegistry;
import ioio.lib.util.IOIOLooperProvider;
import ioio.lib.util.android.ContextWrapperDependent;
import java.util.Iterator;

public class IOIOAndroidApplicationHelper extends IOIOApplicationHelper {
   private final ContextWrapper contextWrapper_;

   static {
      IOIOConnectionRegistry.addBootstraps(new String[]{"ioio.lib.android.accessory.AccessoryConnectionBootstrap", "ioio.lib.android.bluetooth.BluetoothIOIOConnectionBootstrap"});
   }

   public IOIOAndroidApplicationHelper(ContextWrapper var1, IOIOLooperProvider var2) {
      super(var2);
      this.contextWrapper_ = var1;
   }

   public void create() {
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).onCreate(this.contextWrapper_);
         }
      }

   }

   public void destroy() {
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).onDestroy();
         }
      }

   }

   public void restart() {
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).reopen();
         }
      }

   }

   public void start() {
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).open();
         }
      }

      super.start();
   }

   public void stop() {
      super.stop();
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).close();
         }
      }

   }
}
