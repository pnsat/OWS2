package ioio.lib.impl;

import ioio.lib.api.IcspMaster;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class IcspMasterImpl extends AbstractResource implements IcspMaster, IncomingState.DataModuleListener {
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private Queue resultQueue_ = new LinkedList();
   private int rxRemaining_ = 0;

   static {
      boolean var0;
      if(!IcspMasterImpl.class.desiredAssertionStatus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      $assertionsDisabled = var0;
   }

   public IcspMasterImpl(IOIOImpl var1) throws ConnectionLostException {
      super(var1);
   }

   private static int byteToInt(byte var0) {
      return var0 & 255;
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();
         this.ioio_.closeIcsp();
      } finally {
         ;
      }

   }

   public void dataReceived(byte[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void disconnected() {
      synchronized(this){}

      try {
         super.disconnected();
         this.notifyAll();
      } finally {
         ;
      }

   }

   public void enterProgramming() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.ioio_.protocol_.icspEnter();
         } catch (IOException var6) {
            throw new ConnectionLostException(var6);
         }
      } finally {
         ;
      }

   }

   public void executeInstruction(int var1) throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.ioio_.protocol_.icspSix(var1);
         } catch (IOException var7) {
            throw new ConnectionLostException(var7);
         }
      } finally {
         ;
      }

   }

   public void exitProgramming() throws ConnectionLostException {
      synchronized(this){}

      try {
         this.checkState();

         try {
            this.ioio_.protocol_.icspExit();
         } catch (IOException var6) {
            throw new ConnectionLostException(var6);
         }
      } finally {
         ;
      }

   }

   public void readVisi() throws ConnectionLostException, InterruptedException {
      // $FF: Couldn't be decompiled
   }

   public void reportAdditionalBuffer(int var1) {
      synchronized(this){}

      try {
         this.rxRemaining_ += var1;
         this.notifyAll();
      } finally {
         ;
      }

   }

   public int waitVisiResult() throws ConnectionLostException, InterruptedException {
      // $FF: Couldn't be decompiled
   }
}
