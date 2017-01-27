package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface PulseInput extends Closeable {
   float getDuration() throws InterruptedException, ConnectionLostException;

   float getFrequency() throws InterruptedException, ConnectionLostException;

   float waitPulseGetDuration() throws InterruptedException, ConnectionLostException;

   public static enum ClockRate {
      RATE_16MHz(16000000),
      RATE_250KHz(250000),
      RATE_2MHz(2000000),
      RATE_62KHz('\uf424');

      public final int hertz;

      static {
         ClockRate[] var0 = new ClockRate[]{RATE_16MHz, RATE_2MHz, RATE_250KHz, RATE_62KHz};
      }

      private ClockRate(int var3) {
         this.hertz = var3;
      }
   }

   public static enum PulseMode {
      FREQ(1),
      FREQ_SCALE_16(16),
      FREQ_SCALE_4(4),
      NEGATIVE(1),
      POSITIVE(1);

      public final int scaling;

      static {
         PulseMode[] var0 = new PulseMode[]{POSITIVE, NEGATIVE, FREQ, FREQ_SCALE_4, FREQ_SCALE_16};
      }

      private PulseMode(int var3) {
         this.scaling = var3;
      }
   }
}
