package ioio.lib.impl;

import android.util.Log;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.FlowControlledPacketSender;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class SpiMasterImpl extends AbstractResource implements SpiMaster, FlowControlledPacketSender.Sender, IncomingState.DataModuleListener {
   private final int clkPinNum_;
   private final int[] indexToSsPin_;
   private final int misoPinNum_;
   private final int mosiPinNum_;
   private final FlowControlledPacketSender outgoing_ = new FlowControlledPacketSender(this);
   private final Queue pendingRequests_ = new ConcurrentLinkedQueue();
   private final int spiNum_;
   private final Map ssPinToIndex_;

   SpiMasterImpl(IOIOImpl var1, int var2, int var3, int var4, int var5, int[] var6) throws ConnectionLostException {
      super(var1);
      this.spiNum_ = var2;
      this.mosiPinNum_ = var3;
      this.misoPinNum_ = var4;
      this.clkPinNum_ = var5;
      this.indexToSsPin_ = (int[])var6.clone();
      this.ssPinToIndex_ = new HashMap(var6.length);

      for(int var7 = 0; var7 < var6.length; ++var7) {
         this.ssPinToIndex_.put(Integer.valueOf(var6[var7]), Integer.valueOf(var7));
      }

   }

   public void close() {
      // $FF: Couldn't be decompiled
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
         this.ioio_.protocol_.spiMasterRequest(this.spiNum_, var2.ssPin_, var2.writeData_, var2.writeSize_, var2.totalSize_, var2.readSize_);
      } catch (IOException var4) {
         Log.e("SpiImpl", "Caught exception", var4);
      }
   }

   public void writeRead(int var1, byte[] var2, int var3, int var4, byte[] var5, int var6) throws ConnectionLostException, InterruptedException {
      this.writeReadAsync(var1, var2, var3, var4, var5, var6).waitReady();
   }

   public void writeRead(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ConnectionLostException, InterruptedException {
      this.writeRead(0, var1, var2, var3, var4, var5);
   }

   public SpiResult writeReadAsync(int param1, byte[] param2, int param3, int param4, byte[] param5, int param6) throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }

   class OutgoingPacket implements FlowControlledPacketSender.Packet {
      int readSize_;
      int ssPin_;
      int totalSize_;
      byte[] writeData_;
      int writeSize_;

      public int getSize() {
         return 4 + this.writeSize_;
      }
   }

   public class SpiResult implements Result {
      final byte[] data_;
      boolean ready_;

      SpiResult(byte[] var2) {
         this.data_ = var2;
      }

      public void waitReady() throws ConnectionLostException, InterruptedException {
         synchronized(this){}

         while(true) {
            boolean var4 = false;

            try {
               var4 = true;
               if(this.ready_ || SpiMasterImpl.this.state_ == AbstractResource.State.DISCONNECTED) {
                  SpiMasterImpl.this.checkState();
                  var4 = false;
                  return;
               }

               this.wait();
               var4 = false;
            } finally {
               if(var4) {
                  ;
               }
            }
         }
      }
   }
}
