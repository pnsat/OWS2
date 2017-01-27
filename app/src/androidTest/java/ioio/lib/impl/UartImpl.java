package ioio.lib.impl;

import android.util.Log;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.impl.AbstractResource;
import ioio.lib.impl.FlowControlledOutputStream;
import ioio.lib.impl.IOIOImpl;
import ioio.lib.impl.IncomingState;
import ioio.lib.impl.QueueInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class UartImpl extends AbstractResource implements Uart, FlowControlledOutputStream.Sender, IncomingState.DataModuleListener {
   private static final int MAX_PACKET = 64;
   private final QueueInputStream incoming_ = new QueueInputStream();
   private final FlowControlledOutputStream outgoing_ = new FlowControlledOutputStream(this, 64);
   private final int rxPinNum_;
   private final int txPinNum_;
   private final int uartNum_;

   public UartImpl(IOIOImpl var1, int var2, int var3, int var4) throws ConnectionLostException {
      super(var1);
      this.uartNum_ = var4;
      this.rxPinNum_ = var3;
      this.txPinNum_ = var2;
   }

   public void close() {
      synchronized(this){}

      try {
         super.close();
         this.incoming_.close();
         this.outgoing_.close();
         this.ioio_.closeUart(this.uartNum_);
         if(this.rxPinNum_ != -1) {
            this.ioio_.closePin(this.rxPinNum_);
         }

         if(this.txPinNum_ != -1) {
            this.ioio_.closePin(this.txPinNum_);
         }
      } finally {
         ;
      }

   }

   public void dataReceived(byte[] var1, int var2) {
      this.incoming_.write(var1, var2);
   }

   public void disconnected() {
      synchronized(this){}

      try {
         super.disconnected();
         this.incoming_.kill();
         this.outgoing_.close();
      } finally {
         ;
      }

   }

   public InputStream getInputStream() {
      return this.incoming_;
   }

   public OutputStream getOutputStream() {
      return this.outgoing_;
   }

   public void reportAdditionalBuffer(int var1) {
      this.outgoing_.readyToSend(var1);
   }

   public void send(byte[] var1, int var2) {
      try {
         this.ioio_.protocol_.uartData(this.uartNum_, var2, var1);
      } catch (IOException var4) {
         Log.e("UartImpl", var4.getMessage());
      }
   }
}
