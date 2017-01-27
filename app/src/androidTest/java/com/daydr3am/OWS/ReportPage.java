package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class ReportPage extends IORootActivity {
   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      Log.d("debug", "yai main " + var1);
      RadioButton var4 = (RadioButton)this.findViewById(2131361929);
      RadioButton var5 = (RadioButton)this.findViewById(2131361930);
      RadioButton var6 = (RadioButton)this.findViewById(2131361931);
      RadioButton var7 = (RadioButton)this.findViewById(2131361932);
      TextView var8 = (TextView)this.findViewById(2131361926);
      TextView var9 = (TextView)this.findViewById(2131361927);
      var8.setText(MessageTopup.getMessage(8));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      var9.setText(MessageTopup.getMessage(37));
      var9.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(38));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(39));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(40));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(41));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      String var10 = "";
      if(var1 == 1) {
         var10 = var10 + "_th";
      } else if(var1 == 2) {
         var10 = var10 + "_en";
      }

      if(this.next != null) {
         this.next.setBackgroundDrawable(CallImage.imageDrawableCard("bt_inform" + var10));
      }

      Button var11 = (Button)this.findViewById(2131361839);
      if(var11 != null) {
         var11.setText(MessageTopup.getMessage(80));
      }

      if(this.landTextShow != null) {
         this.landTextShow.setText(MessageTopup.getMessage(37));
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903063);
      super.onCreate(var1);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard("bg_orange"));
      ((EditText)this.findViewById(2131361802)).setEnabled(false);
      ViewGroup var2 = (ViewGroup)this.findViewById(2131361824);
      int var3 = var2.getChildCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         ViewGroup var5 = (ViewGroup)var2.getChildAt(var4);
         int var6 = var5.getChildCount();

         for(int var7 = 0; var7 < var6; ++var7) {
            ((Button)var5.getChildAt(var7)).setOnClickListener(new OnClickListener() {
               public void onClick(View var1) {
                  EditText var2 = (EditText)ReportPage.this.findViewById(2131361802);
                  String var3 = ((Button)var1).getHint().toString();
                  if(var3.equals("delete")) {
                     if(var2.getText().length() >= 1) {
                        var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
                     }
                  } else {
                     if(var3.equals("star") && var2.getText().length() < 10) {
                        var2.setText(var2.getText().toString() + "*");
                        return;
                     }

                     if(var2.getText().length() < 10) {
                        var2.setText(var2.getText().toString() + var3);
                        return;
                     }
                  }

               }
            });
         }
      }

      ((Button)this.findViewById(2131361863)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditText var2 = (EditText)ReportPage.this.findViewById(2131361802);
            Bundle var3 = new Bundle();
            RadioGroup var4 = (RadioGroup)ReportPage.this.findViewById(2131361928);
            int var5 = var4.indexOfChild(var4.findViewById(var4.getCheckedRadioButtonId()));
            String[] var6 = new String[]{"topup not success", "wrong number", "machine problem", "comment"};
            var3.putString("Mobile", var2.getText().toString());
            var3.putString("Word", var6[var5]);
            var3.putInt("Service", 10);
            Intent var7 = new Intent(var1.getContext(), Loading.class);
            var7.putExtras(var3);
            var1.getContext().startActivity(var7);
         }
      });
   }

   protected void onResume() {
      super.onResume();
      this.changeLanguage(switchID);
      Log.d("debug", "onResume active");
      AudioDemo.Sound().playSound("d1");
   }
}
