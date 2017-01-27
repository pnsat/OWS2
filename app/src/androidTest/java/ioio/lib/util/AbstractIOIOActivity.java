package ioio.lib.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.spi.IOIOConnectionBootstrap;
import ioio.lib.spi.IOIOConnectionFactory;
import ioio.lib.util.IOIOConnectionRegistry;
import ioio.lib.util.android.ContextWrapperDependent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractIOIOActivity extends Activity {
   private static final String TAG = "AbstractIOIOActivity";
   private Collection bootstraps_ = IOIOConnectionRegistry.getBootstraps();
   private IOIOConnectionFactory currentConnectionFactory_;
   private Collection threads_ = new LinkedList();

   static {
      IOIOConnectionRegistry.addBootstraps(new String[]{"ioio.lib.android.accessory.AccessoryConnectionBootstrap", "ioio.lib.android.bluetooth.BluetoothIOIOConnectionBootstrap"});
   }

   private void abortAllThreads() {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).abort();
      }

   }

   private void createAllThreads() {
      this.threads_.clear();
      Iterator var1 = IOIOConnectionRegistry.getConnectionFactories().iterator();

      while(var1.hasNext()) {
         IOIOConnectionFactory var2 = (IOIOConnectionFactory)var1.next();
         this.currentConnectionFactory_ = var2;
         IOIOThread var3 = this.createIOIOThread(var2.getType(), var2.getExtra());
         if(var3 != null) {
            this.threads_.add(var3);
         }
      }

   }

   private void joinAllThreads() throws InterruptedException {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).join();
      }

   }

   private void startAllThreads() {
      Iterator var1 = this.threads_.iterator();

      while(var1.hasNext()) {
         ((IOIOThread)var1.next()).start();
      }

   }

   protected IOIOThread createIOIOThread() {
      throw new RuntimeException("Client must override on of the createIOIOThread overloads!");
   }

   protected IOIOThread createIOIOThread(String var1, Object var2) {
      return this.createIOIOThread();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Iterator var2 = this.bootstraps_.iterator();

      while(var2.hasNext()) {
         IOIOConnectionBootstrap var3 = (IOIOConnectionBootstrap)var2.next();
         if(var3 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var3).onCreate(this);
         }
      }

   }

   protected void onDestroy() {
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).onDestroy();
         }
      }

      super.onDestroy();
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      if((268435456 & var1.getFlags()) != 0) {
         Iterator var2 = this.bootstraps_.iterator();

         while(var2.hasNext()) {
            IOIOConnectionBootstrap var3 = (IOIOConnectionBootstrap)var2.next();
            if(var3 instanceof ContextWrapperDependent) {
               ((ContextWrapperDependent)var3).open();
            }
         }
      }

   }

   protected void onStart() {
      super.onStart();
      Iterator var1 = this.bootstraps_.iterator();

      while(var1.hasNext()) {
         IOIOConnectionBootstrap var2 = (IOIOConnectionBootstrap)var1.next();
         if(var2 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var2).open();
         }
      }

      this.createAllThreads();
      this.startAllThreads();
   }

   protected void onStop() {
      this.abortAllThreads();

      try {
         this.joinAllThreads();
      } catch (InterruptedException var4) {
         ;
      }

      Iterator var2 = this.bootstraps_.iterator();

      while(var2.hasNext()) {
         IOIOConnectionBootstrap var3 = (IOIOConnectionBootstrap)var2.next();
         if(var3 instanceof ContextWrapperDependent) {
            ((ContextWrapperDependent)var3).close();
         }
      }

      super.onStop();
   }

   protected abstract class IOIOThread extends Thread {
      private boolean abort_ = false;
      private boolean connected_ = true;
      private final IOIOConnectionFactory connectionFactory_;
      protected IOIO ioio_;

      protected IOIOThread() {
         this.connectionFactory_ = AbstractIOIOActivity.this.currentConnectionFactory_;
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

      protected void disconnected() {
      }

      protected void incompatible() {
      }

      protected void loop() throws ConnectionLostException, InterruptedException {
         sleep(100000L);
      }

      public final void run() {
         // $FF: Couldn't be decompiled
      }

      protected void setup() throws ConnectionLostException, InterruptedException {
      }
   }
}
