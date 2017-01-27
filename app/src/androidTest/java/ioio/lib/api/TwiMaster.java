package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface TwiMaster extends Closeable {
   boolean writeRead(int var1, boolean var2, byte[] var3, int var4, byte[] var5, int var6) throws ConnectionLostException, InterruptedException;

   Result writeReadAsync(int var1, boolean var2, byte[] var3, int var4, byte[] var5, int var6) throws ConnectionLostException;

   public static enum Rate {
      RATE_100KHz,
      RATE_1MHz,
      RATE_400KHz;

      static {
         Rate[] var0 = new Rate[]{RATE_100KHz, RATE_400KHz, RATE_1MHz};
      }
   }

   public interface Result {
      boolean waitReady() throws ConnectionLostException, InterruptedException;
   }
}
