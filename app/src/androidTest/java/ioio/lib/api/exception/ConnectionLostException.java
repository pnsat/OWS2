package ioio.lib.api.exception;

public class ConnectionLostException extends Exception {
   private static final long serialVersionUID = 7422862446246046772L;

   public ConnectionLostException() {
      super("Connection lost");
   }

   public ConnectionLostException(Exception var1) {
      super(var1);
   }
}
