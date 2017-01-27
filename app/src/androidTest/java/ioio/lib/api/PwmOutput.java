package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface PwmOutput extends Closeable {
   void setDutyCycle(float var1) throws ConnectionLostException;

   void setPulseWidth(float var1) throws ConnectionLostException;

   void setPulseWidth(int var1) throws ConnectionLostException;
}
