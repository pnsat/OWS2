package ioio.lib.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

class QueueInputStream extends InputStream {
   private final Queue queue_ = new ArrayBlockingQueue(1024);
   private State state_;

   QueueInputStream() {
      this.state_ = State.OPEN;
   }

   public int available() throws IOException {
      synchronized(this){}

      int var2;
      try {
         var2 = this.queue_.size();
      } finally {
         ;
      }

      return var2;
   }

   public void close() {
      // $FF: Couldn't be decompiled
   }

   public void kill() {
      // $FF: Couldn't be decompiled
   }

   public int read() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public int read(byte[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void write(byte[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private static enum State {
      CLOSED,
      KILLED,
      OPEN;

      static {
         State[] var0 = new State[]{OPEN, CLOSED, KILLED};
      }
   }
}
