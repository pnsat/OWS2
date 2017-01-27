package com.daydr3am.OWS;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
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
import com.daydr3am.OWS.DateUtility;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;

public class NumpadUtil extends IORootActivity {
   String[] imageBG = new String[]{"bg_blue", "bg_pink", "bg_green", "bg_orange"};
   int index_background;
   OnClickListener nextAction = new OnClickListener() {
      public void onClick(View var1) {
         Activity var2 = (Activity)((Button)var1).getContext();
         EditText var3 = (EditText)NumpadUtil.this.findViewById(2131361802);
         Log.v("hello", "length " + var3.getText().toString().length());
         Bundle var5 = var2.getIntent().getExtras();
         int var6 = var5.getInt("CurrentData");
         if(var3.getText().toString().length() == 0 && var6 != 6) {
            Intent var21 = new Intent(var1.getContext(), Loading.class);
            var5.putString("ShowError", "กรุณากรอกข้อมูลก่อนไปหน้าถัดไป");
            var21.putExtras(var5);
            NumpadUtil.this.startActivityForResult(var21, 0);
         } else {
            Log.v("hello", "save data " + var6);
            if(var6 == 0) {
               var5.putString("Data1", var3.getText().toString());
            } else if(var6 == 1) {
               var5.putString("Data2", var3.getText().toString());
            } else if(var6 == 2) {
               var5.putString("Data3", var3.getText().toString());
            } else if(var6 == 3) {
               var5.putString("Data4", var3.getText().toString());
            } else if(var6 == 4) {
               var5.putString("Price", var3.getText().toString());
            } else if(var6 == 5) {
               if(var3.getText().toString().length() != 10) {
                  var5.putString("img_background", NumpadUtil.this.imageBG[NumpadUtil.this.index_background]);
                  var5.putString("ShowError", MessageTopup.getMessage(68));
                  Intent var19 = new Intent(var1.getContext(), Loading.class);
                  var19.putExtras(var5);
                  NumpadUtil.this.startActivityForResult(var19, 0);
                  return;
               }

               var5.putString("Mobile", var3.getText().toString());
            } else if(var6 == 6) {
               if(var3.getText().toString().length() == 0) {
                  var5.putString("OTP", "0");
               } else {
                  var5.putString("OTP", var3.getText().toString());
               }
            }

            var5.putInt("CurrentData", var6 + 1);
            if(var5.getString("DataBarcode", "").length() > 0) {
               var5.putInt("Service", 19);
            } else {
               var5.putInt("Service", 11);
            }

            int var8 = var6 + 1;
            var5.putString("img_background", NumpadUtil.this.imageBG[NumpadUtil.this.index_background]);
            Log.v("hello", "cur " + var8 + " max " + var5.getInt("DataMax"));
            if(var8 == NumpadUtil.this.wordinput[var5.getInt("Index")].length) {
               var5.putInt("CurrentData", 4);
               if(var5.getInt("Index") > 4 && var5.getInt("Index") != 15) {
                  Log.v("hello", "current " + var5.getInt("CurrentData"));
                  Intent var17 = new Intent(var1.getContext(), NumpadUtil.class);
                  var17.putExtras(var5);
                  NumpadUtil.this.startActivityForResult(var17, 0);
               } else {
                  Intent var14 = new Intent(var1.getContext(), DateUtility.class);
                  var14.putExtras(var5);
                  NumpadUtil.this.startActivityForResult(var14, 0);
               }
            } else if(var8 == 7) {
               Intent var10 = new Intent(var1.getContext(), Loading.class);
               var10.putExtras(var5);
               NumpadUtil.this.startActivityForResult(var10, 0);
            } else {
               Intent var12 = new Intent(var1.getContext(), NumpadUtil.class);
               var12.putExtras(var5);
               NumpadUtil.this.startActivityForResult(var12, 0);
            }
         }
      }
   };
   OnClickListener numpadClick = new OnClickListener() {
      public void onClick(View var1) {
         EditText var2 = (EditText)NumpadUtil.this.findViewById(2131361802);
         Log.v("hello", "length " + var2.getText().length());
         String var4 = ((Button)var1).getHint().toString();
         if(var4.equals("delete")) {
            if(var2.getText().length() >= 1) {
               var2.setText(var2.getText().toString().toCharArray(), 0, -1 + var2.getText().length());
               if(NumpadUtil.this.getSharedPreferences("hello", 0).getBoolean("Sound", true)) {
                  AudioDemo.Sound().playSound("del");
               }
            }

         } else if(var4.equals("star")) {
            var2.setText(var2.getText().toString() + ".");
         } else {
            if(NumpadUtil.this.getSharedPreferences("hello", 0).getBoolean("Sound", true)) {
               AudioDemo.Sound().playSound("z" + var4);
            }

            var2.setText(var2.getText().toString() + var4);
         }
      }
   };
   final int[] value = new int[]{29, 30, 32, 31, 34, 28, 77, 21, 22, 24};
   final String[] wordFormat = new String[]{"ยอดชำระ", "เบอร์มือถือเพื่อรับผลการชำระ", "กรุณากรอก OTP จากครั้งก่อน หากไม่มีใส่ 0"};
   final String[][] wordinput = new String[][]{{"เลขที่สัญญา", "เลขที่ใบแจ้งหนี้"}, {"หมายเลขผู้ใช้ไฟฟ้า", "รหัสการไฟฟ้า"}, {"เลขที่ใบแจ้งหนี้", "เลขที่ผู้ใช้น้ำ", "เลขที่หน่วยงาน"}, {"หมายเลขสาขา", "หมายเลขเขต", "เลขทะเบียนผู้ใช้น้ำ", "เลขที่ใบแจ้งหนี้"}, {"หมายเลขบริการ", "Account no 14 หลัก", "Invoice No 14 หลัก"}, {"กรุณาระบุเบอร์โทรศัพท์ที่ต้องการชำระ"}, {"กรุณาระบุเบอร์โทรศัพท์ที่ต้องการชำระ"}, {"กรุณาระบุเบอร์โทรศัพท์ที่ต้องการชำระ"}, {"กรุณาระบุเบอร์โทรศัพท์ที่ต้องการชำระ"}, {"กรุณาระบุหมายเลขบริการ"}, {"เลขที่สัญญา,บัตรเครดิต"}, {"หมายเลขสมาชิก/หมายเลขบัญชี"}, {"หมายเลขบัตรเครดิต"}, {"หมายเลขสมาชิก (Account No.)", "เลขที่ (Invoice No.)"}, {"รหัสผู้จำหน่าย", "เลขที่ใบส่งของ"}, {"หมายเลขอ้างอิง"}, new String[0]};

   public void changeLanguage(int var1) {
      TextView var2 = (TextView)this.findViewById(2131361821);
      Bundle var3 = this.getIntent().getExtras();
      if(var3.getInt("CurrentData") < this.wordinput[var3.getInt("Index")].length) {
         var2.setText(this.wordinput[var3.getInt("Index")][var3.getInt("CurrentData")]);
         if(this.landTextShow != null) {
            this.landTextShow.setText(this.wordinput[var3.getInt("Index")][var3.getInt("CurrentData")]);
         }
      } else {
         int var4 = -4 + var3.getInt("CurrentData");
         var2.setText(this.wordFormat[var4]);
         if(this.landTextShow != null) {
            this.landTextShow.setText(this.wordFormat[var4]);
         }
      }

      var2.setTypeface(MessageTopup.setFont(this, 0));
      Button var5 = (Button)this.findViewById(2131361839);
      if(var5 != null) {
         var5.setText(MessageTopup.getMessage(80));
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903058);
      super.onCreate(var1);
      ((EditText)this.findViewById(2131361802)).setEnabled(false);
      Log.d("debug", "MessageTopup >> " + MessageTopup.getMessage(3));
      Log.d("debug", "logo " + this.findViewById(2131361820));
      ImageView var4 = (ImageView)this.findViewById(2131361820);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(this.imageBG[3]));
      this.index_background = 3;
      ViewGroup var5 = (ViewGroup)this.findViewById(2131361824);
      int var6 = var5.getChildCount();

      for(int var7 = 0; var7 < var6; ++var7) {
         ViewGroup var8 = (ViewGroup)var5.getChildAt(var7);
         int var9 = var8.getChildCount();

         for(int var10 = 0; var10 < var9; ++var10) {
            ((Button)var8.getChildAt(var10)).setOnClickListener(this.numpadClick);
         }
      }

      this.next.setOnClickListener(this.nextAction);
      Bundle var11 = this.getIntent().getExtras();
      Resources var12 = this.getResources();
      String var13 = "logo_service" + var11.getString("Network");

      try {
         var4.setImageDrawable(var12.getDrawable(var12.getIdentifier(var13, "drawable", this.getPackageName())));
      } catch (NotFoundException var16) {
         var4.setVisibility(8);
         TextView var15 = (TextView)this.findViewById(2131361888);
         var15.setText(var11.getString("NetworkName", ""));
         var15.setVisibility(0);
      }
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      Bundle var2 = this.getIntent().getExtras();
      int var3 = var2.getInt("Index");
      int var4 = var2.getInt("CurrentData");
      if(var3 <= 9) {
         Log.v("hello", "play name e" + var3 + var4);
         if(var4 < this.wordinput[var3].length) {
            AudioDemo.Sound().playSound("e" + var3 + var4);
         } else {
            AudioDemo.Sound().playSound("e0" + var4);
         }
      } else if(var4 < this.wordinput[var3].length) {
         int var8 = var3 - 10;
         Log.v("hello", "play name f" + var8 + var4);
         AudioDemo.Sound().playSound("f" + var8 + var4);
      } else {
         Log.v("hello", "play name f0" + var4);
         AudioDemo.Sound().playSound("f0" + var4);
      }

      EditText var6 = (EditText)this.findViewById(2131361802);
      if(var2.getInt("Index") == 1 && var2.getInt("CurrentData") == 3) {
         var6.setEnabled(true);
         var6.requestFocus();
         this.findViewById(2131361824).setVisibility(4);
      }

      if(this.findViewById(2131361841) != null) {
         this.findViewById(2131361841).setOnClickListener(this.nextAction);
      }

   }
}
