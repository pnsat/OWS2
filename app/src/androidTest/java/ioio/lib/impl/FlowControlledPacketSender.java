package ioio.lib.impl;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FlowControlledPacketSender {
   private boolean closed_ = false;
   private final BlockingQueue queue_ = new ArrayBlockingQueue(256);
   private int readyToSend_ = 0;
   private final Sender sender_;
   private final FlushThread thread_ = new FlushThread();

   public FlowControlledPacketSender(Sender var1) {
      this.sender_ = var1;
      this.thread_.start();
   }

   // $FF: synthetic method
   static BlockingQueue access$0(FlowControlledPacketSender var0) {
      return var0.queue_;
   }

   // $FF: synthetic method
   static int access$1(FlowControlledPacketSender var0) {
      return var0.readyToSend_;
   }

   // $FF: synthetic method
   static void access$2(FlowControlledPacketSender var0, int var1) {
      var0.readyToSend_ = var1;
   }

   // $FF: synthetic method
   static Sender access$3(FlowControlledPacketSender var0) {
      return var0.sender_;
   }

   public void close() {
      synchronized(this){}

      try {
         this.closed_ = true;
         this.thread_.interrupt();
      } finally {
         ;
      }

   }

   public void flush() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void kill() {
      synchronized(this){}

      try {
         this.thread_.interrupt();
      } finally {
         ;
      }

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

   public void write(Packet param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   class FlushThread extends Thread {
      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   interface Packet {
      int getSize();
   }

   interface Sender {
      void send(Packet var1);
   }
}
