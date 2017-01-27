package ioio.lib.spi;

import ioio.lib.api.IOIOConnection;

public interface IOIOConnectionFactory {
   IOIOConnection createConnection();

   Object getExtra();

   String getType();
}
