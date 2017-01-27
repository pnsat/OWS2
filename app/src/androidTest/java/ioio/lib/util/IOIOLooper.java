package ioio.lib.util;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public interface IOIOLooper {
   void disconnected();

   void incompatible();

   void loop() throws ConnectionLostException, InterruptedException;

   void setup(IOIO var1) throws ConnectionLostException, InterruptedException;
}
