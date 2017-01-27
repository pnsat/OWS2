package ioio.lib.impl;

class PinFunctionMap {
   private static final boolean[] PERIPHERAL_IN;
   private static final boolean[] PERIPHERAL_OUT;

   static {
      boolean[] var0 = new boolean[49];
      var0[0] = true;
      var0[3] = true;
      var0[4] = true;
      var0[5] = true;
      var0[6] = true;
      var0[7] = true;
      var0[10] = true;
      var0[11] = true;
      var0[12] = true;
      var0[13] = true;
      var0[14] = true;
      var0[27] = true;
      var0[28] = true;
      var0[29] = true;
      var0[30] = true;
      var0[31] = true;
      var0[32] = true;
      var0[34] = true;
      var0[35] = true;
      var0[36] = true;
      var0[37] = true;
      var0[38] = true;
      var0[39] = true;
      var0[40] = true;
      var0[45] = true;
      var0[46] = true;
      var0[47] = true;
      var0[48] = true;
      PERIPHERAL_OUT = var0;
      boolean[] var1 = new boolean[49];
      var1[0] = true;
      var1[3] = true;
      var1[4] = true;
      var1[5] = true;
      var1[6] = true;
      var1[7] = true;
      var1[9] = true;
      var1[10] = true;
      var1[11] = true;
      var1[12] = true;
      var1[13] = true;
      var1[14] = true;
      var1[27] = true;
      var1[28] = true;
      var1[29] = true;
      var1[30] = true;
      var1[31] = true;
      var1[32] = true;
      var1[34] = true;
      var1[35] = true;
      var1[36] = true;
      var1[37] = true;
      var1[38] = true;
      var1[39] = true;
      var1[40] = true;
      var1[45] = true;
      var1[46] = true;
      var1[47] = true;
      var1[48] = true;
      PERIPHERAL_IN = var1;
   }

   static void checkSupportsAnalogInput(int var0) {
      checkValidPin(var0);
      if(var0 < 31 || var0 > 46) {
         throw new IllegalArgumentException("Pin " + var0 + " does not support analog input");
      }
   }

   static void checkSupportsPeripheralInput(int var0) {
      checkValidPin(var0);
      if(!PERIPHERAL_IN[var0]) {
         throw new IllegalArgumentException("Pin " + var0 + " does not support peripheral input");
      }
   }

   static void checkSupportsPeripheralOutput(int var0) {
      checkValidPin(var0);
      if(!PERIPHERAL_OUT[var0]) {
         throw new IllegalArgumentException("Pin " + var0 + " does not support peripheral output");
      }
   }

   static void checkValidPin(int var0) {
      if(var0 < 0 || var0 > 48) {
         throw new IllegalArgumentException("Illegal pin: " + var0);
      }
   }
}
