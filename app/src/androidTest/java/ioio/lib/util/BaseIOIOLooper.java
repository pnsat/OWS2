package ioio.lib.util;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.IOIOLooper;

public class BaseIOIOLooper implements IOIOLooper {
   protected IOIO ioio_;

   public void disconnected() {
   }

   public void incompatible() {
   }

   public void loop() throws ConnectionLostException, InterruptedException {
      Thread.sleep(20L);
   }

   protected void setup() throws ConnectionLostException, InterruptedException {
   }

   public final void setup(IOIO var1) throws ConnectionLostException, InterruptedException {
      this.ioio_ = var1;
      this.setup();
   }
}
