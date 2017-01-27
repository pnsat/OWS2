package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface DigitalInput extends Closeable {
   boolean read() throws InterruptedException, ConnectionLostException;

   void waitForValue(boolean var1) throws InterruptedException, ConnectionLostException;

   public static class Spec {
      public Mode mode;
      public int pin;

      public Spec(int var1) {
         this(var1, Mode.FLOATING);
      }

      public Spec(int var1, Mode var2) {
         this.pin = var1;
         this.mode = var2;
      }
   }

   public static enum Mode {
      FLOATING,
      PULL_DOWN,
      PULL_UP;

      static {
         Mode[] var0 = new Mode[]{FLOATING, PULL_UP, PULL_DOWN};
      }
   }
}
