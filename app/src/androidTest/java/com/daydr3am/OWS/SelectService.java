package com.daydr3am.OWS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.OWS.Numpad;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class SelectService extends IORootActivity {
   int ScreenHeight;
   int ScreenWidth;

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage select service " + var1);
      String var3 = this.getIntent().getExtras().getString("param1");
      TextView var4 = (TextView)this.findViewById(2131361934);
      int var5 = -1 + Integer.parseInt(String.valueOf(var3.charAt(6)));
      if(var5 == 0) {
         var4.setText(MessageTopup.getMessage(5));
         if(this.landTextShow != null) {
            this.landTextShow.setText(MessageTopup.getMessage(5));
         }
      } else if(var5 == 1) {
         var4.setText(MessageTopup.getMessage(6));
         if(this.landTextShow != null) {
            this.landTextShow.setText(MessageTopup.getMessage(6));
            return;
         }
      } else if(var5 == 2) {
         var4.setText(MessageTopup.getMessage(6));
         if(this.landTextShow != null) {
            this.landTextShow.setText(MessageTopup.getMessage(6));
            return;
         }
      }

   }

   public void landscapeLayout(ViewGroup var1, int var2, int var3, String[][] var4) {
      for(int var5 = 0; var5 < var2; ++var5) {
         Log.v("hello", "index at " + var5 / 3);
         LinearLayout var7 = (LinearLayout)var1.getChildAt(1 + var5 / 3);
         Button var8 = new Button(this);
         var8.setBackgroundDrawable(CallImage.imageDrawableCard(var4[var3][var5]));
         LayoutParams var9 = new LayoutParams(-2, -2);
         var9.setMargins(10, 5, 10, 5);
         var8.setLayoutParams(var9);
         int var10 = 0;

         for(int var11 = 0; var11 < var3; ++var11) {
            var10 += var4[var11].length;
         }

         var8.setTag(var5 + var10 + " " + var3 + var5);
         var8.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Button var2 = (Button)var1;
               Activity var3 = (Activity)var2.getContext();
               String var4 = var2.getTag().toString();
               String var5 = var4.split(" ")[1];
               String var6 = var4.split(" ")[0];
               Log.v("test", "tag " + var6);
               StringBuilder var8 = new StringBuilder("N");
               Object[] var9 = new Object[]{Integer.valueOf(1 + Integer.parseInt(var6))};
               var8.append(String.format("%03d", var9)).toString();
               Bundle var11 = var3.getIntent().getExtras();
               var11.putString("param2", String.valueOf(var5.charAt(1)));
               Log.v("hello", "param 2 " + var5.charAt(1) + " " + var6);
               int var13 = Integer.parseInt(String.valueOf(var5.charAt(0)));
               byte var14;
               if(var13 == 0) {
                  var14 = 1;
               } else if(var13 == 1) {
                  var14 = 6;
               } else {
                  var14 = 14;
               }

               Log.v("hello", var14 + " " + Integer.parseInt(String.valueOf(var5.charAt(1))));
               var11.putString("Network", String.valueOf(var14 + Integer.parseInt(String.valueOf(var5.charAt(1)))));
               if(var13 == 0 && Integer.parseInt(String.valueOf(var5.charAt(1))) == 5) {
                  var11.putString("Network", "16");
               }

               Log.v("test", "network number " + var11.getString("Network"));
               Log.v("test", "setting " + var13 + " " + Integer.parseInt(String.valueOf(var5.charAt(1))));
               Intent var18 = new Intent(var1.getContext(), Numpad.class);
               var18.putExtras(var11);
               SelectService.this.startActivityForResult(var18, 0);
            }
         });
         var7.addView(var8);
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903064);
      super.onCreate(var1);
      this.findViewById(2131361863).setVisibility(8);
      String var2 = this.getIntent().getExtras().getString("param1");
      ViewGroup var3 = (ViewGroup)this.findViewById(2131361933);
      TextView var4 = (TextView)this.findViewById(2131361934);
      String[][] var5 = new String[][]{{"bt_one2call", "bt_happy", "bt_true", "bt_hutch", "bt_cat", "bt_true_h"}, {"bt_true_money", "bt_cash", "bt_true_d_serial", "bt_cookie_card", "bt_winner", "bt_tot", "bt_play_card", "bt_gcash"}, {"bt_tolld", "bt_tookdee"}};
      int var6 = -1 + Integer.parseInt(String.valueOf(var2.charAt(6)));
      int var7 = var5[var6].length;
      if(var6 == 0) {
         AudioDemo.Sound().playSound("a1");
         var4.setText(MessageTopup.getMessage(5));
      } else if(var6 == 1) {
         AudioDemo.Sound().playSound("b1");
         var4.setText(MessageTopup.getMessage(6));
      } else if(var6 == 2) {
         AudioDemo.Sound().playSound("c1");
         var4.setText(MessageTopup.getMessage(6));
      }

      String[] var8 = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var8[var6]));
      if(this.getResources().getConfiguration().orientation == 2) {
         this.landscapeLayout(var3, var7, var6, var5);
      } else {
         this.portraitLayout(var3, var7, var6, var5);
      }
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      Log.v("hello", "resume customtime");
   }

   public void portraitLayout(ViewGroup var1, int var2, int var3, String[][] var4) {
      for(int var5 = 0; var5 < var2; ++var5) {
         Log.v("hello", "index at " + var5 / 2);
         LinearLayout var7 = (LinearLayout)var1.getChildAt(1 + var5 / 2);
         Button var8 = new Button(this);
         var8.setBackgroundDrawable(CallImage.imageDrawableCard(var4[var3][var5]));
         var8.setLayoutParams(new LayoutParams(-2, -2, 1.0F));
         var8.setLayoutParams(new LayoutParams(-2, -2));
         int var9 = 0;

         for(int var10 = 0; var10 < var3; ++var10) {
            var9 += var4[var10].length;
         }

         var8.setTag(var5 + var9 + " " + var3 + var5);
         var8.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Button var2 = (Button)var1;
               Activity var3 = (Activity)var2.getContext();
               String var4 = var2.getTag().toString();
               String var5 = var4.split(" ")[1];
               String var6 = var4.split(" ")[0];
               Log.v("test", "tag " + var6);
               StringBuilder var8 = new StringBuilder("N");
               Object[] var9 = new Object[]{Integer.valueOf(1 + Integer.parseInt(var6))};
               var8.append(String.format("%03d", var9)).toString();
               Bundle var11 = var3.getIntent().getExtras();
               var11.putString("param2", String.valueOf(var5.charAt(1)));
               Log.v("hello", "param 2 " + var5.charAt(1) + " " + var6);
               int var13 = Integer.parseInt(String.valueOf(var5.charAt(0)));
               byte var14;
               if(var13 == 0) {
                  var14 = 1;
               } else if(var13 == 1) {
                  var14 = 6;
               } else {
                  var14 = 14;
               }

               Log.v("hello", var14 + " " + Integer.parseInt(String.valueOf(var5.charAt(1))));
               var11.putString("Network", String.valueOf(var14 + Integer.parseInt(String.valueOf(var5.charAt(1)))));
               if(var13 == 0 && Integer.parseInt(String.valueOf(var5.charAt(1))) == 5) {
                  var11.putString("Network", "16");
               }

               Log.v("test", "network number " + var11.getString("Network"));
               Log.v("test", "setting " + var13 + " " + Integer.parseInt(String.valueOf(var5.charAt(1))));
               Intent var18 = new Intent(var1.getContext(), Numpad.class);
               var18.putExtras(var11);
               SelectService.this.startActivityForResult(var18, 0);
            }
         });
         var7.addView(var8);
      }

   }
}
