package com.daydr3am.OWS;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class SelectPrice extends IORootActivity {
   String[] imageBG = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
   int index_background;
   int[][][] payPrice = new int[][][]{{{10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {20, 50, 100, 300, 500, 800}, {10, 20, 50, 100, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500, 800}}, {{50, 90, 150, 300, 1000}, {28, 55, 89, 159, 189, 245, 349, 450, 888}, {25, 50, 90, 150, 300, 500, 1000}, {25, 50, 90, 150, 300, 500, 1000}, {49, 149, 299, 399, 555, 999}, {25, 55, 149, 199, 399}, {25, 50, 90, 150, 300, 500, 1000}, {50, 100, 200, 300, 500, 1000}, {50, 90, 150, 300}, {49, 99, 149, 299, 499, 799, 999}}, {{100}, {100, 300}}};

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361921);
      var3.setText(MessageTopup.getMessage(9));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      if(this.landTextShow != null) {
         this.landTextShow.setText(MessageTopup.getMessage(9));
      }

   }

   public void landscapeLayout(int var1, int var2) {
      ViewGroup var3 = (ViewGroup)this.findViewById(2131361922);
      int var4 = this.payPrice[var1][var2].length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LinearLayout var6 = (LinearLayout)var3.getChildAt(var5 / 4);
         Button var7 = new Button(this);
         var7.setText(String.valueOf(this.payPrice[var1][var2][var5]));
         var7.setTextSize(10.0F);
         var7.setBackgroundResource(2130837675);
         var7.setLayoutParams(new LayoutParams(-2, -2, 1.0F));
         LayoutParams var8 = new LayoutParams(-2, -2, 1.0F);
         var8.setMargins(5, 5, 0, 0);
         var7.setLayoutParams(var8);
         var7.setHint("" + this.payPrice[var1][var2][var5]);
         var7.setTextSize(40.0F);
         var7.setTypeface((Typeface)null, 1);
         var7.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Button var2 = (Button)var1;
               Activity var3 = (Activity)var2.getContext();
               String var4 = var2.getHint().toString();
               Bundle var5 = var3.getIntent().getExtras();
               var5.putString("param3", var4);
               var5.putString("Price", var4);
               var5.putInt("Service", 1);
               var5.putString("img_background", SelectPrice.this.imageBG[SelectPrice.this.index_background]);
               Intent var6 = new Intent(SelectPrice.this, Loading.class);
               var6.putExtras(var5);
               SelectPrice.this.startActivityForResult(var6, 0);
               AudioDemo.Sound().playSound("a3");
            }
         });
         this.changeLanguage(switchID);
         var6.addView(var7);
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903061);
      super.onCreate(var1);
      this.findViewById(2131361863).setVisibility(8);
      Bundle var2 = this.getIntent().getExtras();
      String var3 = var2.getString("param1");
      String var4 = var2.getString("param2");
      int var5 = -1 + Integer.parseInt(String.valueOf(var3.charAt(6)));
      int var6 = Integer.parseInt(var4);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(this.imageBG[var5]));
      this.index_background = var5;
      if(this.getResources().getConfiguration().orientation == 2) {
         this.landscapeLayout(var5, var6);
      } else {
         this.portraitLayout(var5, var6);
      }
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active price");
      AudioDemo.Sound().playSound("a22");
   }

   public void portraitLayout(int var1, int var2) {
      ViewGroup var3 = (ViewGroup)this.findViewById(2131361922);
      int var4 = this.payPrice[var1][var2].length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LinearLayout var6 = (LinearLayout)var3.getChildAt(var5 / 3);
         Button var7 = new Button(this);
         var7.setText(String.valueOf(this.payPrice[var1][var2][var5]));
         var7.setTextSize(10.0F);
         var7.setBackgroundResource(2130837558);
         var7.setLayoutParams(new LayoutParams(-2, -2, 1.0F));
         var7.setLayoutParams(new LayoutParams(-2, -2, 1.0F));
         var7.setHint("" + this.payPrice[var1][var2][var5]);
         var7.setTextSize(22.0F);
         var7.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Button var2 = (Button)var1;
               Activity var3 = (Activity)var2.getContext();
               String var4 = var2.getHint().toString();
               Bundle var5 = var3.getIntent().getExtras();
               var5.putString("param3", var4);
               var5.putString("Price", var4);
               var5.putInt("Service", 1);
               var5.putString("img_background", SelectPrice.this.imageBG[SelectPrice.this.index_background]);
               Intent var6 = new Intent(SelectPrice.this, Loading.class);
               var6.putExtras(var5);
               SelectPrice.this.startActivityForResult(var6, 0);
               AudioDemo.Sound().playSound("a3");
            }
         });
         this.changeLanguage(switchID);
         var6.addView(var7);
      }

   }
}
