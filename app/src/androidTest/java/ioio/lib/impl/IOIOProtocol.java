package ioio.lib.impl;

import android.util.Log;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.TwiMaster;
import ioio.lib.api.Uart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class IOIOProtocol {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   static final int CHECK_INTERFACE = 2;
   static final int CHECK_INTERFACE_RESPONSE = 2;
   static final int ESTABLISH_CONNECTION = 0;
   static final int HARD_RESET = 0;
   static final int I2C_CONFIGURE_MASTER = 19;
   static final int I2C_REPORT_TX_STATUS = 21;
   static final int I2C_RESULT = 20;
   static final int I2C_STATUS = 19;
   static final int I2C_WRITE_READ = 20;
   static final int ICSP_CONFIG = 26;
   static final int ICSP_PROG_ENTER = 24;
   static final int ICSP_PROG_EXIT = 25;
   static final int ICSP_REGOUT = 23;
   static final int ICSP_REPORT_RX_STATUS = 22;
   static final int ICSP_RESULT = 23;
   static final int ICSP_SIX = 22;
   static final int INCAP_CONFIGURE = 27;
   static final int INCAP_REPORT = 28;
   static final int INCAP_STATUS = 27;
   static final int REGISTER_PERIODIC_DIGITAL_SAMPLING = 7;
   static final int REPORT_ANALOG_IN_FORMAT = 12;
   static final int REPORT_ANALOG_IN_STATUS = 11;
   static final int REPORT_DIGITAL_IN_STATUS = 4;
   static final int REPORT_PERIODIC_DIGITAL_IN_STATUS = 5;
   static final int[] SCALE_DIV;
   static final int SET_ANALOG_IN_SAMPLING = 12;
   static final int SET_CHANGE_NOTIFY = 6;
   static final int SET_DIGITAL_OUT_LEVEL = 4;
   static final int SET_PIN_ANALOG_IN = 11;
   static final int SET_PIN_DIGITAL_IN = 5;
   static final int SET_PIN_DIGITAL_OUT = 3;
   static final int SET_PIN_INCAP = 28;
   static final int SET_PIN_PWM = 8;
   static final int SET_PIN_SPI = 18;
   static final int SET_PIN_UART = 15;
   static final int SET_PWM_DUTY_CYCLE = 9;
   static final int SET_PWM_PERIOD = 10;
   static final int SOFT_CLOSE = 29;
   static final int SOFT_RESET = 1;
   static final int SPI_CONFIGURE_MASTER = 16;
   static final int SPI_DATA = 17;
   static final int SPI_MASTER_REQUEST = 17;
   static final int SPI_REPORT_TX_STATUS = 18;
   static final int SPI_STATUS = 16;
   private static final String TAG = "IOIOProtocol";
   static final int UART_CONFIG = 13;
   static final int UART_DATA = 14;
   static final int UART_REPORT_TX_STATUS = 15;
   static final int UART_STATUS = 13;
   private int batchCounter_ = 0;
   private final IncomingHandler handler_;
   private final InputStream in_;
   private final OutputStream out_;
   private byte[] outbuf_ = new byte[256];
   private int pos_ = 0;
   private final IncomingThread thread_ = new IncomingThread();

   static {
      boolean var0;
      if(!IOIOProtocol.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
      SCALE_DIV = new int[]{31, 30, 29, 28, 27, 26, 23, 22, 21, 20, 19, 18, 15, 14, 13, 12, 11, 10, 7, 6, 5, 4, 3, 2, 1};
   }

   public IOIOProtocol(InputStream var1, OutputStream var2, IncomingHandler var3) {
      this.in_ = var1;
      this.out_ = var2;
      this.handler_ = var3;
      this.thread_.start();
   }

   // $FF: synthetic method
   static IncomingHandler access$1(IOIOProtocol var0) {
      return var0.handler_;
   }

   private void flush() throws IOException {
      try {
         this.out_.write(this.outbuf_, 0, this.pos_);
      } finally {
         this.pos_ = 0;
      }

   }

   private void writeByte(int var1) throws IOException {
      if($assertionsDisabled || var1 >= 0 && var1 < 256) {
         if(this.pos_ == this.outbuf_.length) {
            this.flush();
         }

         byte[] var2 = this.outbuf_;
         int var3 = this.pos_;
         this.pos_ = var3 + 1;
         var2[var3] = (byte)var1;
      } else {
         throw new AssertionError();
      }
   }

   private void writeThreeBytes(int var1) throws IOException {
      this.writeByte(var1 & 255);
      this.writeByte(255 & var1 >> 8);
      this.writeByte(255 & var1 >> 16);
   }

   private void writeTwoBytes(int var1) throws IOException {
      this.writeByte(var1 & 255);
      this.writeByte(var1 >> 8);
   }

   public void beginBatch() {
      synchronized(this){}

      try {
         ++this.batchCounter_;
      } finally {
         ;
      }

   }

   public void checkInterface(byte[] param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void endBatch() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void hardReset() throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(0);
         this.writeByte(73);
         this.writeByte(79);
         this.writeByte(73);
         this.writeByte(79);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void i2cClose(int var1) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(19);
         this.writeByte(var1);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void i2cConfigureMaster(int param1, TwiMaster.Rate param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void i2cWriteRead(int param1, boolean param2, int param3, int param4, int param5, byte[] param6) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void icspClose() throws IOException {
      this.beginBatch();
      this.writeByte(26);
      this.writeByte(0);
      this.endBatch();
   }

   public void icspEnter() throws IOException {
      this.beginBatch();
      this.writeByte(24);
      this.endBatch();
   }

   public void icspExit() throws IOException {
      this.beginBatch();
      this.writeByte(25);
      this.endBatch();
   }

   public void icspOpen() throws IOException {
      this.beginBatch();
      this.writeByte(26);
      this.writeByte(1);
      this.endBatch();
   }

   public void icspRegout() throws IOException {
      this.beginBatch();
      this.writeByte(23);
      this.endBatch();
   }

   public void icspSix(int var1) throws IOException {
      this.beginBatch();
      this.writeByte(22);
      this.writeThreeBytes(var1);
      this.endBatch();
   }

   public void incapClose(int var1) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(27);
         this.writeByte(var1);
         this.writeByte(0);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void incapConfigure(int param1, boolean param2, int param3, int param4) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void registerPeriodicDigitalSampling(int var1, int var2) throws IOException {
      synchronized(this){}
   }

   public void setAnalogInSampling(int param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setChangeNotify(int param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setDigitalOutLevel(int param1, boolean param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPinAnalogIn(int var1) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(11);
         this.writeByte(var1);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void setPinDigitalIn(int param1, DigitalInput.Mode param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPinDigitalOut(int param1, boolean param2, DigitalOutput.Mode param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPinIncap(int param1, int param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPinPwm(int param1, int param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPinSpi(int var1, int var2, boolean var3, int var4) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(18);
         this.writeByte(var1);
         this.writeByte(var4 | 16 | var2 << 2);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void setPinUart(int param1, int param2, boolean param3, boolean param4) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setPwmDutyCycle(int var1, int var2, int var3) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(9);
         this.writeByte(var3 | var1 << 2);
         this.writeTwoBytes(var2);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void setPwmPeriod(int var1, int var2, PwmScale var3) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(10);
         this.writeByte((2 & var3.encoding) << 6 | var1 << 1 | 1 & var3.encoding);
         this.writeTwoBytes(var2);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void softClose() throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(29);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void softReset() throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(1);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void spiClose(int var1) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(16);
         this.writeByte(var1 << 5);
         this.writeByte(0);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void spiConfigureMaster(int param1, SpiMaster.Config param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void spiMasterRequest(int param1, int param2, byte[] param3, int param4, int param5, int param6) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void uartClose(int var1) throws IOException {
      synchronized(this){}

      try {
         this.beginBatch();
         this.writeByte(13);
         this.writeByte(var1 << 6);
         this.writeTwoBytes(0);
         this.endBatch();
      } finally {
         ;
      }

   }

   public void uartConfigure(int param1, int param2, boolean param3, Uart.StopBits param4, Uart.Parity param5) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void uartData(int param1, int param2, byte[] param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public interface IncomingHandler {
      void handleAnalogPinStatus(int var1, boolean var2);

      void handleCheckInterfaceResponse(boolean var1);

      void handleConnectionLost();

      void handleEstablishConnection(byte[] var1, byte[] var2, byte[] var3);

      void handleI2cClose(int var1);

      void handleI2cOpen(int var1);

      void handleI2cReportTxStatus(int var1, int var2);

      void handleI2cResult(int var1, int var2, byte[] var3);

      void handleIcspClose();

      void handleIcspOpen();

      void handleIcspReportRxStatus(int var1);

      void handleIcspResult(int var1, byte[] var2);

      void handleIncapClose(int var1);

      void handleIncapOpen(int var1);

      void handleIncapReport(int var1, int var2, byte[] var3);

      void handleRegisterPeriodicDigitalSampling(int var1, int var2);

      void handleReportAnalogInStatus(int[] var1, int[] var2);

      void handleReportDigitalInStatus(int var1, boolean var2);

      void handleReportPeriodicDigitalInStatus(int var1, boolean[] var2);

      void handleSetChangeNotify(int var1, boolean var2);

      void handleSoftReset();

      void handleSpiClose(int var1);

      void handleSpiData(int var1, int var2, byte[] var3, int var4);

      void handleSpiOpen(int var1);

      void handleSpiReportTxStatus(int var1, int var2);

      void handleUartClose(int var1);

      void handleUartData(int var1, int var2, byte[] var3);

      void handleUartOpen(int var1);

      void handleUartReportTxStatus(int var1, int var2);
   }

   class IncomingThread extends Thread {
      private Set addedPins_ = new HashSet(16);
      private int[] analogFramePins_ = new int[0];
      private byte[] inbuf_ = new byte[64];
      private int readOffset_ = 0;
      private Set removedPins_ = new HashSet(16);
      private int validBytes_ = 0;

      private void fillBuf() throws IOException {
         try {
            this.validBytes_ = IOIOProtocol.this.in_.read(this.inbuf_, 0, this.inbuf_.length);
            if(this.validBytes_ <= 0) {
               throw new IOException("Unexpected stream closure");
            } else {
               this.readOffset_ = 0;
            }
         } catch (IOException var2) {
            Log.i("IOIOProtocol", "IOIO disconnected");
            throw var2;
         }
      }

      private void findDelta(int[] var1) {
         int var2 = 0;
         this.removedPins_.clear();
         this.addedPins_.clear();
         int[] var3 = this.analogFramePins_;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int var6 = var3[var5];
            this.removedPins_.add(Integer.valueOf(var6));
         }

         for(int var8 = var1.length; var2 < var8; ++var2) {
            int var9 = var1[var2];
            this.addedPins_.add(Integer.valueOf(var9));
         }

         Iterator var11 = this.removedPins_.iterator();

         while(var11.hasNext()) {
            Integer var12 = (Integer)var11.next();
            if(this.addedPins_.contains(var12)) {
               var11.remove();
               this.addedPins_.remove(var12);
            }
         }

      }

      private int readByte() throws IOException {
         if(this.readOffset_ == this.validBytes_) {
            this.fillBuf();
         }

         byte[] var1 = this.inbuf_;
         int var2 = this.readOffset_;
         this.readOffset_ = var2 + 1;
         return 255 & var1[var2];
      }

      private void readBytes(int var1, byte[] var2) throws IOException {
         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = (byte)this.readByte();
         }

      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   static enum PwmScale {
      SCALE_1X(1, 0),
      SCALE_256X(256, 1),
      SCALE_64X(64, 2),
      SCALE_8X(8, 3);

      private final int encoding;
      public final int scale;

      static {
         PwmScale[] var0 = new PwmScale[]{SCALE_1X, SCALE_8X, SCALE_64X, SCALE_256X};
      }

      private PwmScale(int var3, int var4) {
         this.scale = var3;
         this.encoding = var4;
      }
   }
}
