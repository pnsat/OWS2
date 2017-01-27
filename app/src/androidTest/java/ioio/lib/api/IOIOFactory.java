package ioio.lib.api;

import android.util.Log;
import ioio.lib.api.IOIO;
import ioio.lib.api.IOIOConnection;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.spi.IOIOConnectionFactory;
import ioio.lib.util.IOIOConnectionRegistry;
import java.util.Collection;
import java.util.NoSuchElementException;

public class IOIOFactory {
   private static final String TAG = "IOIOFactory";

   public static IOIO create() {
      Collection var0 = IOIOConnectionRegistry.getConnectionFactories();

      try {
         IOIO var3 = create(((IOIOConnectionFactory)var0.iterator().next()).createConnection());
         return var3;
      } catch (NoSuchElementException var4) {
         Log.e("IOIOFactory", "No connection is available. This shouldn\'t happen.");
         throw var4;
      }
   }

   public static IOIO create(IOIOConnection var0) {
      return new IOIOImpl(var0);
   }
}
