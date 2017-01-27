package ioio.lib.impl;

import android.util.Log;
import ioio.lib.api.TwiMaster;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.FlowControlledPacketSender;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class TwiMasterImpl extends AbstractResource implements TwiMaster, FlowControlledPacketSender.Sender, IncomingState.DataModuleListener {
   private final FlowControlledPacketSender outgoing_ = new FlowControlledPacketSender(this);
   private final Queue pendingRequests_ = new ConcurrentLinkedQueue();
   private final int twiNum_;

   TwiMasterImpl(IOIOImpl var1, int var2) throws ConnectionLostException {
      super(var1);
      this.twiNum_ = var2;
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();
         this.outgoing_.close();
         this.ioio_.closeTwi(this.twiNum_);
      } finally {
         ;
      }

   }

   public void dataReceived(byte[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void disconnected() {
      // $FF: Couldn't be decompiled
   }

   public void reportAdditionalBuffer(int var1) {
      this.outgoing_.readyToSend(var1);
   }

   public void send(FlowControlledPacketSender.Packet var1) {
      OutgoingPacket var2 = (OutgoingPacket)var1;

      try {
         this.ioio_.protocol_.i2cWriteRead(this.twiNum_, var2.tenBitAddr_, var2.addr_, var2.writeSize_, var2.readSize_, var2.writeData_);
      } catch (IOException var4) {
         Log.e("TwiImpl", "Caught exception", var4);
      }
   }

   public boolean writeRead(int var1, boolean var2, byte[] var3, int var4, byte[] var5, int var6) throws ConnectionLostException, InterruptedException {
      return this.writeReadAsync(var1, var2, var3, var4, var5, var6).waitReady();
   }

   public Result writeReadAsync(int param1, boolean param2, byte[] param3, int param4, byte[] param5, int param6) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   class OutgoingPacket implements FlowControlledPacketSender.Packet {
      int addr_;
      int readSize_;
      boolean tenBitAddr_;
      byte[] writeData_;
      int writeSize_;

      public int getSize() {
         return 4 + this.writeSize_;
      }
   }

   class TwiResult implements Result {
      final byte[] data_;
      boolean ready_ = false;
      boolean success_;

      public TwiResult(byte[] var2) {
         this.data_ = var2;
      }

      public boolean waitReady() throws ConnectionLostException, InterruptedException {
         synchronized(this){}

         while(true) {
            boolean var5 = false;

            try {
               var5 = true;
               if(this.ready_ || TwiMasterImpl.this.state_ == AbstractResource.State.DISCONNECTED) {
                  TwiMasterImpl.this.checkState();
                  boolean var2 = this.success_;
                  var5 = false;
                  return var2;
               }

               this.wait();
               var5 = false;
            } finally {
               if(var5) {
                  ;
               }
            }
         }
      }
   }
}
