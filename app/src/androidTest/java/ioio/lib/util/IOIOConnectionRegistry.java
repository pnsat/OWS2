package ioio.lib.util;

import android.util.Log;
import ioio.lib.spi.IOIOConnectionBootstrap;
import ioio.lib.spi.NoRuntimeSupportException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class IOIOConnectionRegistry {
   private static final String TAG = "IOIOConnectionRegistry";
   private static Collection bootstraps_ = new LinkedList();

   static {
      addBootstraps(new String[]{"ioio.lib.impl.SocketIOIOConnectionBootstrap"});
   }

   private static void addBootstrap(String var0) {
      try {
         Class var7 = Class.forName(var0).asSubclass(IOIOConnectionBootstrap.class);
         bootstraps_.add((IOIOConnectionBootstrap)var7.newInstance());
         Log.d("IOIOConnectionRegistry", "Successfully added bootstrap class: " + var0);
      } catch (ClassNotFoundException var8) {
         Log.d("IOIOConnectionRegistry", "Bootstrap class not found: " + var0 + ". Not adding.");
      } catch (NoRuntimeSupportException var9) {
         Log.d("IOIOConnectionRegistry", "No runtime support for: " + var0 + ". Not adding.");
      } catch (Throwable var10) {
         Log.e("IOIOConnectionRegistry", "Exception caught while attempting to initialize accessory connection factory", var10);
      }
   }

   public static void addBootstraps(String[] var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         addBootstrap(var0[var2]);
      }

   }

   public static Collection getBootstraps() {
      return bootstraps_;
   }

   public static Collection getConnectionFactories() {
      LinkedList var0 = new LinkedList();
      Iterator var1 = bootstraps_.iterator();

      while(var1.hasNext()) {
         ((IOIOConnectionBootstrap)var1.next()).getFactories(var0);
      }

      return var0;
   }
}
