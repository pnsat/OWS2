package com.daydr3am.OWS;

import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;

public class Detail extends IORootActivity {
   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361842);
      TextView var4 = (TextView)this.findViewById(2131361843);
      TextView var5 = (TextView)this.findViewById(2131361845);
      TextView var6 = (TextView)this.findViewById(2131361847);
      TextView var7 = (TextView)this.findViewById(2131361849);
      TextView var8 = (TextView)this.findViewById(2131361851);
      TextView var9 = (TextView)this.findViewById(2131361853);
      var3.setText(MessageTopup.getMessage(49));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(50));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(51));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(52));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(53));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(54));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      var9.setTypeface(MessageTopup.setFont(this, 0));
      this.cancel.setVisibility(8);
      this.next.setVisibility(8);
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903045);
      super.onCreate(var1);
      TextView var2 = (TextView)this.findViewById(2131361844);
      TextView var3 = (TextView)this.findViewById(2131361846);
      TextView var4 = (TextView)this.findViewById(2131361848);
      TextView var5 = (TextView)this.findViewById(2131361850);
      TextView var6 = (TextView)this.findViewById(2131361852);
      TextView var7 = (TextView)this.findViewById(2131361854);
      TextView var8 = (TextView)this.findViewById(2131361855);
      TextView var9 = (TextView)this.findViewById(2131361856);
      String[] var10 = this.getIntent().getExtras().getString("returned").split("&");
      String var11 = var10[1].split("=")[1];
      String var12 = var10[2].split("=")[1];
      String var13 = var10[3].split("=")[1];
      String var14 = this.getSharedPreferences("hello", 0).getString("HARDWARE_ID", "");

      String var16;
      try {
         var16 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
      } catch (NameNotFoundException var18) {
         var18.printStackTrace();
         var16 = null;
      }

      var2.setText(var14);
      var3.setText(var16);
      var4.setText(var11);
      var5.setText(var12);
      var6.setText(var13);
      SharedPreferences var17 = this.getSharedPreferences("hello", 0);
      var7.setText(String.valueOf(var17.getInt("MoneyLog", 0)));
      var8.setText(var17.getString("phonenum", ""));
      var9.setText(var17.getString("phonecredit", ""));
      this.changeLanguage(switchID);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      this.changeLanguage(switchID);
   }
}
