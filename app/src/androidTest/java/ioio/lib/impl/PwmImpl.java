package ioio.lib.impl;

import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.IOIOImpl;

class PwmImpl extends AbstractResource implements PwmOutput {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private final float baseUs_;
   private final int period_;
   private final int pinNum_;
   private final int pwmNum_;

   static {
      boolean var0;
      if(!PwmImpl.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
   }

   public PwmImpl(IOIOImpl var1, int var2, int var3, int var4, float var5) throws ConnectionLostException {
      super(var1);
      this.pwmNum_ = var3;
      this.pinNum_ = var2;
      this.baseUs_ = var5;
      this.period_ = var4;
   }

   private void setPulseWidthInClocks(float param1) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();
         this.ioio_.closePwm(this.pwmNum_);
         this.ioio_.closePin(this.pinNum_);
      } finally {
         ;
      }

   }

   public void setDutyCycle(float var1) throws ConnectionLostException {
      if($assertionsDisabled || var1 <= 1.0F && var1 >= 0.0F) {
         this.setPulseWidthInClocks(var1 * (float)this.period_);
      } else {
         throw new AssertionError();
      }
   }

   public void setPulseWidth(float var1) throws ConnectionLostException {
      if(!$assertionsDisabled && var1 < 0.0F) {
         throw new AssertionError();
      } else {
         this.setPulseWidthInClocks(var1 / this.baseUs_);
      }
   }

   public void setPulseWidth(int var1) throws ConnectionLostException {
      this.setPulseWidth((float)var1);
   }
}
