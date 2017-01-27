package com.daydr3am.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class PictureDemo extends Activity {
   private Camera camera = null;
   private boolean cameraConfigured = false;
   private boolean inPreview = false;
   PictureCallback photoCallback = new PictureCallback() {
      public void onPictureTaken(byte[] var1, Camera var2) {
         (PictureDemo.this.new SavePhotoTask()).execute(new byte[][]{var1});
         var2.startPreview();
         PictureDemo.this.inPreview = true;
      }
   };
   public SurfaceView preview = null;
   private SurfaceHolder previewHolder = null;
   Callback surfaceCallback = new Callback() {
      public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
         PictureDemo.this.initPreview(var3, var4);
         PictureDemo.this.startPreview();
      }

      public void surfaceCreated(SurfaceHolder var1) {
      }

      public void surfaceDestroyed(SurfaceHolder var1) {
      }
   };

   private Size getBestPreviewSize(int var1, int var2, Parameters var3) {
      Size var4 = null;
      Iterator var5 = var3.getSupportedPreviewSizes().iterator();

      while(var5.hasNext()) {
         Size var6 = (Size)var5.next();
         if(var6.width <= var1 && var6.height <= var2) {
            if(var4 == null) {
               var4 = var6;
            } else {
               int var7 = var4.width * var4.height;
               if(var6.width * var6.height > var7) {
                  var4 = var6;
               }
            }
         }
      }

      return var4;
   }

   private Size getSmallestPictureSize(Parameters var1) {
      Size var2 = null;
      Iterator var3 = var1.getSupportedPictureSizes().iterator();

      while(var3.hasNext()) {
         Size var4 = (Size)var3.next();
         if(var2 == null) {
            var2 = var4;
         } else {
            int var5 = var2.width * var2.height;
            if(var4.width * var4.height < var5) {
               var2 = var4;
            }
         }
      }

      return var2;
   }

   private void initPreview(int var1, int var2) {
      if(this.camera != null && this.previewHolder.getSurface() != null) {
         try {
            this.camera.setPreviewDisplay(this.previewHolder);
         } catch (Throwable var8) {
            Log.e("PreviewDemo-surfaceCallback", "Exception in setPreviewDisplay()", var8);
            Toast.makeText(this, var8.getMessage(), 1).show();
         }

         if(!this.cameraConfigured) {
            Parameters var5 = this.camera.getParameters();
            Size var6 = this.getBestPreviewSize(var1, var2, var5);
            Size var7 = this.getSmallestPictureSize(var5);
            if(var6 != null && var7 != null) {
               var5.setPreviewSize(var6.width, var6.height);
               var5.setPictureSize(var7.width, var7.height);
               var5.setPictureFormat(256);
               this.camera.setParameters(var5);
               this.cameraConfigured = true;
            }
         }
      }

   }

   private void startPreview() {
      if(this.cameraConfigured && this.camera != null) {
         this.camera.startPreview();
         this.inPreview = true;
      }

   }

   public void onCreate(Bundle var1) {
      Log.v("Ant", "create");
      this.previewHolder = this.preview.getHolder();
      this.previewHolder.addCallback(this.surfaceCallback);
      this.previewHolder.setType(3);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      Log.v("Ant", "createoption");
      (new MenuInflater(this)).inflate(2131296256, var1);
      return super.onCreateOptionsMenu(var1);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      Log.v("Ant", "item");
      return super.onOptionsItemSelected(var1);
   }

   public void onPause() {
      Log.v("Ant", "pause");
      if(this.inPreview) {
         this.camera.stopPreview();
      }

      this.camera.release();
      this.camera = null;
      this.inPreview = false;
   }

   @TargetApi(9)
   public void onResume() {
      if(VERSION.SDK_INT >= 9) {
         CameraInfo var1 = new CameraInfo();
         Log.v("test", "" + Camera.getNumberOfCameras());

         for(int var3 = 0; var3 < Camera.getNumberOfCameras(); ++var3) {
            Camera.getCameraInfo(var3, var1);
            if(var1.facing == 1) {
               this.camera = Camera.open(var3);
            }
         }
      }

      if(this.camera == null) {
         this.camera = Camera.open();
      }

      this.startPreview();
   }

   public void setPreview(SurfaceView var1) {
      this.preview = var1;
   }

   class SavePhotoTask extends AsyncTask {
      protected String doInBackground(byte[]... var1) {
         File var2 = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
         if(var2.exists()) {
            var2.delete();
         }

         try {
            FileOutputStream var3 = new FileOutputStream(var2.getPath());
            var3.write(var1[0]);
            var3.close();
         } catch (IOException var5) {
            Log.e("PictureDemo", "Exception in photoCallback", var5);
         }

         return null;
      }
   }
}
