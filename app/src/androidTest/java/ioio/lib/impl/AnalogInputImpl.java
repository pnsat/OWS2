package ioio.lib.impl;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractPin;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.io.IOException;

class AnalogInputImpl extends AbstractPin implements AnalogInput, IncomingState.InputPinListener {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   int bufferCapacity_;
   int bufferOverflowCount_ = 0;
   int bufferReadCursor_;
   int bufferSize_;
   int bufferWriteCursor_;
   short[] buffer_;
   private boolean valid_ = false;
   private int value_;

   static {
      boolean var0;
      if(!AnalogInputImpl.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
   }

   AnalogInputImpl(IOIOImpl var1, int var2) throws ConnectionLostException {
      super(var1, var2);
   }

   private short bufferPull() throws InterruptedException, ConnectionLostException {
      synchronized(this){}

      try {
         if(this.buffer_ == null) {
            throw new IllegalStateException("Need to call setBuffer() before reading buffered values.");
         } else {
            while(this.bufferSize_ == 0 && this.state_ == AbstractResource.State.OPEN) {
               this.wait();
            }

            this.checkState();
            short[] var2 = this.buffer_;
            int var3 = this.bufferReadCursor_;
            this.bufferReadCursor_ = var3 + 1;
            short var4 = var2[var3];
            if(this.bufferReadCursor_ == this.bufferCapacity_) {
               this.bufferReadCursor_ = 0;
            }

            this.bufferSize_ += -1;
            return var4;
         }
      } finally {
         ;
      }
   }

   private void bufferPush(short var1) {
      if(this.buffer_ != null) {
         if(this.bufferSize_ == this.bufferCapacity_) {
            ++this.bufferOverflowCount_;
         } else {
            ++this.bufferSize_;
         }

         short[] var2 = this.buffer_;
         int var3 = this.bufferWriteCursor_;
         this.bufferWriteCursor_ = var3 + 1;
         var2[var3] = var1;
         if(this.bufferWriteCursor_ == this.bufferCapacity_) {
            this.bufferWriteCursor_ = 0;
         }

         this.notifyAll();
      }
   }

   public int available() throws ConnectionLostException {
      return this.bufferSize_;
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();

         try {
            this.ioio_.protocol_.setAnalogInSampling(this.pinNum_, false);
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

   public int getOverflowCount() throws ConnectionLostException {
      return this.bufferOverflowCount_;
   }

   public float getReference() {
      return 3.3F;
   }

   public float getSampleRate() throws ConnectionLostException {
      return 1000.0F;
   }

   public float getVoltage() throws InterruptedException, ConnectionLostException {
      return this.read() * this.getReference();
   }

   public float getVoltageBuffered() throws InterruptedException, ConnectionLostException {
      return this.readBuffered() * this.getReference();
   }

   public float read() throws InterruptedException, ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public float readBuffered() throws InterruptedException, ConnectionLostException {
      this.checkState();
      return (float)this.bufferPull() / 1023.0F;
   }

   public void setBuffer(int param1) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public void setValue(int param1) {
      // $FF: Couldn't be decompiled
   }
}
