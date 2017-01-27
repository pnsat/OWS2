package ioio.lib.impl;

import ioio.lib.api.DigitalInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractPin;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.io.IOException;

class DigitalInputImpl extends AbstractPin implements DigitalInput, IncomingState.InputPinListener {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private boolean valid_ = false;
   private boolean value_;

   static {
      boolean var0;
      if(!DigitalInputImpl.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
   }

   DigitalInputImpl(IOIOImpl var1, int var2) throws ConnectionLostException {
      super(var1, var2);
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();

         try {
            this.ioio_.protocol_.setChangeNotify(this.pinNum_, false);
         } catch (IOException var6) {
            ;
         }
      } finally {
         ;
      }

   }

   public void disconnected() {
      synchronized(this){}

      try {
         super.disconnected();
         this.notifyAll();
      } finally {
         ;
      }

   }

   public boolean read() throws InterruptedException, ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public void setValue(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void waitForValue(boolean param1) throws InterruptedException, ConnectionLostException {
      // $FF: Couldn't be decompiled
   }
}
