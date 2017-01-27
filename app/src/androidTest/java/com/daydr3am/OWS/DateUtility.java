package com.daydr3am.OWS;

import android.content.res.Resources;
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
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtility extends IORootActivity {
   OnClickListener clickAction = new OnClickListener() {
      public void onClick(View var1) {
         EditText var2 = (EditText)DateUtility.this.findViewById(DateUtility.this.idActive);
         Log.v("hello", "length " + var2.getText().length());
         String var4 = ((Button)var1).getHint().toString();
         if(var4.equals("delete")) {
            if(var2.getText().length() >= 1) {
               var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
               AudioDemo.Sound().playSound("del");
            } else if(var2.getText().length() == 0 && DateUtility.this.idActive == 2131361802) {
               DateUtility.this.idActive = 2131361823;
               var2 = (EditText)DateUtility.this.findViewById(DateUtility.this.idActive);
               var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
               AudioDemo.Sound().playSound("del");
            } else if(var2.getText().length() == 0 && DateUtility.this.idActive == 2131361823) {
               DateUtility.this.idActive = 2131361822;
               var2 = (EditText)DateUtility.this.findViewById(DateUtility.this.idActive);
               var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
               AudioDemo.Sound().playSound("del");
            }
         } else if(var4.equals("star") && var2.getText().length() < 10) {
            var2.setText(var2.getText().toString() + ".");
         } else if(var2.getText().length() < 2) {
            AudioDemo.Sound().playSound("z" + var4);
            var2.setText(var2.getText().toString() + var4);
         }

         if(var2.getText().length() == 2 && DateUtility.this.idActive == 2131361822) {
            DateUtility.this.idActive = 2131361823;
         } else if(var2.getText().length() == 2 && DateUtility.this.idActive == 2131361823) {
            DateUtility.this.idActive = 2131361802;
            return;
         }

      }
   };
   int idActive = 2131361822;
   String[] imageBG = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
   int index_background;
   OnClickListener nextAction = new OnClickListener() {
      public void onClick(View param1) {
         // $FF: Couldn't be decompiled
      }
   };
   int[][][] payPrice = new int[][][]{{{10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {10, 20, 30, 40, 50, 60, 100, 200, 300, 500}, {20, 50, 100, 300, 500, 800}, {10, 20, 50, 100, 300, 500}}, {{50, 90, 150, 300, 1000}, {28, 55, 89, 159, 189, 245, 349, 450, 888}, {25, 50, 90, 150, 300, 500, 1000}, {25, 50, 90, 150, 300, 500, 1000}, {49, 149, 299, 399, 555, 999}, {25, 55, 149, 199, 399}, {25, 50, 90, 150, 300, 500, 1000}, {50, 100, 200, 300, 500, 1000}, {50, 90, 150, 300}, {49, 99, 149, 299, 499, 799, 999}}, {{100}, {100, 300}}};

   public void changeLanguage(int var1) {
      TextView var2 = (TextView)this.findViewById(2131361821);
      var2.setText("กรุณากรอกวันที่ วัน-เดือน-ปี");
      var2.setTypeface(MessageTopup.setFont(this, 0));
      Button var3 = (Button)this.findViewById(2131361839);
      if(var3 != null) {
         var3.setText(MessageTopup.getMessage(80));
      }

      if(this.landTextShow != null) {
         this.landTextShow.setText("วันครบกำหนดชำระ");
      }

   }

   public Date isThisDateValid(String var1, String var2) {
      if(var1 == null) {
         return null;
      } else {
         Log.v("test", "locale " + new Locale("th", "th"));
         SimpleDateFormat var4 = new SimpleDateFormat(var2, new Locale("th", "th"));
         var4.setLenient(false);
         Date var5 = null;

         try {
            var5 = var4.parse(var1);
            System.out.println(var5);
            return var5;
         } catch (ParseException var7) {
            var7.printStackTrace();
            return var5;
         }
      }
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903044);
      super.onCreate(var1);
      this.findViewById(2131361822).setEnabled(false);
      this.findViewById(2131361823).setEnabled(false);
      this.findViewById(2131361802).setEnabled(false);
      Log.d("debug", "MessageTopup >> " + MessageTopup.getMessage(3));
      Log.d("debug", "logo " + this.findViewById(2131361820));
      ImageView var4 = (ImageView)this.findViewById(2131361820);
      Bundle var5 = this.getIntent().getExtras();
      Resources var6 = this.getResources();
      var4.setImageDrawable(var6.getDrawable(var6.getIdentifier("logo_service" + var5.getString("Network"), "drawable", this.getPackageName())));
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(this.imageBG[3]));
      this.index_background = 3;
      ViewGroup var7 = (ViewGroup)this.findViewById(2131361824);
      int var8 = var7.getChildCount();

      for(int var9 = 0; var9 < var8; ++var9) {
         ViewGroup var10 = (ViewGroup)var7.getChildAt(var9);
         int var11 = var10.getChildCount();

         for(int var12 = 0; var12 < var11; ++var12) {
            ((Button)var10.getChildAt(var12)).setOnClickListener(this.clickAction);
         }
      }

      this.next.setOnClickListener(this.nextAction);
   }

   protected void onResume() {
      super.onResume();
      AudioDemo.Sound().playSound("e09");
      if(this.findViewById(2131361841) != null) {
         this.findViewById(2131361841).setOnClickListener(this.nextAction);
      }

   }
}
