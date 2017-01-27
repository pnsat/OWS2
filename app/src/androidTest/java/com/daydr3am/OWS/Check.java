package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.InputCoin;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class Check extends IORootActivity {
   public int moneyInput = 20;

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361801);
      TextView var4 = (TextView)this.findViewById(2131361803);
      TextView var5 = (TextView)this.findViewById(2131361805);
      TextView var6 = (TextView)this.findViewById(2131361807);
      TextView var7 = (TextView)this.findViewById(2131361810);
      TextView var8 = (TextView)this.findViewById(2131361804);
      TextView var9 = (TextView)this.findViewById(2131361806);
      TextView var10 = (TextView)this.findViewById(2131361809);
      TextView var11 = (TextView)this.findViewById(2131361812);
      TextView var12 = (TextView)this.findViewById(2131361813);
      var3.setText(MessageTopup.getMessage(11));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(12));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(13));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(14));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(15));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(16));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      var9.setText(MessageTopup.getMessage(16));
      var9.setTypeface(MessageTopup.setFont(this, 0));
      var10.setText(MessageTopup.getMessage(16));
      var10.setTypeface(MessageTopup.setFont(this, 0));
      var11.setText(MessageTopup.getMessage(16));
      var11.setTypeface(MessageTopup.setFont(this, 0));
      var12.setText(MessageTopup.getMessage(17));
      var12.setTypeface(MessageTopup.setFont(this, 0));
      String var13 = "";
      if(var1 == 1) {
         var13 = var13 + "_th";
      } else if(var1 == 2) {
         var13 = var13 + "_en";
      }

      if(this.next != null) {
         this.next.setBackgroundDrawable(CallImage.imageDrawableCard("bt_pay" + var13));
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903042);
      super.onCreate(var1);
      ImageView var2 = (ImageView)this.findViewById(2131361800);
      String[][] var3 = new String[][]{{"logo_one2call", "logo_happy", "logo_true", "logo_hutch", "logo_cat", "logo_true_h"}, {"logo_true", "logo_cash", "logo_dserial", "logo_cookiecard", "logo_winner", "logo_tot", "logo_play", "logo_gcash"}, {"logo_tolld", "logo_tookdee"}};
      Bundle var4 = this.getIntent().getExtras();
      String var5 = var4.getString("param1");
      String var6 = var4.getString("param2");
      int var7 = -1 + Integer.parseInt(String.valueOf(var5.charAt(6)));
      int var8 = Integer.parseInt(var6);
      Log.v("test", "index " + var7 + " " + var8);
      Log.v("test", "picture " + var3[var7][var8]);
      Log.d("debug", "logo " + this.findViewById(2131361820));
      var2.setImageDrawable(CallImage.imageDrawableCard(var3[var7][var8]));
      EditText var12 = (EditText)this.findViewById(2131361802);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var4.getString("img_background")));
      var12.setText(var4.getString("param4"));
      var12.setEnabled(false);
      int var13 = Integer.parseInt(var4.getString("param3"));
      int var14 = var4.getInt("OR");
      int var15 = var4.getInt("MC");
      ((TextView)this.findViewById(2131361796)).setText(String.valueOf(var13));
      ((TextView)this.findViewById(2131361798)).setText(String.valueOf(var14));
      ((TextView)this.findViewById(2131361808)).setText(String.valueOf(var15));
      TextView var16 = (TextView)this.findViewById(2131361811);
      if(var13 + var14 - var15 > 0) {
         var16.setText(String.valueOf(var13 + var14 - var15));
      } else {
         var16.setText("0");
      }

      Button var17 = (Button)this.findViewById(2131361863);
      OnClickListener var18 = new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = ((Check)((Button)var1).getContext()).getIntent().getExtras();
            Intent var3 = new Intent(var1.getContext(), InputCoin.class);
            var3.putExtras(var2);
            Check.this.startActivityForResult(var3, 0);
         }
      };
      var17.setOnClickListener(var18);
   }

   protected void onResume() {
      super.onResume();
      this.changeLanguage(switchID);
      Log.d("debug", "onResume active");
      AudioDemo.Sound().playSound("a4");
      (new Handler()).postDelayed(new Runnable() {
         public void run() {
            if(Check.this.activityActive) {
               Bundle var1 = Check.this.getIntent().getExtras();
               Intent var2 = new Intent(Check.this.getApplicationContext(), InputCoin.class);
               var2.putExtras(var1);
               Check.this.startActivityForResult(var2, 0);
            }

         }
      }, 4000L);
   }
}
