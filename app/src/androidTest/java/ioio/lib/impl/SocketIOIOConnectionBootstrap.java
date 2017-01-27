package ioio.lib.impl;

import ioio.lib.api.IOIOConnection;
import ioio.lib.impl.SocketIOIOConnection;
import ioio.lib.spi.IOIOConnectionBootstrap;
import ioio.lib.spi.IOIOConnectionFactory;
import java.util.Collection;

public class SocketIOIOConnectionBootstrap implements IOIOConnectionBootstrap {
   public static final int IOIO_PORT = 4545;

   public void getFactories(Collection var1) {
      var1.add(new IOIOConnectionFactory() {
         private Integer port_ = new Integer(4545);

         public IOIOConnection createConnection() {
            return new SocketIOIOConnection(4545);
         }

         public Object getExtra() {
            return this.port_;
         }

         public String getType() {
            return SocketIOIOConnection.class.getCanonicalName();
         }
      });
   }
}
