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
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MainPage;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.CallImage;

public class InputPassword extends IORootActivity {
   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361900);
      Button var4 = (Button)this.findViewById(2131361840);
      if(var1 == 1) {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_login_th"));
      } else {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_login_en"));
      }

      int var5 = this.getIntent().getExtras().getInt("PassType");
      if(var5 == 10) {
         var5 = 5;
      }

      var3.setText(MessageTopup.getMessage(45) + var5);
      var3.setTypeface(MessageTopup.setFont(this, 0));
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903053);
      super.onCreate(var1);
      ((EditText)this.findViewById(2131361802)).setEnabled(false);
      ViewGroup var2 = (ViewGroup)this.findViewById(2131361824);
      int var3 = var2.getChildCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         ViewGroup var5 = (ViewGroup)var2.getChildAt(var4);
         int var6 = var5.getChildCount();

         for(int var7 = 0; var7 < var6; ++var7) {
            ((Button)var5.getChildAt(var7)).setOnClickListener(new OnClickListener() {
               public void onClick(View var1) {
                  EditText var2 = (EditText)InputPassword.this.findViewById(2131361802);
                  String var3 = ((Button)var1).getHint().toString();
                  if(var3.equals("delete")) {
                     if(var2.getText().length() >= 1) {
                        var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
                     }

                  } else if(var3.equals("star")) {
                     var2.setText(var2.getText().toString() + "*");
                  } else {
                     var2.setText(var2.getText().toString() + var3);
                  }
               }
            });
         }
      }

      ((Button)this.findViewById(2131361840)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            InputPassword.this.finish();
            EditText var2 = (EditText)InputPassword.this.findViewById(2131361802);
            Bundle var3 = ((Activity)var1.getContext()).getIntent().getExtras();
            var3.putInt("Service", 5);
            if(var3.getInt("PassType") == 3) {
               var3.putInt("Service", 6);
            }

            var3.putString("Password", var2.getText().toString());
            Intent var4 = new Intent(var1.getContext(), Loading.class);
            var4.putExtras(var3);
            InputPassword.this.startActivityForResult(var4, 0);
         }
      });
      ((Button)this.findViewById(2131361901)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = ((Activity)var1.getContext()).getIntent().getExtras();
            if(var2.getInt("PassType") == 1) {
               Intent var3 = new Intent(var1.getContext(), MainPage.class);
               var3.putExtras(var2);
               InputPassword.this.startActivityForResult(var3, 0);
            } else {
               InputPassword.this.finish();
            }
         }
      });
      this.changeLanguage(switchID);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      this.changeLanguage(switchID);
   }
}
