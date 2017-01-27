package ioio.lib.impl;

import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.Constants;
import ioio.lib.impl.IOIOProtocol;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

class IncomingState implements IOIOProtocol.IncomingHandler {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   public String bootloaderId_;
   private ConnectionState connection_;
   private final Set disconnectListeners_;
   public String firmwareId_;
   public String hardwareId_;
   private final DataModuleState icspState_;
   private final DataModuleState[] incapStates_;
   private final InputPinState[] intputPinStates_ = new InputPinState[49];
   private final DataModuleState[] spiStates_ = new DataModuleState[3];
   private final DataModuleState[] twiStates_ = new DataModuleState[3];
   private final DataModuleState[] uartStates_ = new DataModuleState[4];

   static {
      boolean var0;
      if(!IncomingState.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
   }

   public IncomingState() {
      this.incapStates_ = new DataModuleState[2 * Constants.INCAP_MODULES_DOUBLE.length + Constants.INCAP_MODULES_SINGLE.length];
      this.icspState_ = new DataModuleState();
      this.disconnectListeners_ = new HashSet();
      this.connection_ = ConnectionState.INIT;

      for(int var1 = 0; var1 < this.intputPinStates_.length; ++var1) {
         this.intputPinStates_[var1] = new InputPinState();
      }

      for(int var2 = 0; var2 < this.uartStates_.length; ++var2) {
         this.uartStates_[var2] = new DataModuleState();
      }

      for(int var3 = 0; var3 < this.twiStates_.length; ++var3) {
         this.twiStates_[var3] = new DataModuleState();
      }

      for(int var4 = 0; var4 < this.spiStates_.length; ++var4) {
         this.spiStates_[var4] = new DataModuleState();
      }

      for(int var5 = 0; var5 < this.incapStates_.length; ++var5) {
         this.incapStates_[var5] = new DataModuleState();
      }

   }

   private void checkNotDisconnected() throws ConnectionLostException {
      if(this.connection_ == ConnectionState.DISCONNECTED) {
         throw new ConnectionLostException();
      }
   }

   public void addDisconnectListener(DisconnectListener var1) throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkNotDisconnected();
         this.disconnectListeners_.add(var1);
      } finally {
         ;
      }

   }

   public void addIcspListener(DataModuleListener var1) {
      this.icspState_.pushListener(var1);
   }

   public void addIncapListener(int var1, DataModuleListener var2) {
      this.incapStates_[var1].pushListener(var2);
   }

   public void addInputPinListener(int var1, InputPinListener var2) {
      this.intputPinStates_[var1].pushListener(var2);
   }

   public void addSpiListener(int var1, DataModuleListener var2) {
      this.spiStates_[var1].pushListener(var2);
   }

   public void addTwiListener(int var1, DataModuleListener var2) {
      this.twiStates_[var1].pushListener(var2);
   }

   public void addUartListener(int var1, DataModuleListener var2) {
      this.uartStates_[var1].pushListener(var2);
   }

   public void handleAnalogPinStatus(int var1, boolean var2) {
      if(var2) {
         this.intputPinStates_[var1].openNextListener();
      } else {
         this.intputPinStates_[var1].closeCurrentListener();
      }
   }

   public void handleCheckInterfaceResponse(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void handleConnectionLost() {
      // $FF: Couldn't be decompiled
   }

   public void handleEstablishConnection(byte[] param1, byte[] param2, byte[] param3) {
      // $FF: Couldn't be decompiled
   }

   public void handleI2cClose(int var1) {
      this.twiStates_[var1].closeCurrentListener();
   }

   public void handleI2cOpen(int var1) {
      this.twiStates_[var1].openNextListener();
   }

   public void handleI2cReportTxStatus(int var1, int var2) {
      this.twiStates_[var1].reportAdditionalBuffer(var2);
   }

   public void handleI2cResult(int var1, int var2, byte[] var3) {
      this.twiStates_[var1].dataReceived(var3, var2);
   }

   public void handleIcspClose() {
      this.icspState_.closeCurrentListener();
   }

   public void handleIcspOpen() {
      this.icspState_.openNextListener();
   }

   public void handleIcspReportRxStatus(int var1) {
      this.icspState_.reportAdditionalBuffer(var1);
   }

   public void handleIcspResult(int var1, byte[] var2) {
      this.icspState_.dataReceived(var2, var1);
   }

   public void handleIncapClose(int var1) {
      this.incapStates_[var1].closeCurrentListener();
   }

   public void handleIncapOpen(int var1) {
      this.incapStates_[var1].openNextListener();
   }

   public void handleIncapReport(int var1, int var2, byte[] var3) {
      this.incapStates_[var1].dataReceived(var3, var2);
   }

   public void handleRegisterPeriodicDigitalSampling(int var1, int var2) {
      if(!$assertionsDisabled) {
         throw new AssertionError();
      }
   }

   public void handleReportAnalogInStatus(int[] var1, int[] var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.intputPinStates_[var1[var3]].setValue(var2[var3]);
      }

   }

   public void handleReportDigitalInStatus(int var1, boolean var2) {
      InputPinState var3 = this.intputPinStates_[var1];
      byte var4;
      if(var2) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var3.setValue(var4);
   }

   public void handleReportPeriodicDigitalInStatus(int var1, boolean[] var2) {
   }

   public void handleSetChangeNotify(int var1, boolean var2) {
      if(var2) {
         this.intputPinStates_[var1].openNextListener();
      } else {
         this.intputPinStates_[var1].closeCurrentListener();
      }
   }

   public void handleSoftReset() {
      int var1 = 0;
      InputPinState[] var2 = this.intputPinStates_;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2[var4].closeCurrentListener();
      }

      DataModuleState[] var5 = this.uartStates_;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         var5[var7].closeCurrentListener();
      }

      DataModuleState[] var8 = this.twiStates_;
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         var8[var10].closeCurrentListener();
      }

      DataModuleState[] var11 = this.spiStates_;
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         var11[var13].closeCurrentListener();
      }

      DataModuleState[] var14 = this.incapStates_;

      for(int var15 = var14.length; var1 < var15; ++var1) {
         var14[var1].closeCurrentListener();
      }

      this.icspState_.closeCurrentListener();
   }

   public void handleSpiClose(int var1) {
      this.spiStates_[var1].closeCurrentListener();
   }

   public void handleSpiData(int var1, int var2, byte[] var3, int var4) {
      this.spiStates_[var1].dataReceived(var3, var4);
   }

   public void handleSpiOpen(int var1) {
      this.spiStates_[var1].openNextListener();
   }

   public void handleSpiReportTxStatus(int var1, int var2) {
      this.spiStates_[var1].reportAdditionalBuffer(var2);
   }

   public void handleUartClose(int var1) {
      this.uartStates_[var1].closeCurrentListener();
   }

   public void handleUartData(int var1, int var2, byte[] var3) {
      this.uartStates_[var1].dataReceived(var3, var2);
   }

   public void handleUartOpen(int var1) {
      this.uartStates_[var1].openNextListener();
   }

   public void handleUartReportTxStatus(int var1, int var2) {
      this.uartStates_[var1].reportAdditionalBuffer(var2);
   }

   public void removeDisconnectListener(DisconnectListener var1) {
      synchronized(this){}

      try {
         if(this.connection_ != ConnectionState.DISCONNECTED) {
            this.disconnectListeners_.remove(var1);
         }
      } finally {
         ;
      }

   }

   public void waitConnectionEstablished() throws InterruptedException, ConnectionLostException {
      synchronized(this){}

      while(true) {
         boolean var4 = false;

         try {
            var4 = true;
            if(this.connection_ == ConnectionState.INIT) {
               this.wait();
               var4 = false;
               continue;
            }

            if(this.connection_ == ConnectionState.DISCONNECTED) {
               throw new ConnectionLostException();
            }

            var4 = false;
         } finally {
            if(var4) {
               ;
            }
         }

         return;
      }
   }

   public void waitDisconnect() throws InterruptedException {
      // $FF: Couldn't be decompiled
   }

   public boolean waitForInterfaceSupport() throws InterruptedException, ConnectionLostException {
      synchronized(this){}
      boolean var7 = false;

      ConnectionState var2;
      ConnectionState var3;
      try {
         var7 = true;
         if(this.connection_ == ConnectionState.INIT) {
            throw new IllegalStateException("Have to connect before waiting for interface support");
         }

         while(this.connection_ == ConnectionState.ESTABLISHED) {
            this.wait();
         }

         if(this.connection_ == ConnectionState.DISCONNECTED) {
            throw new ConnectionLostException();
         }

         var2 = this.connection_;
         var3 = ConnectionState.CONNECTED;
         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      boolean var4;
      if(var2 == var3) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   static enum ConnectionState {
      CONNECTED,
      DISCONNECTED,
      ESTABLISHED,
      INIT,
      UNSUPPORTED_IID;

      static {
         ConnectionState[] var0 = new ConnectionState[]{INIT, ESTABLISHED, CONNECTED, DISCONNECTED, UNSUPPORTED_IID};
      }
   }

   interface DataModuleListener {
      void dataReceived(byte[] var1, int var2);

      void reportAdditionalBuffer(int var1);
   }

   class DataModuleState {
      // $FF: synthetic field
      static final boolean $assertionsDisabled;
      private boolean currentOpen_ = false;
      private Queue listeners_ = new ConcurrentLinkedQueue();

      static {
         boolean var0;
         if(!IncomingState.class.desiredAssertionStatus()) {
            var0 = true;
         } else {
            var0 = false;
         }

         $assertionsDisabled = var0;
      }

      void closeCurrentListener() {
         if(this.currentOpen_) {
            this.currentOpen_ = false;
            this.listeners_.remove();
         }

      }

      void dataReceived(byte[] var1, int var2) {
         if(!$assertionsDisabled && !this.currentOpen_) {
            throw new AssertionError();
         } else {
            ((DataModuleListener)this.listeners_.peek()).dataReceived(var1, var2);
         }
      }

      void openNextListener() {
         if(!$assertionsDisabled && this.listeners_.isEmpty()) {
            throw new AssertionError();
         } else {
            if(!this.currentOpen_) {
               this.currentOpen_ = true;
            }

         }
      }

      void pushListener(DataModuleListener var1) {
         this.listeners_.add(var1);
      }

      public void reportAdditionalBuffer(int var1) {
         if(!$assertionsDisabled && !this.currentOpen_) {
            throw new AssertionError();
         } else {
            ((DataModuleListener)this.listeners_.peek()).reportAdditionalBuffer(var1);
         }
      }
   }

   interface DisconnectListener {
      void disconnected();
   }

   interface InputPinListener {
      void setValue(int var1);
   }

   class InputPinState {
      // $FF: synthetic field
      static final boolean $assertionsDisabled;
      private boolean currentOpen_ = false;
      private Queue listeners_ = new ConcurrentLinkedQueue();

      static {
         boolean var0;
         if(!IncomingState.class.desiredAssertionStatus()) {
            var0 = true;
         } else {
            var0 = false;
         }

         $assertionsDisabled = var0;
      }

      void closeCurrentListener() {
         if(this.currentOpen_) {
            this.currentOpen_ = false;
            this.listeners_.remove();
         }

      }

      void openNextListener() {
         if(!$assertionsDisabled && this.listeners_.isEmpty()) {
            throw new AssertionError();
         } else {
            if(!this.currentOpen_) {
               this.currentOpen_ = true;
            }

         }
      }

      void pushListener(InputPinListener var1) {
         this.listeners_.add(var1);
      }

      void setValue(int var1) {
         if(!$assertionsDisabled && !this.currentOpen_) {
            throw new AssertionError();
         } else {
            ((InputPinListener)this.listeners_.peek()).setValue(var1);
         }
      }
   }
}
