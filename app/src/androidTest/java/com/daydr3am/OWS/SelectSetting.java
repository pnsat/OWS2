package com.daydr3am.OWS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.InputPassword;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MainPage;
import com.daydr3am.OWS.Record;
import com.daydr3am.lib.LogController;

public class SelectSetting extends IORootActivity {
   AudioManager audio;

   public void changeLanguage(int param1) {
      // $FF: Couldn't be decompiled
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903065);
      super.onCreate(var1);
      this.getSharedPreferences("hello", 0);
      Button var3 = (Button)this.findViewById(2131361908);
      Button var4 = (Button)this.findViewById(2131361909);
      Button var5 = (Button)this.findViewById(2131361910);
      Button var6 = (Button)this.findViewById(2131361911);
      Button var7 = (Button)this.findViewById(2131361905);
      Button var8 = (Button)this.findViewById(2131361913);
      Button var9 = (Button)this.findViewById(2131361937);
      Button var10 = (Button)this.findViewById(2131361940);
      Button var11 = (Button)this.findViewById(2131361906);
      CheckBox var12 = (CheckBox)this.findViewById(2131361935);
      SharedPreferences var13 = this.getSharedPreferences("hello", 0);
      final Editor var14 = var13.edit();
      var12.setChecked(var13.getBoolean("Sound", true));
      OnCheckedChangeListener var15 = new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            var14.putBoolean("Sound", var2);
            var14.commit();
         }
      };
      var12.setOnCheckedChangeListener(var15);
      this.audio = (AudioManager)this.getSystemService("audio");
      Log.v("hello", "sound " + this.audio.getStreamVolume(1));
      SeekBar var17 = (SeekBar)this.findViewById(2131361938);
      var17.setMax(this.audio.getStreamMaxVolume(3));
      var17.setProgress(this.audio.getStreamVolume(3));
      OnSeekBarChangeListener var18 = new OnSeekBarChangeListener() {
         public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
         }

         public void onStartTrackingTouch(SeekBar var1) {
         }

         public void onStopTrackingTouch(SeekBar var1) {
            SelectSetting.this.audio.setStreamVolume(3, var1.getProgress(), 4);
         }
      };
      var17.setOnSeekBarChangeListener(var18);
      SeekBar var19 = (SeekBar)this.findViewById(2131361939);
      var19.setMax(255);

      try {
         int var32 = System.getInt(this.getContentResolver(), "screen_brightness");
         var19.setProgress(var32);
         Log.v("hello", "previous " + var32);
      } catch (SettingNotFoundException var34) {
         var34.printStackTrace();
      }

      OnSeekBarChangeListener var21 = new OnSeekBarChangeListener() {
         public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
         }

         public void onStartTrackingTouch(SeekBar var1) {
         }

         public void onStopTrackingTouch(SeekBar var1) {
            System.putInt(SelectSetting.this.getContentResolver(), "screen_brightness", var1.getProgress());
         }
      };
      var19.setOnSeekBarChangeListener(var21);
      if(var3 != null) {
         OnClickListener var22 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), Loading.class);
               Bundle var3 = new Bundle();
               var3.putInt("Service", 16);
               var2.putExtras(var3);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var3.setOnClickListener(var22);
      }

      if(var4 != null) {
         OnClickListener var23 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), InputPassword.class);
               Bundle var3 = new Bundle();
               var3.putInt("PassType", 2);
               var2.putExtras(var3);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var4.setOnClickListener(var23);
      }

      if(var5 != null) {
         OnClickListener var24 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), Record.class);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var5.setOnClickListener(var24);
      }

      if(var6 != null) {
         OnClickListener var25 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), InputPassword.class);
               Bundle var3 = new Bundle();
               var3.putInt("PassType", 3);
               var2.putExtras(var3);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var6.setOnClickListener(var25);
      }

      if(var7 != null) {
         OnClickListener var26 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), InputPassword.class);
               Bundle var3 = new Bundle();
               var3.putInt("PassType", 4);
               var2.putExtras(var3);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var7.setOnClickListener(var26);
      }

      if(var8 != null) {
         OnClickListener var27 = new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), InputPassword.class);
               Bundle var3 = new Bundle();
               var3.putInt("PassType", 5);
               var2.putExtras(var3);
               SelectSetting.this.startActivityForResult(var2, 0);
            }
         };
         var8.setOnClickListener(var27);
      }

      if(var9 != null) {
         OnClickListener var28 = new OnClickListener() {
            public void onClick(View var1) {
               try {
                  Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot"}).waitFor();
               } catch (Exception var3) {
                  Log.d("debug", "Could not reboot", var3);
               }
            }
         };
         var9.setOnClickListener(var28);
      }

      if(var10 != null) {
         OnClickListener var29 = new OnClickListener() {
            public void onClick(View var1) {
               Log.v("hello", "button click 09");
               Intent var3 = new Intent(SelectSetting.this.getBaseContext(), MainPage.class);
               var3.setFlags(67108864);
               SelectSetting.this.startActivity(var3);
            }
         };
         var10.setOnClickListener(var29);
      }

      if(var11 != null) {
         OnClickListener var30 = new OnClickListener() {
            public void onClick(View var1) {
               LogController.deleteLog();
            }
         };
         var11.setOnClickListener(var30);
      }

      try {
         this.changeLanguage(switchID);
      } catch (Exception var33) {
         var33.printStackTrace();
      }
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      this.changeLanguage(switchID);
   }
}
