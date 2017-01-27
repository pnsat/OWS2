package com.daydr3am.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public class AudioDemo implements OnCompletionListener {
   private static String DB_PATH = "/mnt/sdcard/Resource/Command.Mp3/";
   private static String Sound_PATH = "/mnt/sdcard/Resource/";
   public static String active = "0x31_1.mp3";
   public static Context baseContext;
   public static String close = "0x66_f.mp3";
   public static String close_box = "0x6C_l.mp3";
   public static String open = "0x6F_o.mp3";
   public static String open_box = "0x75_u.mp3";
   public static AudioDemo point;
   public static String standby = "0x30_0.mp3";
   private MediaPlayer mp;
   public int playId;
   public SharedPreferences preferences;

   public static AudioDemo Sound() {
      if(point == null) {
         point = new AudioDemo();
      }

      return point;
   }

   private void goBlooey(Throwable var1) {
   }

   private void pause() {
      this.mp.pause();
   }

   private void stop() {
      this.mp.stop();

      try {
         this.mp.prepare();
         this.mp.seekTo(0);
      } catch (Throwable var2) {
         this.goBlooey(var2);
      }
   }

   public void onCompletion(MediaPlayer var1) {
   }

   public void play() {
      this.stop();
      this.mp.start();
   }

   public void playAdSound(String var1) {
      if(this.mp != null) {
         this.mp.release();
      }

      this.mp = new MediaPlayer();

      try {
         Log.v("hello", "file sound /mnt/sdcard/Resource/save/loaddata/" + var1);
         this.mp.setDataSource("/mnt/sdcard/Resource/save/loaddata/" + var1);
         this.mp.prepare();
         this.mp.seekTo(0);
         this.play();
      } catch (IllegalArgumentException var5) {
         var5.printStackTrace();
      } catch (IllegalStateException var6) {
         var6.printStackTrace();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void playCommand(String var1) {
      if(this.mp != null) {
         this.mp.release();
      }

      this.mp = new MediaPlayer();

      try {
         Log.v("test", DB_PATH + var1);
         this.mp.setDataSource(DB_PATH + var1);
         this.mp.prepare();
         this.play();
         Log.v("test", "end play");
      } catch (IllegalArgumentException var5) {
         var5.printStackTrace();
      } catch (IllegalStateException var6) {
         var6.printStackTrace();
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public void playSound(String var1) {
      if(this.mp != null) {
         this.mp.release();
      }

      try {
         if((new File(Sound_PATH + "/" + var1 + ".mp3")).exists()) {
            this.mp = new MediaPlayer();
            this.mp.setDataSource(Sound_PATH + "/" + var1 + ".mp3");
            this.mp.prepare();
            this.mp.seekTo(0);
            this.play();
         } else {
            int var7 = baseContext.getResources().getIdentifier(var1, "raw", baseContext.getPackageName());
            this.mp = MediaPlayer.create(baseContext, var7);
            this.mp.start();
         }
      } catch (IllegalArgumentException var8) {
         var8.printStackTrace();
      } catch (IllegalStateException var9) {
         var9.printStackTrace();
      } catch (NullPointerException var10) {
         var10.printStackTrace();
      } catch (SecurityException var11) {
         var11.printStackTrace();
      } catch (IOException var12) {
         var12.printStackTrace();
      }
   }
}
