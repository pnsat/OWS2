package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface DigitalOutput extends Closeable {
   void write(boolean var1) throws ConnectionLostException;

   public static class Spec {
      public Mode mode;
      public int pin;

      public Spec(int var1) {
         this(var1, Mode.NORMAL);
      }

      public Spec(int var1, Mode var2) {
         this.pin = var1;
         this.mode = var2;
      }
   }

   public static enum Mode {
      NORMAL,
      OPEN_DRAIN;

      static {
         Mode[] var0 = new Mode[]{NORMAL, OPEN_DRAIN};
      }
   }
}
