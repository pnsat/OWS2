package ioio.lib.impl;

import ioio.lib.api.Closeable;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;

class AbstractResource implements Closeable, IncomingState.DisconnectListener {
   protected final IOIOImpl ioio_;
   protected State state_;

   public AbstractResource(IOIOImpl var1) throws ConnectionLostException {
      this.state_ = State.OPEN;
      this.ioio_ = var1;
   }

   protected void checkState() throws ConnectionLostException {
      synchronized(this){}

      try {
         if(this.state_ == State.CLOSED) {
            throw new IllegalStateException("Trying to use a closed resouce");
         }

         if(this.state_ == State.DISCONNECTED) {
            throw new ConnectionLostException();
         }
      } finally {
         ;
      }

   }

   public void close() {
      synchronized(this){}

      try {
         if(this.state_ == State.CLOSED) {
            throw new IllegalStateException("Trying to use a closed resouce");
         }

         if(this.state_ == State.DISCONNECTED) {
            this.state_ = State.CLOSED;
         } else {
            this.state_ = State.CLOSED;
            this.ioio_.removeDisconnectListener(this);
         }
      } finally {
         ;
      }

   }

   public void disconnected() {
      synchronized(this){}

      try {
         if(this.state_ != State.CLOSED) {
            this.state_ = State.DISCONNECTED;
         }
      } finally {
         ;
      }

   }

   static enum State {
      CLOSED,
      DISCONNECTED,
      OPEN;

      static {
         State[] var0 = new State[]{OPEN, CLOSED, DISCONNECTED};
      }
   }
}
