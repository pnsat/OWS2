package ioio.lib.impl;

import ioio.lib.api.PulseInput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractPin;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.util.LinkedList;
import java.util.Queue;

class IncapImpl extends AbstractPin implements PulseInput, IncomingState.DataModuleListener {
   private static final int MAX_QUEUE_LEN = 32;
   private final boolean doublePrecision_;
   private final int incapNum_;
   private long lastDuration_;
   private final PulseMode mode_;
   private Queue pulseQueue_ = new LinkedList();
   private final float timeBase_;
   private boolean valid_ = false;

   public IncapImpl(IOIOImpl var1, PulseMode var2, int var3, int var4, int var5, int var6, boolean var7) throws ConnectionLostException {
      super(var1, var4);
      this.mode_ = var2;
      this.incapNum_ = var3;
      this.timeBase_ = 1.0F / (float)(var6 * var5);
      this.doublePrecision_ = var7;
   }

   private static long ByteArrayToLong(byte[] var0, int var1) {
      long var2 = 0L;
      int var4 = var1;

      while(true) {
         int var5 = var4 - 1;
         if(var4 <= 0) {
            if(var2 == 0L) {
               var2 = (long)(1 << var1 * 8);
            }

            return var2;
         }

         var2 = var2 << 8 | (long)(255 & var0[var5]);
         var4 = var5;
      }
   }

   public void close() {
      synchronized(this){}

      try {
         this.ioio_.closeIncap(this.incapNum_, this.doublePrecision_);
         super.close();
      } finally {
         ;
      }

   }

   public void dataReceived(byte[] var1, int var2) {
      synchronized(this){}

      try {
         this.lastDuration_ = ByteArrayToLong(var1, var2);
         if(this.pulseQueue_.size() == 32) {
            this.pulseQueue_.remove();
         }

         this.pulseQueue_.add(Long.valueOf(this.lastDuration_));
         this.valid_ = true;
         this.notifyAll();
      } finally {
         ;
      }

   }

   public void disconnected() {
      synchronized(this){}

      try {
         this.notifyAll();
         super.disconnected();
      } finally {
         ;
      }

   }

   public float getDuration() throws InterruptedException, ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public float getFrequency() throws InterruptedException, ConnectionLostException {
      if(this.mode_ != PulseMode.FREQ && this.mode_ != PulseMode.FREQ_SCALE_4 && this.mode_ != PulseMode.FREQ_SCALE_16) {
         throw new IllegalStateException("Cannot query frequency when module was not opened in frequency mode.");
      } else {
         return 1.0F / this.getDuration();
      }
   }

   public void reportAdditionalBuffer(int var1) {
      synchronized(this){}
   }

   public float waitPulseGetDuration() throws InterruptedException, ConnectionLostException {
      // $FF: Couldn't be decompiled
   }
}
