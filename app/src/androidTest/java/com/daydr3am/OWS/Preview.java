package com.daydr3am.OWS;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

class Preview extends SurfaceView implements Runnable {
   private static final String TAG = "Preview";
   public static int count = 0;
   private static boolean inPreview = false;
   public Camera camera;
   private boolean cameraConfigured = false;
   public String hwID;
   SurfaceHolder mHolder;
   private SurfaceHolder previewHolder = null;
   Callback surfaceCallback = new Callback() {
      public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
         Preview.this.initPreview(var3, var4);
         Preview.this.startPreview();
      }

      @SuppressLint({"NewApi"})
      public void surfaceCreated(SurfaceHolder var1) {
         File var2 = new File("/mnt/sdcard/Resource/Camera/");
         int var3 = var2.listFiles().length;
         int var4 = 0;
         if(var3 > 0) {
            var4 = Integer.parseInt(var2.listFiles()[0].getName().replace("rotate", "").replace(".json", ""));
            Log.v("Ant", String.valueOf(var4));
         }

         try {
            Log.v("Ant", "create " + Preview.inPreview);
            Preview.this.camera = Camera.open(1);
            Log.v("Ant", "camera " + Preview.this.camera);
            Preview.this.camera.setDisplayOrientation(var4);
         } catch (Exception var9) {
            Log.v("test", "class " + this + " " + var9.toString());

            try {
               Preview.this.camera = Camera.open(0);
               Preview.this.camera.setDisplayOrientation(var4);
            } catch (Exception var8) {
               ;
            }
         }
      }

      public void surfaceDestroyed(SurfaceHolder var1) {
      }
   };
   Thread th;

   Preview(Context var1) {
      super(var1);
      this.previewHolder = this.getHolder();
      this.previewHolder.addCallback(this.surfaceCallback);
      this.previewHolder.setType(3);
   }

   private Size getBestPreviewSize(int var1, int var2, Parameters var3) {
      Size var4 = null;
      Iterator var5 = var3.getSupportedPreviewSizes().iterator();

      while(var5.hasNext()) {
         Size var6 = (Size)var5.next();
         if(var4 == null) {
            var4 = var6;
         } else {
            int var7 = var4.width * var4.height;
            if(var6.width * var6.height > var7) {
               var4 = var6;
            }
         }
      }

      return var4;
   }

   private void initPreview(int var1, int var2) {
      if(this.camera != null && this.previewHolder.getSurface() != null) {
         try {
            this.camera.setPreviewDisplay(this.previewHolder);
         } catch (Throwable var7) {
            Log.e("PreviewDemo-surfaceCallback", "Exception in setPreviewDisplay()", var7);
         }

         if(!this.cameraConfigured) {
            Parameters var5 = this.camera.getParameters();
            Size var6 = this.getBestPreviewSize(var1, var2, var5);
            if(var6 != null) {
               var5.setPreviewSize(var6.width, var6.height);
               this.camera.setParameters(var5);
               this.cameraConfigured = true;
            }
         }
      }

   }

   private void startPreview() {
      if(this.cameraConfigured && this.camera != null) {
         this.camera.startPreview();
         inPreview = true;
         this.th = new Thread(this);
         this.th.start();
      }

   }

   public void draw(Canvas var1) {
      super.draw(var1);
      Paint var2 = new Paint(-65536);
      Log.d("Preview", "draw");
      var1.drawText("PREVIEW", (float)(var1.getWidth() / 2), (float)(var1.getHeight() / 2), var2);
   }

   public void ftpFunction() {
      // $FF: Couldn't be decompiled
   }

   @TargetApi(9)
   public void pause() {
      Log.v("Ant", "pause");
      if(inPreview && this.camera != null) {
         this.camera.stopPreview();
         this.th.interrupt();
      }

      if(this.camera != null) {
         this.camera.release();
      }

      this.camera = null;
      inPreview = false;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void takePicture() {
      (new TakePictureTask((TakePictureTask)null)).execute(new Void[0]);
   }

   private class TakePictureTask extends AsyncTask {
      private TakePictureTask() {
      }

      // $FF: synthetic method
      TakePictureTask(TakePictureTask var2) {
         this();
      }

      protected Void doInBackground(Void... var1) {
         PictureCallback var2 = new PictureCallback() {
            @SuppressLint({"SdCardPath"})
            public void onPictureTaken(byte[] var1, Camera var2) {
               File var3 = new File("/sdcard/Resource/save/camera/hello" + Preview.count + ".jpg");

               try {
                  FileOutputStream var4 = new FileOutputStream(var3);
                  var4.write(var1);
                  var4.close();
               } catch (IOException var6) {
                  var6.printStackTrace();
               }
            }
         };
         Preview.this.camera.takePicture((ShutterCallback)null, (PictureCallback)null, var2);

         try {
            Thread.sleep(300L);
            return null;
         } catch (InterruptedException var4) {
            var4.printStackTrace();
            return null;
         }
      }

      protected void onPostExecute(Void var1) {
         if(Preview.this.camera != null) {
            Preview.this.camera.startPreview();
         }

      }
   }
}
