package ioio.lib.api;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IcspMaster;
import ioio.lib.api.PulseInput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.TwiMaster;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.api.exception.IncompatibilityException;

public interface IOIO {
   int INVALID_PIN = -1;
   int LED_PIN;

   void beginBatch() throws ConnectionLostException;

   void disconnect();

   void endBatch() throws ConnectionLostException;

   String getImplVersion(VersionType var1) throws ConnectionLostException;

   State getState();

   void hardReset() throws ConnectionLostException;

   AnalogInput openAnalogInput(int var1) throws ConnectionLostException;

   DigitalInput openDigitalInput(int var1) throws ConnectionLostException;

   DigitalInput openDigitalInput(int var1, DigitalInput.Mode var2) throws ConnectionLostException;

   DigitalInput openDigitalInput(DigitalInput.Spec var1) throws ConnectionLostException;

   DigitalOutput openDigitalOutput(int var1) throws ConnectionLostException;

   DigitalOutput openDigitalOutput(int var1, DigitalOutput.Mode var2, boolean var3) throws ConnectionLostException;

   DigitalOutput openDigitalOutput(int var1, boolean var2) throws ConnectionLostException;

   DigitalOutput openDigitalOutput(DigitalOutput.Spec var1, boolean var2) throws ConnectionLostException;

   IcspMaster openIcspMaster() throws ConnectionLostException;

   PulseInput openPulseInput(int var1, PulseInput.PulseMode var2) throws ConnectionLostException;

   PulseInput openPulseInput(DigitalInput.Spec var1, PulseInput.ClockRate var2, PulseInput.PulseMode var3, boolean var4) throws ConnectionLostException;

   PwmOutput openPwmOutput(int var1, int var2) throws ConnectionLostException;

   PwmOutput openPwmOutput(DigitalOutput.Spec var1, int var2) throws ConnectionLostException;

   SpiMaster openSpiMaster(int var1, int var2, int var3, int var4, SpiMaster.Rate var5) throws ConnectionLostException;

   SpiMaster openSpiMaster(int var1, int var2, int var3, int[] var4, SpiMaster.Rate var5) throws ConnectionLostException;

   SpiMaster openSpiMaster(DigitalInput.Spec var1, DigitalOutput.Spec var2, DigitalOutput.Spec var3, DigitalOutput.Spec[] var4, SpiMaster.Config var5) throws ConnectionLostException;

   TwiMaster openTwiMaster(int var1, TwiMaster.Rate var2, boolean var3) throws ConnectionLostException;

   Uart openUart(int var1, int var2, int var3, Uart.Parity var4, Uart.StopBits var5) throws ConnectionLostException;

   Uart openUart(DigitalInput.Spec var1, DigitalOutput.Spec var2, int var3, Uart.Parity var4, Uart.StopBits var5) throws ConnectionLostException;

   void softReset() throws ConnectionLostException;

   void waitForConnect() throws ConnectionLostException, IncompatibilityException;

   void waitForDisconnect() throws InterruptedException;

   public static enum State {
      CONNECTED,
      DEAD,
      INCOMPATIBLE,
      INIT;

      static {
         State[] var0 = new State[]{INIT, CONNECTED, INCOMPATIBLE, DEAD};
      }
   }

   public static enum VersionType {
      APP_FIRMWARE_VER,
      BOOTLOADER_VER,
      HARDWARE_VER,
      IOIOLIB_VER;

      static {
         VersionType[] var0 = new VersionType[]{HARDWARE_VER, BOOTLOADER_VER, APP_FIRMWARE_VER, IOIOLIB_VER};
      }
   }
}
