package ioio.lib.api;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;

public interface IcspMaster extends Closeable {
   void enterProgramming() throws ConnectionLostException;

   void executeInstruction(int var1) throws ConnectionLostException;

   void exitProgramming() throws ConnectionLostException;

   void readVisi() throws ConnectionLostException, InterruptedException;

   int waitVisiResult() throws ConnectionLostException, InterruptedException;
}
