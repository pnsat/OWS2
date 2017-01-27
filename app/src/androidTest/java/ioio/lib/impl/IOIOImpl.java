package ioio.lib.impl;

import android.util.Log;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.IOIOConnection;
import ioio.lib.api.IcspMaster;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.TwiMaster;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.api.exception.IncompatibilityException;
import ioio.lib.impl.AnalogInputImpl;
import ioio.lib.impl.Constants;
import ioio.lib.impl.DigitalInputImpl;
import ioio.lib.impl.DigitalOutputImpl;
import ioio.lib.impl.IOIOProtocol;
import ioio.lib.impl.IcspMasterImpl;
import ioio.lib.impl.IncapImpl;
import ioio.lib.impl.IncomingState;
import ioio.lib.impl.ModuleAllocator;
import ioio.lib.impl.PinFunctionMap;
import ioio.lib.impl.TwiMasterImpl;
import java.io.IOException;

public class IOIOImpl implements IOIO, IncomingState.DisconnectListener {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$ioio$lib$api$IOIO$VersionType;
   private static final byte[] REQUIRED_INTERFACE_ID = new byte[]{(byte)73, (byte)79, (byte)73, (byte)79, (byte)48, (byte)48, (byte)48, (byte)51};
   private static final String TAG = "IOIOImpl";
   private final IOIOConnection connection_;
   private boolean disconnect_ = false;
   private final ModuleAllocator incapAllocatorDouble_;
   private final ModuleAllocator incapAllocatorSingle_;
   private final IncomingState incomingState_ = new IncomingState();
   private boolean openIcsp_ = false;
   private final boolean[] openPins_ = new boolean[49];
   private final boolean[] openTwi_ = new boolean[3];
   IOIOProtocol protocol_;
   private final ModuleAllocator pwmAllocator_ = new ModuleAllocator(9, "PWM");
   private final ModuleAllocator spiAllocator_ = new ModuleAllocator(3, "SPI");
   private State state_;
   private final ModuleAllocator uartAllocator_ = new ModuleAllocator(4, "UART");

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$ioio$lib$api$IOIO$VersionType() {
      int[] var0 = $SWITCH_TABLE$ioio$lib$api$IOIO$VersionType;
      if(var0 != null) {
         return var0;
      } else {
         int[] var1 = new int[VersionType.values().length];

         try {
            var1[VersionType.APP_FIRMWARE_VER.ordinal()] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            var1[VersionType.BOOTLOADER_VER.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            var1[VersionType.HARDWARE_VER.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            var1[VersionType.IOIOLIB_VER.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
            ;
         }

         $SWITCH_TABLE$ioio$lib$api$IOIO$VersionType = var1;
         return var1;
      }
   }

   public IOIOImpl(IOIOConnection var1) {
      this.incapAllocatorDouble_ = new ModuleAllocator(Constants.INCAP_MODULES_DOUBLE, "INCAP_DOUBLE");
      this.incapAllocatorSingle_ = new ModuleAllocator(Constants.INCAP_MODULES_SINGLE, "INCAP_SINGLE");
      this.state_ = State.INIT;
      this.connection_ = var1;
   }

   private void checkIcspFree() {
      if(this.openIcsp_) {
         throw new IllegalArgumentException("ICSP already open");
      }
   }

   private void checkInterfaceVersion() throws IncompatibilityException, ConnectionLostException, InterruptedException {
      try {
         this.protocol_.checkInterface(REQUIRED_INTERFACE_ID);
      } catch (IOException var2) {
         throw new ConnectionLostException(var2);
      }

      if(!this.incomingState_.waitForInterfaceSupport()) {
         this.state_ = State.INCOMPATIBLE;
         Log.e("IOIOImpl", "Required interface ID is not supported");
         throw new IncompatibilityException("IOIO firmware does not support required firmware: " + new String(REQUIRED_INTERFACE_ID));
      }
   }

   private void checkPinFree(int var1) {
      if(this.openPins_[var1]) {
         throw new IllegalArgumentException("Pin already open: " + var1);
      }
   }

   private void checkState() throws ConnectionLostException {
      if(this.state_ == State.DEAD) {
         throw new ConnectionLostException();
      } else if(this.state_ == State.INCOMPATIBLE) {
         throw new IllegalStateException("Incompatibility has been reported - IOIO cannot be used");
      } else if(this.state_ != State.CONNECTED) {
         throw new IllegalStateException("Connection has not yet been established");
      }
   }

   private void checkTwiFree(int var1) {
      if(this.openTwi_[var1]) {
         throw new IllegalArgumentException("TWI already open: " + var1);
      }
   }

   void addDisconnectListener(IncomingState.DisconnectListener var1) throws ConnectionLostException {
      synchronized(this){}

      try {
         this.incomingState_.addDisconnectListener(var1);
      } finally {
         ;
      }

   }

   public void beginBatch() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();
         this.protocol_.beginBatch();
      } finally {
         ;
      }

   }

   void closeIcsp() {
      synchronized(this){}

      try {
         this.checkState();
         if(!this.openIcsp_) {
            throw new IllegalStateException("ICSP not open");
         }

         this.openIcsp_ = false;
         this.openPins_[Constants.ICSP_PINS[0]] = false;
         this.openPins_[Constants.ICSP_PINS[1]] = false;
         this.protocol_.icspClose();
      } catch (ConnectionLostException var8) {
         ;
      } catch (IOException var9) {
         ;
      } finally {
         ;
      }

   }

   void closeIncap(int param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   void closePin(int var1) {
      synchronized(this){}

      try {
         this.checkState();
         if(!this.openPins_[var1]) {
            throw new IllegalStateException("Pin not open: " + var1);
         }

         this.protocol_.setPinDigitalIn(var1, DigitalInput.Mode.FLOATING);
         this.openPins_[var1] = false;
      } catch (IOException var9) {
         ;
      } catch (ConnectionLostException var10) {
         ;
      } finally {
         ;
      }

   }

   void closePwm(int var1) {
      synchronized(this){}

      try {
         this.checkState();
         this.pwmAllocator_.releaseModule(var1);
         this.protocol_.setPwmPeriod(var1, 0, IOIOProtocol.PwmScale.SCALE_1X);
      } catch (IOException var9) {
         ;
      } catch (ConnectionLostException var10) {
         ;
      } finally {
         ;
      }

   }

   void closeSpi(int var1) {
      synchronized(this){}

      try {
         this.checkState();
         this.spiAllocator_.releaseModule(var1);
         this.protocol_.spiClose(var1);
      } catch (IOException var9) {
         ;
      } catch (ConnectionLostException var10) {
         ;
      } finally {
         ;
      }

   }

   void closeTwi(int var1) {
      synchronized(this){}

      try {
         this.checkState();
         if(!this.openTwi_[var1]) {
            throw new IllegalStateException("TWI not open: " + var1);
         }

         this.openTwi_[var1] = false;
         this.openPins_[Constants.TWI_PINS[var1][0]] = false;
         this.openPins_[Constants.TWI_PINS[var1][1]] = false;
         this.protocol_.i2cClose(var1);
      } catch (IOException var9) {
         ;
      } catch (ConnectionLostException var10) {
         ;
      } finally {
         ;
      }

   }

   void closeUart(int var1) {
      synchronized(this){}

      try {
         this.checkState();
         this.uartAllocator_.releaseModule(var1);
         this.protocol_.uartClose(var1);
      } catch (IOException var9) {
         ;
      } catch (ConnectionLostException var10) {
         ;
      } finally {
         ;
      }

   }

   public void disconnect() {
      // $FF: Couldn't be decompiled
   }

   public void disconnected() {
      // $FF: Couldn't be decompiled
   }

   public void endBatch() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.protocol_.endBatch();
         } catch (IOException var6) {
            throw new ConnectionLostException(var6);
         }
      } finally {
         ;
      }

   }

   public String getImplVersion(VersionType var1) throws ConnectionLostException {
      if(this.state_ == State.INIT) {
         throw new IllegalStateException("Connection has not yet been established");
      } else {
         switch($SWITCH_TABLE$ioio$lib$api$IOIO$VersionType()[var1.ordinal()]) {
         case 1:
            return this.incomingState_.hardwareId_;
         case 2:
            return this.incomingState_.bootloaderId_;
         case 3:
            return this.incomingState_.firmwareId_;
         case 4:
            return "IOIO0323";
         default:
            return null;
         }
      }
   }

   public State getState() {
      return this.state_;
   }

   public void hardReset() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.protocol_.hardReset();
         } catch (IOException var6) {
            throw new ConnectionLostException(var6);
         }
      } finally {
         ;
      }

   }

   public AnalogInput openAnalogInput(int var1) throws ConnectionLostException {
      synchronized(this){}

      AnalogInputImpl var3;
      try {
         this.checkState();
         PinFunctionMap.checkSupportsAnalogInput(var1);
         this.checkPinFree(var1);
         var3 = new AnalogInputImpl(this, var1);
         this.addDisconnectListener(var3);
         this.openPins_[var1] = true;
         this.incomingState_.addInputPinListener(var1, var3);

         try {
            this.protocol_.setPinAnalogIn(var1);
            this.protocol_.setAnalogInSampling(var1, true);
         } catch (IOException var8) {
            var3.close();
            throw new ConnectionLostException(var8);
         }
      } finally {
         ;
      }

      return var3;
   }

   public DigitalInput openDigitalInput(int var1) throws ConnectionLostException {
      return this.openDigitalInput(new DigitalInput.Spec(var1));
   }

   public DigitalInput openDigitalInput(int var1, DigitalInput.Mode var2) throws ConnectionLostException {
      return this.openDigitalInput(new DigitalInput.Spec(var1, var2));
   }

   public DigitalInput openDigitalInput(DigitalInput.Spec var1) throws ConnectionLostException {
      synchronized(this){}

      DigitalInputImpl var3;
      try {
         this.checkState();
         PinFunctionMap.checkValidPin(var1.pin);
         this.checkPinFree(var1.pin);
         var3 = new DigitalInputImpl(this, var1.pin);
         this.addDisconnectListener(var3);
         this.openPins_[var1.pin] = true;
         this.incomingState_.addInputPinListener(var1.pin, var3);

         try {
            this.protocol_.setPinDigitalIn(var1.pin, var1.mode);
            this.protocol_.setChangeNotify(var1.pin, true);
         } catch (IOException var8) {
            var3.close();
            throw new ConnectionLostException(var8);
         }
      } finally {
         ;
      }

      return var3;
   }

   public DigitalOutput openDigitalOutput(int var1) throws ConnectionLostException {
      return this.openDigitalOutput(new DigitalOutput.Spec(var1), false);
   }

   public DigitalOutput openDigitalOutput(int var1, DigitalOutput.Mode var2, boolean var3) throws ConnectionLostException {
      return this.openDigitalOutput(new DigitalOutput.Spec(var1, var2), var3);
   }

   public DigitalOutput openDigitalOutput(int var1, boolean var2) throws ConnectionLostException {
      return this.openDigitalOutput(new DigitalOutput.Spec(var1), var2);
   }

   public DigitalOutput openDigitalOutput(DigitalOutput.Spec var1, boolean var2) throws ConnectionLostException {
      synchronized(this){}

      DigitalOutputImpl var4;
      try {
         this.checkState();
         PinFunctionMap.checkValidPin(var1.pin);
         this.checkPinFree(var1.pin);
         var4 = new DigitalOutputImpl(this, var1.pin, var2);
         this.addDisconnectListener(var4);
         this.openPins_[var1.pin] = true;

         try {
            this.protocol_.setPinDigitalOut(var1.pin, var2, var1.mode);
         } catch (IOException var9) {
            var4.close();
            throw new ConnectionLostException(var9);
         }
      } finally {
         ;
      }

      return var4;
   }

   public IcspMaster openIcspMaster() throws ConnectionLostException {
      synchronized(this){}

      IcspMasterImpl var2;
      try {
         this.checkState();
         this.checkIcspFree();
         this.checkPinFree(Constants.ICSP_PINS[0]);
         this.checkPinFree(Constants.ICSP_PINS[1]);
         this.checkPinFree(Constants.ICSP_PINS[2]);
         this.openPins_[Constants.ICSP_PINS[0]] = true;
         this.openPins_[Constants.ICSP_PINS[1]] = true;
         this.openPins_[Constants.ICSP_PINS[2]] = true;
         this.openIcsp_ = true;
         var2 = new IcspMasterImpl(this);
         this.addDisconnectListener(var2);
         this.incomingState_.addIcspListener(var2);

         try {
            this.protocol_.icspOpen();
         } catch (IOException var7) {
            var2.close();
            throw new ConnectionLostException(var7);
         }
      } finally {
         ;
      }

      return var2;
   }

   public PulseInput openPulseInput(int var1, PulseInput.PulseMode var2) throws ConnectionLostException {
      return this.openPulseInput(new DigitalInput.Spec(var1), PulseInput.ClockRate.RATE_16MHz, var2, true);
   }

   public PulseInput openPulseInput(DigitalInput.Spec var1, PulseInput.ClockRate var2, PulseInput.PulseMode var3, boolean var4) throws ConnectionLostException {
      this.checkState();
      this.checkPinFree(var1.pin);
      PinFunctionMap.checkSupportsPeripheralInput(var1.pin);
      Integer var5;
      if(var4) {
         var5 = this.incapAllocatorDouble_.allocateModule();
      } else {
         var5 = this.incapAllocatorSingle_.allocateModule();
      }

      int var6 = var5.intValue();
      IncapImpl var7 = new IncapImpl(this, var3, var6, var1.pin, var2.hertz, var3.scaling, var4);
      this.addDisconnectListener(var7);
      this.incomingState_.addIncapListener(var6, var7);
      this.openPins_[var1.pin] = true;

      try {
         this.protocol_.setPinDigitalIn(var1.pin, var1.mode);
         this.protocol_.setPinIncap(var1.pin, var6, true);
         this.protocol_.incapConfigure(var6, var4, 1 + var3.ordinal(), var2.ordinal());
         return var7;
      } catch (IOException var9) {
         var7.close();
         throw new ConnectionLostException(var9);
      }
   }

   public PwmOutput openPwmOutput(int var1, int var2) throws ConnectionLostException {
      return this.openPwmOutput(new DigitalOutput.Spec(var1), var2);
   }

   public PwmOutput openPwmOutput(DigitalOutput.Spec param1, int param2) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public SpiMaster openSpiMaster(int var1, int var2, int var3, int var4, SpiMaster.Rate var5) throws ConnectionLostException {
      return this.openSpiMaster(var1, var2, var3, new int[]{var4}, var5);
   }

   public SpiMaster openSpiMaster(int var1, int var2, int var3, int[] var4, SpiMaster.Rate var5) throws ConnectionLostException {
      DigitalOutput.Spec[] var6 = new DigitalOutput.Spec[var4.length];

      for(int var7 = 0; var7 < var4.length; ++var7) {
         var6[var7] = new DigitalOutput.Spec(var4[var7]);
      }

      return this.openSpiMaster(new DigitalInput.Spec(var1, DigitalInput.Mode.PULL_UP), new DigitalOutput.Spec(var2), new DigitalOutput.Spec(var3), var6, new SpiMaster.Config(var5));
   }

   public SpiMaster openSpiMaster(DigitalInput.Spec param1, DigitalOutput.Spec param2, DigitalOutput.Spec param3, DigitalOutput.Spec[] param4, SpiMaster.Config param5) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   public TwiMaster openTwiMaster(int var1, TwiMaster.Rate var2, boolean var3) throws ConnectionLostException {
      synchronized(this){}

      TwiMasterImpl var5;
      try {
         this.checkState();
         this.checkTwiFree(var1);
         this.checkPinFree(Constants.TWI_PINS[var1][0]);
         this.checkPinFree(Constants.TWI_PINS[var1][1]);
         this.openPins_[Constants.TWI_PINS[var1][0]] = true;
         this.openPins_[Constants.TWI_PINS[var1][1]] = true;
         this.openTwi_[var1] = true;
         var5 = new TwiMasterImpl(this, var1);
         this.addDisconnectListener(var5);
         this.incomingState_.addTwiListener(var1, var5);

         try {
            this.protocol_.i2cConfigureMaster(var1, var2, var3);
         } catch (IOException var10) {
            var5.close();
            throw new ConnectionLostException(var10);
         }
      } finally {
         ;
      }

      return var5;
   }

   public Uart openUart(int var1, int var2, int var3, Uart.Parity var4, Uart.StopBits var5) throws ConnectionLostException {
      DigitalInput.Spec var6;
      if(var1 == -1) {
         var6 = null;
      } else {
         var6 = new DigitalInput.Spec(var1);
      }

      DigitalOutput.Spec var7 = null;
      if(var2 != -1) {
         var7 = new DigitalOutput.Spec(var2);
      }

      return this.openUart(var6, var7, var3, var4, var5);
   }

   public Uart openUart(DigitalInput.Spec param1, DigitalOutput.Spec param2, int param3, Uart.Parity param4, Uart.StopBits param5) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   void removeDisconnectListener(IncomingState.DisconnectListener var1) {
      synchronized(this){}

      try {
         this.incomingState_.removeDisconnectListener(var1);
      } finally {
         ;
      }

   }

   public void softReset() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.protocol_.softReset();
         } catch (IOException var6) {
            throw new ConnectionLostException(var6);
         }
      } finally {
         ;
      }

   }

   public void waitForConnect() throws ConnectionLostException, IncompatibilityException {
      // $FF: Couldn't be decompiled
   }

   public void waitForDisconnect() throws InterruptedException {
      this.incomingState_.waitDisconnect();
   }
}
