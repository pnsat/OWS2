package ioio.lib.api;

import ioio.lib.api.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public interface Uart extends Closeable {
   InputStream getInputStream();

   OutputStream getOutputStream();

   public static enum Parity {
      EVEN,
      NONE,
      ODD;

      static {
         Parity[] var0 = new Parity[]{NONE, EVEN, ODD};
      }
   }

   public static enum StopBits {
      ONE,
      TWO;

      static {
         StopBits[] var0 = new StopBits[]{ONE, TWO};
      }
   }
}
