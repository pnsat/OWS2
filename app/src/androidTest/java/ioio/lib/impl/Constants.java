package ioio.lib.impl;

class Constants {
   static final int BUFFER_SIZE = 1024;
   static final int[] ICSP_PINS;
   static final int[] INCAP_MODULES_DOUBLE;
   static final int[] INCAP_MODULES_SINGLE;
   static final int NUM_ANALOG_PINS = 16;
   static final int NUM_PINS = 49;
   static final int NUM_PWM_MODULES = 9;
   static final int NUM_SPI_MODULES = 3;
   static final int NUM_TWI_MODULES = 3;
   static final int NUM_UART_MODULES = 4;
   static final int PACKET_BUFFER_SIZE = 256;
   static final int[][] TWI_PINS;

   static {
      int[] var0 = new int[]{0, 2, 4};
      INCAP_MODULES_DOUBLE = var0;
      INCAP_MODULES_SINGLE = new int[]{6, 7, 8};
      TWI_PINS = new int[][]{{4, 5}, {47, 48}, {26, 25}};
      ICSP_PINS = new int[]{36, 37, 38};
   }
}
