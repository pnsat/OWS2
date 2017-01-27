package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface AnalogInput extends Closeable {
   int available() throws ConnectionLostException;

   int getOverflowCount() throws ConnectionLostException;

   float getReference();

   float getSampleRate() throws ConnectionLostException;

   float getVoltage() throws InterruptedException, ConnectionLostException;

   float getVoltageBuffered() throws InterruptedException, ConnectionLostException;

   float read() throws InterruptedException, ConnectionLostException;

   float readBuffered() throws InterruptedException, ConnectionLostException;

   void setBuffer(int var1) throws ConnectionLostException;
}
