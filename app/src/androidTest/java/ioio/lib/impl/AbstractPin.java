package ioio.lib.impl;

import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.IOIOImpl;

abstract class AbstractPin extends AbstractResource {
   protected final int pinNum_;

   AbstractPin(IOIOImpl var1, int var2) throws ConnectionLostException {
      super(var1);
      this.pinNum_ = var2;
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();
         this.ioio_.closePin(this.pinNum_);
      } finally {
         ;
      }

   }
}
