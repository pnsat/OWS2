package com.daydr3am.OWS;

import android.content.Intent;
import android.content.res.Resources;
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
import com.daydr3am.OWS.NumpadUtil;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class SelectOtherUtility extends IORootActivity {
   int ScreenHeight;
   int ScreenWidth;
   final int[] value = new int[]{39, 40, 53, 28, 66, 56};
   final String[] word = new String[]{"อิออน ธนสินทรัพย์", "กรุงศรี เฟิร์สช้อยส์", "บัตรเครดิตเคทีซี/เจบีซี", "บัดดี้บรอดแบนด์", "มิสทีน", "aia"};

   public void changeLanguage(int var1) {
      ((TextView)this.findViewById(2131361934)).setText(MessageTopup.getMessage(6));
      if(this.landTextShow != null) {
         this.landTextShow.setText(MessageTopup.getMessage(6));
      }

   }

   public void landscapeLayout(ViewGroup var1, int var2, int var3, String[] var4) {
      for(int var5 = 0; var5 < var2; ++var5) {
         LinearLayout var6 = (LinearLayout)var1.getChildAt(1 + var5 / 3);
         Button var7 = new Button(this);
         LayoutParams var8 = new LayoutParams(-2, -2);
         var8.setMargins(10, 5, 10, 5);
         var7.setLayoutParams(var8);
         Resources var9 = this.getResources();
         String var10 = "bt_service" + this.value[var5];
         Log.v("hello", "str " + var10);
         int var12 = var9.getIdentifier(var10, "drawable", this.getPackageName());
         Log.v("hello", "str " + var10 + " " + var12);
         var7.setBackgroundDrawable(var9.getDrawable(var12));
         int var14 = 0;

         for(int var15 = 0; var15 < var3; ++var15) {
            var14 += var4.length;
         }

         var7.setTag("" + var5);
         var7.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               int var2 = Integer.parseInt(((Button)var1).getTag().toString());
               Bundle var3 = new Bundle();
               byte var4;
               if(var2 != 4 && var2 != 3) {
                  var4 = 4;
               } else {
                  var4 = 5;
               }

               var3.putInt("DataMax", var4);
               var3.putInt("CurrentData", 0);
               var3.putString("Network", String.valueOf(SelectOtherUtility.this.value[var2]));
               var3.putInt("Index", var2 + 10);
               Intent var5 = new Intent(var1.getContext(), NumpadUtil.class);
               var5.putExtras(var3);
               SelectOtherUtility.this.startActivityForResult(var5, 0);
            }
         });
         var6.addView(var7);
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903064);
      super.onCreate(var1);
      ViewGroup var2 = (ViewGroup)this.findViewById(2131361933);
      TextView var3 = (TextView)this.findViewById(2131361934);
      int var4 = this.word.length;
      AudioDemo.Sound().playSound("c1");
      var3.setText(MessageTopup.getMessage(6));
      String[] var5 = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var5[3]));
      if(this.getResources().getConfiguration().orientation == 2) {
         this.landscapeLayout(var2, var4, 3, this.word);
      } else {
         this.portraitLayout(var2, var4, 3, this.word);
      }
   }

   protected void onResume() {
      super.onResume();
      AudioDemo.Sound().playSound("e100");
      Log.d("debug", "onResume active");
   }

   public void portraitLayout(ViewGroup var1, int var2, int var3, String[] var4) {
      for(int var5 = 0; var5 < var2; ++var5) {
         LinearLayout var6 = (LinearLayout)var1.getChildAt(1 + var5 / 2);
         Log.v("hello", "row " + var5 + " " + (1 + var5 / 2));
         Button var8 = new Button(this);
         var8.setLayoutParams(new LayoutParams(-2, -2, 1.0F));
         var8.setLayoutParams(new LayoutParams(-2, -2));
         Resources var9 = this.getResources();
         String var10 = "bt_service" + this.value[var5];
         Log.v("hello", "str " + var10);
         int var12 = var9.getIdentifier(var10, "drawable", this.getPackageName());
         Log.v("hello", "str " + var10 + " " + var12);
         var8.setBackgroundDrawable(var9.getDrawable(var12));
         int var14 = 0;

         for(int var15 = 0; var15 < var3; ++var15) {
            var14 += var4.length;
         }

         var8.setTag("" + var5);
         var8.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               int var2 = Integer.parseInt(((Button)var1).getTag().toString());
               Bundle var3 = new Bundle();
               byte var4;
               if(var2 != 4 && var2 != 3) {
                  var4 = 4;
               } else {
                  var4 = 5;
               }

               var3.putInt("DataMax", var4);
               var3.putInt("CurrentData", 0);
               var3.putString("Network", String.valueOf(SelectOtherUtility.this.value[var2]));
               var3.putInt("Index", var2 + 10);
               Intent var5 = new Intent(var1.getContext(), NumpadUtil.class);
               var5.putExtras(var3);
               SelectOtherUtility.this.startActivityForResult(var5, 0);
            }
         });
         var6.addView(var8);
      }

   }
}
