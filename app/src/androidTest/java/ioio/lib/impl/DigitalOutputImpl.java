package ioio.lib.impl;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractPin;
import ioio.lib.impl.IOIOImpl;

class DigitalOutputImpl extends AbstractPin implements DigitalOutput {
   boolean value_;

   DigitalOutputImpl(IOIOImpl var1, int var2, boolean var3) throws ConnectionLostException {
      super(var1, var2);
      this.value_ = var3;
   }

   public void write(boolean param1) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }
}
