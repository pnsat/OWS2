package ioio.lib.impl;

import ioio.lib.api.IOIOConnection;
import ioio.lib.api.exception.ConnectionLostException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketIOIOConnection implements IOIOConnection {
   private static final String TAG = "SocketIOIOConnection";
   private boolean disconnect_ = false;
   private final int port_;
   private ServerSocket server_ = null;
   private boolean server_owned_by_connect_ = true;
   private Socket socket_ = null;
   private boolean socket_owned_by_connect_ = true;

   public SocketIOIOConnection(int var1) {
      this.port_ = var1;
   }

   public void disconnect() {
      // $FF: Couldn't be decompiled
   }

   public InputStream getInputStream() throws ConnectionLostException {
      try {
         InputStream var2 = this.socket_.getInputStream();
         return var2;
      } catch (IOException var3) {
         throw new ConnectionLostException(var3);
      }
   }

   public OutputStream getOutputStream() throws ConnectionLostException {
      try {
         OutputStream var2 = this.socket_.getOutputStream();
         return var2;
      } catch (IOException var3) {
         throw new ConnectionLostException(var3);
      }
   }

   public void waitForConnect() throws ConnectionLostException {
      // $FF: Couldn't be decompiled
   }
}
