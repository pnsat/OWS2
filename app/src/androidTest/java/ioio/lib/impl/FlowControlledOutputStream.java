package ioio.lib.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FlowControlledOutputStream extends OutputStream {
   private boolean closed_ = false;
   private final int maxPacket_;
   private final byte[] packet_;
   private final BlockingQueue queue_ = new ArrayBlockingQueue(1024);
   private int readyToSend_ = 0;
   private final Sender sender_;
   private final FlushThread thread_ = new FlushThread();

   public FlowControlledOutputStream(Sender var1, int var2) {
      this.sender_ = var1;
      this.maxPacket_ = var2;
      this.packet_ = new byte[var2];
      this.thread_.start();
   }

   // $FF: synthetic method
   static int access$0(FlowControlledOutputStream var0) {
      return var0.readyToSend_;
   }

   // $FF: synthetic method
   static BlockingQueue access$1(FlowControlledOutputStream var0) {
      return var0.queue_;
   }

   // $FF: synthetic method
   static int access$2(FlowControlledOutputStream var0) {
      return var0.maxPacket_;
   }

   // $FF: synthetic method
   static byte[] access$3(FlowControlledOutputStream var0) {
      return var0.packet_;
   }

   // $FF: synthetic method
   static void access$4(FlowControlledOutputStream var0, int var1) {
      var0.readyToSend_ = var1;
   }

   // $FF: synthetic method
   static Sender access$5(FlowControlledOutputStream var0) {
      return var0.sender_;
   }

   public void close() {
      // $FF: Couldn't be decompiled
   }

   public void flush() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void readyToSend(int var1) {
      synchronized(this){}

      try {
         this.readyToSend_ += var1;
         this.notifyAll();
      } finally {
         ;
      }

   }

   public void write(int param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   class FlushThread extends Thread {
      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   interface Sender {
      void send(byte[] var1, int var2);
   }
}
