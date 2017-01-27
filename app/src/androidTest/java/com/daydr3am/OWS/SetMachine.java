package com.daydr3am.OWS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.lib.CallImage;
import java.io.File;
import java.util.ArrayList;

public class SetMachine extends IORootActivity {
   private String ID_msg;
   private ArrayList array_sort_msgt = new ArrayList();
   private Bundle bundledata_sort_msg;

   private boolean deleteSms(String var1) {
      try {
         this.getContentResolver().delete(Uri.parse("content://sms/" + var1), (String)null, (String[])null);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private String getContactName(String var1) {
      Uri var2 = PhoneLookup.CONTENT_FILTER_URI;
      String[] var3 = new String[]{"display_name"};

      label17: {
         String[] var8;
         try {
            var2 = (Uri)Class.forName("android.provider.ContactsContract$PhoneLookup").getField("CONTENT_FILTER_URI").get(var2);
            var8 = new String[]{"display_name"};
         } catch (Exception var9) {
            break label17;
         }

         var3 = var8;
      }

      Uri var5 = Uri.withAppendedPath(var2, Uri.encode(var1));
      Cursor var6 = this.getContentResolver().query(var5, var3, (String)null, (String[])null, (String)null);
      String var7 = "";
      if(var6.moveToFirst()) {
         var7 = var6.getString(0);
      }

      var6.close();
      return var7;
   }

   private void readDataSetmechine() {
      String var1 = "818370602";
      if(this.getString(2131230720).equalsIgnoreCase("OWS")) {
         var1 = "818370602";
      } else if(this.getString(2131230720).equalsIgnoreCase("VDC")) {
         var1 = "964269990";
      }

      Log.d("debug", "array_sort_msgt size >>> : " + this.array_sort_msgt.size());

      for(int var3 = 0; var3 < this.array_sort_msgt.size(); ++var3) {
         this.bundledata_sort_msg = (Bundle)this.array_sort_msgt.get(var3);
         String var4 = this.bundledata_sort_msg.getString("messageId");
         String var5 = this.bundledata_sort_msg.getString("address");
         String var6 = this.bundledata_sort_msg.getString("body");
         if(var5.contains("845557930")) {
            this.setDataSetmechine(var6);
            this.ID_msg = var4;
            return;
         }

         if(var5.contains(var1)) {
            this.setDataSetmechine(var6);
            this.ID_msg = var4;
            return;
         }
      }

   }

   private void readSMS() {
      Log.d("debug", "readSMS >>> : ");
      Cursor var2 = this.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id", "thread_id", "address", "person", "date", "body"}, (String)null, (String[])null, "date DESC");
      String var3 = "";

      while(var2.moveToNext()) {
         long var4 = var2.getLong(0);
         long var6 = var2.getLong(1);
         String var8 = var2.getString(2);
         long var9 = var2.getLong(3);
         String var11 = String.valueOf(var9);
         long var12 = var2.getLong(4);
         String var14 = var2.getString(5);
         var3 = var3 + "messageId " + var4 + "\nthreadId " + var6 + "\naddress " + var8 + "\ncontactId " + var9 + "\ncontactId_string " + var11 + "\ntimestamp " + var12 + "\nbody " + var14 + "\n\n\n";
         this.bundledata_sort_msg = new Bundle();
         this.bundledata_sort_msg.putString("messageId", "" + var4);
         this.bundledata_sort_msg.putString("NameContact", this.getContactName(var8));
         this.bundledata_sort_msg.putString("address", var8);
         this.bundledata_sort_msg.putString("timestamp", "" + var12);
         this.bundledata_sort_msg.putString("body", var14);
         this.array_sort_msgt.add(this.bundledata_sort_msg);
      }

      var2.close();
      this.readDataSetmechine();
   }

   private void setDataSetmechine(String var1) {
      String[] var2 = var1.split("/");
      Log.v("hello", "body " + var1);
      EditText var4 = (EditText)this.findViewById(2131361823);
      EditText var5 = (EditText)this.findViewById(2131361822);
      ((EditText)this.findViewById(2131361949)).setText(var2[2]);
      var4.setText(var2[0]);
      var5.setText(var2[1]);
   }

   public void callUSSD(int var1) {
      String var2 = this.getSimOperator();
      Uri var4;
      if(var2.length() == 0) {
         if(var1 == 1) {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "101" + Uri.encode("#"));
         } else {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "102" + Uri.encode("#"));
         }
      } else if(var2.equalsIgnoreCase("ais")) {
         if(var1 == 1) {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "121" + Uri.encode("#"));
         } else {
            var4 = Uri.parse("tel:" + Uri.encode("*") + "545" + Uri.encode("#"));
         }
      } else {
         boolean var3 = var2.equalsIgnoreCase("true-h");
         var4 = null;
         if(var3) {
            if(var1 == 1) {
               var4 = Uri.parse("tel:" + Uri.encode("#") + "123" + Uri.encode("#"));
            } else {
               var4 = Uri.parse("tel:" + Uri.encode("*") + "933" + Uri.encode("#"));
            }
         }
      }

      if(var4 != null) {
         Intent var5 = new Intent("android.intent.action.CALL");
         var5.setData(var4);
         var5.addFlags(268435456);
         var5.addFlags(4);
         this.startActivity(var5);
      } else {
         Log.e("hello", "unknown operator");
      }
   }

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361947);
      Button var4 = (Button)this.findViewById(2131361797);
      Button var5 = (Button)this.findViewById(2131361826);
      Button var6 = (Button)this.findViewById(2131361827);
      if(var1 == 1) {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_test_network_th"));
         var5.setBackgroundDrawable(CallImage.imageDrawableCard("bt_record_th"));
         var6.setBackgroundDrawable(CallImage.imageDrawableCard("bt_back_th"));
      } else {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_test_network_en"));
         var5.setBackgroundDrawable(CallImage.imageDrawableCard("bt_record_en"));
         var6.setBackgroundDrawable(CallImage.imageDrawableCard("bt_back_en"));
      }

      var3.setText(MessageTopup.getMessage(65));
      var3.setTypeface(MessageTopup.setFont(this, 0));
   }

   public String getSimOperator() {
      return ((TelephonyManager)this.getSystemService("phone")).getSimOperatorName();
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903067);
      super.onCreate(var1);
      EditText var2 = (EditText)this.findViewById(2131361823);
      EditText var3 = (EditText)this.findViewById(2131361822);
      EditText var4 = (EditText)this.findViewById(2131361949);
      SharedPreferences var5 = this.getSharedPreferences("hello", 0);
      var2.setText(var5.getString("HARDWARE_ID", ""));
      var3.setText(var5.getString("KEYTABLE", ""));
      var4.setText(var5.getString("PARTNER_ID", ""));
      Button var6 = (Button)this.findViewById(2131361797);
      OnClickListener var7 = new OnClickListener() {
         public void onClick(View var1) {
         }
      };
      var6.setOnClickListener(var7);
      Button var8 = (Button)this.findViewById(2131361826);
      OnClickListener var9 = new OnClickListener() {
         public void onClick(View var1) {
            EditText var2 = (EditText)SetMachine.this.findViewById(2131361823);
            EditText var3 = (EditText)SetMachine.this.findViewById(2131361822);
            EditText var4 = (EditText)SetMachine.this.findViewById(2131361949);
            Editor var5 = SetMachine.this.getSharedPreferences("hello", 0).edit();
            var5.putString("HARDWARE_ID", var2.getText().toString());
            var5.putString("KEYTABLE", var3.getText().toString());
            var5.putString("PARTNER_ID", var4.getText().toString());
            var5.commit();
            SetMachine.this.deleteSms(SetMachine.this.ID_msg);
            SetMachine.this.finish();
         }
      };
      var8.setOnClickListener(var9);
      Button var10 = (Button)this.findViewById(2131361958);
      OnClickListener var11 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
         }
      };
      var10.setOnClickListener(var11);
      Button var12 = (Button)this.findViewById(2131361959);
      OnClickListener var13 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.callUSSD(1);
         }
      };
      var12.setOnClickListener(var13);
      Button var14 = (Button)this.findViewById(2131361960);
      OnClickListener var15 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.callUSSD(0);
         }
      };
      var14.setOnClickListener(var15);
      Button var16 = (Button)this.findViewById(2131361827);
      OnClickListener var17 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.finish();
         }
      };
      var16.setOnClickListener(var17);
      Button var18 = (Button)this.findViewById(2131361955);
      OnClickListener var19 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.readSMS();
         }
      };
      var18.setOnClickListener(var19);
      Button var20 = (Button)this.findViewById(2131361956);
      OnClickListener var21 = new OnClickListener() {
         public void onClick(View var1) {
            SetMachine.this.startActivityForResult(new Intent("android.settings.SETTINGS"), 0);
         }
      };
      var20.setOnClickListener(var21);
      CheckBox var22 = (CheckBox)this.findViewById(2131361954);
      SharedPreferences var23 = this.getSharedPreferences("hello", 0);
      final Editor var24 = var23.edit();
      var22.setChecked(var23.getBoolean("inDebugMode", false));
      OnCheckedChangeListener var25 = new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            var24.putBoolean("inDebugMode", var2);
            var24.commit();
         }
      };
      var22.setOnCheckedChangeListener(var25);
      CheckBox var26 = (CheckBox)this.findViewById(2131361953);
      var26.setChecked(var23.getBoolean("isICT", false));
      OnCheckedChangeListener var27 = new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            Log.v("hello", "is ict " + var2);
            var24.putBoolean("isICT", var2);
            var24.commit();
         }
      };
      var26.setOnCheckedChangeListener(var27);
      CheckBox var28 = (CheckBox)this.findViewById(2131361952);
      var28.setChecked(var23.getBoolean("autoReset", false));
      OnCheckedChangeListener var29 = new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            var24.putBoolean("autoReset", var2);
            var24.commit();
         }
      };
      var28.setOnCheckedChangeListener(var29);
      this.changeLanguage(switchID);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
      final SharedPreferences var2 = this.getSharedPreferences("hello", 0);
      if(var2.getBoolean("DownloadFinish", false)) {
         Button var3 = (Button)this.findViewById(2131361957);
         var3.setTextColor(-16777216);
         var3.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               String var2x = "/mnt/sdcard/Resource/" + var2.getString("DownloadVersion", "") + ".apk";
               Intent var3 = new Intent("android.intent.action.VIEW");
               var3.setDataAndType(Uri.fromFile(new File(var2x)), "application/vnd.android.package-archive");
               SetMachine.this.startActivity(var3);
            }
         });
      }

   }
}
