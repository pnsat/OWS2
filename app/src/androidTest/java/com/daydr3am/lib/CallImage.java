package com.daydr3am.lib;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.HashMap;

public class CallImage {
   private static String IMAGE_PATH = "/mnt/sdcard/Resource/";
   public static Context baseContext;
   private static HashMap mDrawables_button;
   private static HashMap mDrawables_image;

   public static Drawable getBackground(String var0) {
      try {
         if(mDrawables_image == null) {
            mDrawables_image = new HashMap();
         }

         if(!mDrawables_image.containsKey(var0)) {
            mDrawables_image.put(var0, BitmapDrawable.createFromPath("/sdcard/Resource/save/loaddata/" + var0));
            Log.v("hello", "load path /sdcard/Resource/save/loaddata/" + var0);
            Log.v("hello", "load obj" + BitmapDrawable.createFromPath("/sdcard/Resource/save/loaddata/" + var0));
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      return (Drawable)mDrawables_image.get(var0);
   }

   public static Drawable getimage(String var0) {
      try {
         Drawable var2 = BitmapDrawable.createFromPath(IMAGE_PATH + var0 + ".png");
         return var2;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static Drawable imageDrawableCard(String var0) {
      try {
         if(mDrawables_button == null) {
            mDrawables_button = new HashMap();
         }

         if(!mDrawables_button.containsKey(var0)) {
            Log.v("test", "file name " + var0 + " " + baseContext);
            int var3 = baseContext.getResources().getIdentifier(var0, "drawable", baseContext.getPackageName());
            Drawable var4 = baseContext.getResources().getDrawable(var3);
            Log.v("test", "get base " + var0 + "  " + var4);
            mDrawables_button.put(var0, var4);
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return (Drawable)mDrawables_button.get(var0);
   }
}
