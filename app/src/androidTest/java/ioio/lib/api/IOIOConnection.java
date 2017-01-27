package ioio.lib.api;

import ioio.lib.api.exception.ConnectionLostException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOIOConnection {
   void disconnect();

   InputStream getInputStream() throws ConnectionLostException;

   OutputStream getOutputStream() throws ConnectionLostException;

   void waitForConnect() throws ConnectionLostException;
}
