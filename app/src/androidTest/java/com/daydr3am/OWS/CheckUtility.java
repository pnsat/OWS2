package com.daydr3am.OWS;

import android.content.Intent;
import android.content.res.Resources;
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
import com.daydr3am.OWS.InputCoinUtil;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class CheckUtility extends IORootActivity {
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
      Bundle var3 = this.getIntent().getExtras();
      Resources var4 = this.getResources();
      var2.setImageDrawable(var4.getDrawable(var4.getIdentifier("logo_service" + var3.getString("Network"), "drawable", this.getPackageName())));
      EditText var5 = (EditText)this.findViewById(2131361802);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var3.getString("img_background")));
      var5.setText(var3.getString("Mobile"));
      var5.setEnabled(false);
      String var6 = var3.getString("Price");
      float var7 = Float.parseFloat(var6);
      int var8 = var3.getInt("OR");
      int var9 = var3.getInt("MC");
      TextView var10 = (TextView)this.findViewById(2131361796);
      Object[] var11 = new Object[]{Float.valueOf(Float.parseFloat(var6))};
      var10.setText(String.format("%7.2f", var11));
      ((TextView)this.findViewById(2131361798)).setText(String.valueOf(var8));
      ((TextView)this.findViewById(2131361808)).setText(String.valueOf(var9));
      TextView var12 = (TextView)this.findViewById(2131361811);
      if(var7 + (float)var8 - (float)var9 > 0.0F) {
         Object[] var15 = new Object[]{Float.valueOf(var7 + (float)var8 - (float)var9)};
         var12.setText(String.format("%7.2f", var15));
      } else {
         var12.setText("0");
      }

      Button var13 = (Button)this.findViewById(2131361863);
      OnClickListener var14 = new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = ((CheckUtility)((Button)var1).getContext()).getIntent().getExtras();
            Intent var3 = new Intent(var1.getContext(), InputCoinUtil.class);
            var3.putExtras(var2);
            CheckUtility.this.startActivityForResult(var3, 0);
         }
      };
      var13.setOnClickListener(var14);
   }

   protected void onResume() {
      super.onResume();
      this.changeLanguage(switchID);
      Log.d("debug", "onResume active");
      AudioDemo.Sound().playSound("a4");
      (new Handler()).postDelayed(new Runnable() {
         public void run() {
            if(CheckUtility.this.activityActive) {
               Bundle var1 = CheckUtility.this.getIntent().getExtras();
               Intent var2 = new Intent(CheckUtility.this.getApplicationContext(), InputCoinUtil.class);
               var2.putExtras(var1);
               CheckUtility.this.startActivityForResult(var2, 0);
            }

         }
      }, 4000L);
   }
}
