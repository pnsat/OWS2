package com.daydr3am.OWS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.OWS.SelectPrice;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class Numpad extends IORootActivity {
   String[] imageBG = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
   int index_background;
   OnClickListener nextAction = new OnClickListener() {
      public void onClick(View var1) {
         Activity var2 = (Activity)((Button)var1).getContext();
         EditText var3 = (EditText)Numpad.this.findViewById(2131361802);
         Log.v("hello", "length " + var3.getText().toString().length());
         if(var3.getText().toString().length() == 10) {
            Bundle var8 = var2.getIntent().getExtras();
            var8.putString("param4", var3.getText().toString());
            var8.putString("Mobile", var3.getText().toString());
            var8.putInt("Service", 1);
            var8.putString("img_background", Numpad.this.imageBG[Numpad.this.index_background]);
            Intent var9 = new Intent(var1.getContext(), SelectPrice.class);
            var9.putExtras(var8);
            Numpad.this.startActivityForResult(var9, 0);
         } else {
            Bundle var5 = var2.getIntent().getExtras();
            var5.putString("img_background", Numpad.this.imageBG[Numpad.this.index_background]);
            var5.putString("ShowError", MessageTopup.getMessage(68));
            Intent var6 = new Intent(var1.getContext(), Loading.class);
            var6.putExtras(var5);
            Numpad.this.startActivityForResult(var6, 0);
         }
      }
   };
   int[][][] payPrice = new int[][][]{{{10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {20, 50, 100, 300, 500, 800}, {10, 20, 50, 100, 300, 500}}, {{50, 90, 150, 300, 1000}, {28, 55, 89, 159, 189, 245, 349, 450, 888}, {25, 50, 90, 150, 300, 500, 1000}, {25, 50, 90, 150, 300, 500, 1000}, {49, 149, 299, 399, 555, 999}, {25, 55, 149, 199, 399}, {25, 50, 90, 150, 300, 500, 1000}, {50, 100, 200, 300, 500, 1000}, {50, 90, 150, 300}, {49, 99, 149, 299, 499, 799, 999}}, {{100}, {100, 300}}};

   private void setImageNumpad() {
      Button var1 = (Button)this.findViewById(2131361797);
      Button var2 = (Button)this.findViewById(2131361826);
      Button var3 = (Button)this.findViewById(2131361827);
      Button var4 = (Button)this.findViewById(2131361829);
      Button var5 = (Button)this.findViewById(2131361830);
      Button var6 = (Button)this.findViewById(2131361831);
      Button var7 = (Button)this.findViewById(2131361833);
      Button var8 = (Button)this.findViewById(2131361834);
      Button var9 = (Button)this.findViewById(2131361835);
      Button var10 = (Button)this.findViewById(2131361837);
      Button var11 = (Button)this.findViewById(2131361838);
      Button var12 = (Button)this.findViewById(2131361839);
      var1.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var2.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var3.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var5.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var6.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var7.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var8.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var9.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var10.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var11.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
      var12.setBackgroundDrawable(CallImage.imageDrawableCard("bt_number"));
   }

   public void changeLanguage(int var1) {
      TextView var2 = (TextView)this.findViewById(2131361821);
      var2.setText(MessageTopup.getMessage(8));
      var2.setTypeface(MessageTopup.setFont(this, 0));
      Button var3 = (Button)this.findViewById(2131361839);
      if(var3 != null) {
         var3.setText(MessageTopup.getMessage(80));
      }

      if(this.landTextShow != null) {
         this.landTextShow.setText(MessageTopup.getMessage(8));
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903057);
      super.onCreate(var1);
      this.findViewById(2131361863).setVisibility(8);
      String[][] var2 = new String[][]{{"logo_one2call", "logo_happy", "logo_true", "logo_hutch", "logo_cat", "logo_true_h"}, {"logo_true", "logo_cash", "logo_dserial", "logo_cookiecard", "logo_winner", "logo_tot", "logo_play", "logo_gcash"}, {"logo_tolld", "logo_tookdee"}};
      Bundle var3 = this.getIntent().getExtras();
      String var4 = var3.getString("param1");
      String var5 = var3.getString("param2");
      int var6 = -1 + Integer.parseInt(String.valueOf(var4.charAt(6)));
      int var7 = Integer.parseInt(var5);
      Log.v("test", "index " + var6 + " " + var7);
      Log.v("test", "picture " + var2[var6][var7]);
      ((EditText)this.findViewById(2131361802)).setEnabled(false);
      Log.d("debug", "MessageTopup >> " + MessageTopup.getMessage(3));
      Log.d("debug", "logo " + this.findViewById(2131361820));
      ((ImageView)this.findViewById(2131361820)).setImageDrawable(CallImage.imageDrawableCard(var2[var6][var7]));
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(this.imageBG[var6]));
      this.index_background = var6;
      ViewGroup var12 = (ViewGroup)this.findViewById(2131361824);
      int var13 = var12.getChildCount();

      for(int var14 = 0; var14 < var13; ++var14) {
         ViewGroup var15 = (ViewGroup)var12.getChildAt(var14);
         int var16 = var15.getChildCount();

         for(int var17 = 0; var17 < var16; ++var17) {
            Button var18 = (Button)var15.getChildAt(var17);
            OnClickListener var19 = new OnClickListener() {
               public void onClick(View var1) {
                  EditText var2 = (EditText)Numpad.this.findViewById(2131361802);
                  Log.v("hello", "length " + var2.getText().length());
                  String var4 = ((Button)var1).getHint().toString();
                  if(var4.equals("delete")) {
                     if(var2.getText().length() >= 1) {
                        var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
                        if(Numpad.this.getSharedPreferences("hello", 0).getBoolean("Sound", true)) {
                           AudioDemo.Sound().playSound("del");
                        }
                     }
                  } else if(var4.equals("star") && var2.getText().length() < 10) {
                     var2.setText(var2.getText().toString() + "*");
                  } else if(var2.getText().length() < 10) {
                     if(Numpad.this.getSharedPreferences("hello", 0).getBoolean("Sound", true)) {
                        AudioDemo.Sound().playSound("z" + var4);
                     }

                     var2.setText(var2.getText().toString() + var4);
                  }

                  if(var2.getText().length() == 10) {
                     Button var5 = (Button)Numpad.this.findViewById(2131361840);
                     var5.setOnClickListener(Numpad.this.nextAction);
                     var5.setVisibility(0);
                  }

               }
            };
            var18.setOnClickListener(var19);
         }
      }

      this.next.setOnClickListener(this.nextAction);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      AudioDemo.Sound().playSound("a21");
   }
}
