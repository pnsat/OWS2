package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface SpiMaster extends Closeable {
   void writeRead(int var1, byte[] var2, int var3, int var4, byte[] var5, int var6) throws ConnectionLostException, InterruptedException;

   void writeRead(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ConnectionLostException, InterruptedException;

   Result writeReadAsync(int var1, byte[] var2, int var3, int var4, byte[] var5, int var6) throws ConnectionLostException;

   public static class Config {
      public boolean invertClk;
      public Rate rate;
      public boolean sampleOnTrailing;

      public Config(Rate var1) {
         this(var1, false, false);
      }

      public Config(Rate var1, boolean var2, boolean var3) {
         this.rate = var1;
         this.invertClk = var2;
         this.sampleOnTrailing = var3;
      }
   }

   public static enum Rate {
      RATE_125K,
      RATE_142K,
      RATE_166K,
      RATE_1M,
      RATE_1_3M,
      RATE_200K,
      RATE_250K,
      RATE_2M,
      RATE_2_2M,
      RATE_2_6M,
      RATE_31K,
      RATE_333K,
      RATE_35K,
      RATE_3_2M,
      RATE_41K,
      RATE_4M,
      RATE_500K,
      RATE_50K,
      RATE_571K,
      RATE_5_3M,
      RATE_62K,
      RATE_666K,
      RATE_800K,
      RATE_83K,
      RATE_8M;

      static {
         Rate[] var0 = new Rate[]{RATE_31K, RATE_35K, RATE_41K, RATE_50K, RATE_62K, RATE_83K, RATE_125K, RATE_142K, RATE_166K, RATE_200K, RATE_250K, RATE_333K, RATE_500K, RATE_571K, RATE_666K, RATE_800K, RATE_1M, RATE_1_3M, RATE_2M, RATE_2_2M, RATE_2_6M, RATE_3_2M, RATE_4M, RATE_5_3M, RATE_8M};
      }
   }

   public interface Result {
      void waitReady() throws ConnectionLostException, InterruptedException;
   }
}
