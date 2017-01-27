package ioio.lib.util;

import ioio.lib.api.IOIO;
import ioio.lib.spi.IOIOConnectionFactory;
import ioio.lib.util.IOIOConnectionRegistry;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.IOIOLooperProvider;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class IOIOApplicationHelper {
   protected static final String TAG = "IOIOAndroidApplicationHelper";
   protected Collection bootstraps_ = IOIOConnectionRegistry.getBootstraps();
   protected final IOIOLooperProvider looperProvider_;
   private Collection threads_ = new LinkedList();

   public IOIOApplicationHelper(IOIOLooperProvider var1) {
      this.looperProvider_ = var1;
   }

   protected void abortAllThreads() {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).abort();
      }

   }

   protected void createAllThreads() {
      this.threads_.clear();
      Iterator var1 = IOIOConnectionRegistry.getConnectionFactories().iterator();

      while(var1.hasNext()) {
         IOIOConnectionFactory var2 = (IOIOConnectionFactory)var1.next();
         IOIOLooper var3 = this.looperProvider_.createIOIOLooper(var2.getType(), var2.getExtra());
         if(var3 != null) {
            this.threads_.add(new IOIOThread(var3, var2));
         }
      }

   }

   protected void joinAllThreads() throws InterruptedException {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).join();
      }

   }

   public void start() {
      this.createAllThreads();
      this.startAllThreads();
   }

   protected void startAllThreads() {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).start();
      }

   }

   public void stop() {
      this.abortAllThreads();

      try {
         this.joinAllThreads();
      } catch (InterruptedException var2) {
         ;
      }
   }

   private static class IOIOThread extends Thread {
      private boolean abort_ = false;
      private boolean connected_ = true;
      private final IOIOConnectionFactory connectionFactory_;
      protected IOIO ioio_;
      private final IOIOLooper looper_;

      IOIOThread(IOIOLooper var1, IOIOConnectionFactory var2) {
         this.looper_ = var1;
         this.connectionFactory_ = var2;
      }

      public final void abort() {
         synchronized(this){}

         try {
            this.abort_ = true;
            if(this.ioio_ != null) {
               this.ioio_.disconnect();
            }

            if(this.connected_) {
               this.interrupt();
            }
         } finally {
            ;
         }

      }

      public final void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
