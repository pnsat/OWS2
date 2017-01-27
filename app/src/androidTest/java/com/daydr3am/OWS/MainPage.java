package com.daydr3am.OWS;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.daydr3am.OWS.Barcode;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.OWS.ReportPage;
import com.daydr3am.OWS.SampleActivity;
import com.daydr3am.OWS.SelectOtherUtility;
import com.daydr3am.OWS.SelectService;
import com.daydr3am.OWS.SelectUtility;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;
import com.daydr3am.lib.ConnectService;
import com.daydr3am.lib.IOService;
import com.daydr3am.lib.IOServiceBV20;
import com.daydr3am.lib.LogChecker;
import com.daydr3am.lib.Service;
import java.util.Calendar;

public class MainPage extends IORootActivity {
   static boolean isBV20;
   static boolean pause = false;
   static Thread th;
   boolean passCreate;
   SharedPreferences preferences;
   public ServiceConnection service = new ServiceConnection() {
      public void onServiceConnected(ComponentName var1, IBinder var2) {
         MainPage.this.setting = (IOService.SettingService)var2;
         Log.v("hello", "already bind" + MainPage.this.setting);
         MainPage.this.setting.serviceSetup(MainPage.this);
      }

      public void onServiceDisconnected(ComponentName var1) {
      }
   };
   IOService.SettingService setting;
   private final BroadcastReceiver ussdCatcherReceiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getStringExtra("extra_message");
         String var4 = var2.getStringExtra("extra_cutmessage");
         int var5 = Calendar.getInstance().get(11);
         String var6 = "message2";
         String var7;
         if(var5 == 18) {
            var7 = "phonenumraw";
            var6 = "phonenum";
         } else if(var5 == 1) {
            var7 = "phonecreditraw";
            var6 = "phonecredit";
         } else {
            var7 = "message";
         }

         Editor var8 = MainPage.this.getSharedPreferences("hello", 0).edit();
         var8.putString(var7, var3);
         var8.putString(var6, var4);
         var8.commit();
         Toast.makeText(MainPage.this, var3, 1).show();
      }
   };

   public void ImageButton(int var1, int var2) {
      Button var3 = (Button)this.findViewById(2131361908);
      Button var4 = (Button)this.findViewById(2131361909);
      Button var5 = (Button)this.findViewById(2131361910);
      Button var6 = (Button)this.findViewById(2131361911);
      Button var7 = (Button)this.findViewById(2131361905);
      Button var8 = (Button)this.findViewById(2131361906);
      String var9;
      if(var2 == 1) {
         var9 = "";
      } else {
         var9 = null;
         if(var2 == 2) {
            var9 = "_on";
         }
      }

      if(switchID == 1) {
         if(var1 == 1) {
            var3.setBackgroundDrawable(CallImage.imageDrawableCard("mobile_topup_th" + var9));
         }

         if(var1 == 2) {
            var4.setBackgroundDrawable(CallImage.imageDrawableCard("game_th" + var9));
         }

         if(var1 == 3) {
            var5.setBackgroundDrawable(CallImage.imageDrawableCard("card_th" + var9));
         }

         if(var1 == 4) {
            var6.setBackgroundDrawable(CallImage.imageDrawableCard("billing_th" + var9));
         }

         if(var1 == 5) {
            var7.setBackgroundDrawable(CallImage.imageDrawableCard("how_to_use_th" + var9));
         }

         if(var1 == 11) {
            var8.setBackgroundDrawable(CallImage.imageDrawableCard("report_th" + var9));
         }
      } else if(switchID == 2) {
         if(var1 == 1) {
            var3.setBackgroundDrawable(CallImage.imageDrawableCard("mobile_topup_en" + var9));
         }

         if(var1 == 2) {
            var4.setBackgroundDrawable(CallImage.imageDrawableCard("game_en" + var9));
         }

         if(var1 == 3) {
            var5.setBackgroundDrawable(CallImage.imageDrawableCard("card_en" + var9));
         }

         if(var1 == 4) {
            var6.setBackgroundDrawable(CallImage.imageDrawableCard("billing_en" + var9));
         }

         if(var1 == 5) {
            var7.setBackgroundDrawable(CallImage.imageDrawableCard("how_to_use_en" + var9));
         }

         if(var1 == 11) {
            var8.setBackgroundDrawable(CallImage.imageDrawableCard("report_en" + var9));
            return;
         }
      }

   }

   public void LandScapeEachPage() {
      if(this.landBack != null) {
         Button var2 = (Button)this.landBack;
         var2.setText("แจ้งปัญหา       ");
         var2.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Intent var2 = new Intent(var1.getContext(), ReportPage.class);
               var1.getContext().startActivity(var2);
            }
         });
      }

      Log.v("hello", "land show " + this.landTextShow);
      if(this.landTextShow != null) {
         this.landTextShow.setText("เลือกบริการที่ท่านต้องการ");
      }

   }

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      Log.d("debug", "changeLanguage MessageTopup >> " + MessageTopup.getMessage(5));
      Button var4 = (Button)this.findViewById(2131361908);
      Button var5 = (Button)this.findViewById(2131361909);
      Button var6 = (Button)this.findViewById(2131361910);
      Button var7 = (Button)this.findViewById(2131361911);
      Button var8 = (Button)this.findViewById(2131361905);
      Button var9 = (Button)this.findViewById(2131361913);
      Button var10 = (Button)this.findViewById(2131361912);
      Button var11 = (Button)this.findViewById(2131361906);
      if(var1 == 1) {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("mobile_topup_th"));
         var5.setBackgroundDrawable(CallImage.imageDrawableCard("game_th"));
         var6.setBackgroundDrawable(CallImage.imageDrawableCard("card_th"));
         var7.setBackgroundDrawable(CallImage.imageDrawableCard("billing_th"));
         var10.setBackgroundDrawable(CallImage.imageDrawableCard("billing_other_th"));
         var8.setBackgroundDrawable(CallImage.imageDrawableCard("how_to_use_th"));
         var9.setBackgroundDrawable(CallImage.imageDrawableCard("barcode_th"));
         var11.setBackgroundDrawable(CallImage.imageDrawableCard("report_th"));
      } else if(var1 == 2) {
         var4.setBackgroundDrawable(CallImage.imageDrawableCard("mobile_topup_en"));
         var5.setBackgroundDrawable(CallImage.imageDrawableCard("game_en"));
         var6.setBackgroundDrawable(CallImage.imageDrawableCard("card_en"));
         var7.setBackgroundDrawable(CallImage.imageDrawableCard("billing_en"));
         var10.setBackgroundDrawable(CallImage.imageDrawableCard("billing_other_en"));
         var8.setBackgroundDrawable(CallImage.imageDrawableCard("how_to_use_en"));
         var9.setBackgroundDrawable(CallImage.imageDrawableCard("barcode_en"));
         var11.setBackgroundDrawable(CallImage.imageDrawableCard("report_en"));
      }

      TextView var12 = (TextView)this.findViewById(2131361796);
      TextView var13 = (TextView)this.findViewById(2131361798);
      TextView var14 = (TextView)this.findViewById(2131361858);
      TextView var15 = (TextView)this.findViewById(2131361811);
      TextView var16 = (TextView)this.findViewById(2131361915);
      TextView var17 = (TextView)this.findViewById(2131361916);
      if(var1 == 1) {
         if(var12 != null) {
            var12.setText("เติมเงินมือถือ");
         }

         if(var13 != null) {
            var13.setText("ซื้อบัตรเงินสดเกมส์");
         }

         if(var14 != null) {
            var14.setText("บัตรโทรศัพท์ต่างประเทศ");
         }

         if(var15 != null) {
            var15.setText("ชำระค่าน้ำ ไฟ โทรศัพย์");
         }

         if(var16 != null) {
            var16.setText("ชำระค่าบริการอื่นๆ");
         }

         if(var17 != null) {
            var17.setText("สแกนบาร์โค้ด");
         }
      } else {
         if(var12 != null) {
            var12.setText("prepaid mobile");
         }

         if(var13 != null) {
            var13.setText("prepaid game");
         }

         if(var14 != null) {
            var14.setText("prepaid telephone");
         }

         if(var15 != null) {
            var15.setText("pay for utility");
         }

         if(var16 != null) {
            var16.setText("other utility");
         }

         if(var17 != null) {
            var17.setText("scan barcode");
            return;
         }
      }

   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903056);
      super.onCreate(var1);
      this.getWindow().addFlags(128);
      this.preferences = this.getPreferences(0);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard("bg_blue"));
      AudioDemo.Sound().preferences = this.preferences;
      SharedPreferences var2 = this.getSharedPreferences("hello", 0);
      if(var2.getString("HARDWARE_ID", "").length() == 0) {
         Editor var39 = this.getSharedPreferences("hello", 0).edit();
         var39.putString("HARDWARE_ID", "KTPN00001");
         var39.putString("KEYTABLE", "B1FE5D76493C28A0");
         var39.commit();
      }

      try {
         String var33 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
         if(var2.getString("DownloadVersion", "").equalsIgnoreCase(var33)) {
            Editor var34 = var2.edit();
            var34.putString("DownloadVersion", (String)null);
            var34.putBoolean("DownloadFinish", false);
            Log.w("hello", "clear value");
            var34.commit();
         }
      } catch (NameNotFoundException var41) {
         var41.printStackTrace();
      }

      Service.globalPref = var2;
      Log.w("hello", "pass here " + Service.globalPref);
      View var5 = this.findViewById(2131361907);
      OnClickListener var6 = new OnClickListener() {
         public void onClick(View var1) {
            MainPage.this.setting.writeConfirmOrder((Bundle)null);
         }
      };
      var5.setOnClickListener(var6);
      Intent var7 = new Intent(this, ConnectService.class);
      this.startService(var7);
      Log.v("hello", "thread start");
      Button var10 = (Button)this.findViewById(2131361908);
      OnTouchListener var11 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 == 0) {
               MainPage.this.ImageButton(1, 2);
            } else if(var3 == 1) {
               MainPage.this.ImageButton(1, 1);
               Bundle var4 = new Bundle();
               var4.putString("param1", "click01");
               Intent var5 = new Intent(var1.getContext(), SelectService.class);
               var5.putExtras(var4);
               MainPage.this.startActivityForResult(var5, 0);
               return false;
            }

            return false;
         }
      };
      var10.setOnTouchListener(var11);
      Button var12 = (Button)this.findViewById(2131361909);
      OnTouchListener var13 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 == 0) {
               MainPage.this.ImageButton(2, 2);
            } else if(var3 == 1) {
               MainPage.this.ImageButton(2, 1);
               Bundle var4 = new Bundle();
               var4.putString("param1", "click02");
               Intent var5 = new Intent(var1.getContext(), SelectService.class);
               var5.putExtras(var4);
               MainPage.this.startActivityForResult(var5, 0);
               return false;
            }

            return false;
         }
      };
      var12.setOnTouchListener(var13);
      Button var14 = (Button)this.findViewById(2131361910);
      OnTouchListener var15 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 == 0) {
               MainPage.this.ImageButton(3, 2);
            } else if(var3 == 1) {
               MainPage.this.ImageButton(3, 1);
               Bundle var4 = new Bundle();
               var4.putString("param1", "click03");
               Intent var5 = new Intent(var1.getContext(), SelectService.class);
               var5.putExtras(var4);
               MainPage.this.startActivityForResult(var5, 0);
               return false;
            }

            return false;
         }
      };
      var14.setOnTouchListener(var15);
      Button var16 = (Button)this.findViewById(2131361911);
      OnTouchListener var17 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 == 0) {
               MainPage.this.ImageButton(4, 2);
            } else if(var3 == 1) {
               MainPage.this.ImageButton(4, 1);
               Intent var4 = new Intent(var1.getContext(), SelectUtility.class);
               var1.getContext().startActivity(var4);
            }

            return false;
         }
      };
      var16.setOnTouchListener(var17);
      Button var18 = (Button)this.findViewById(2131361905);
      OnTouchListener var19 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            return false;
         }
      };
      var18.setOnTouchListener(var19);
      Button var20 = (Button)this.findViewById(2131361912);
      OnTouchListener var21 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 != 0 && var3 == 1) {
               Intent var4 = new Intent(var1.getContext(), SelectOtherUtility.class);
               var1.getContext().startActivity(var4);
            }

            return false;
         }
      };
      var20.setOnTouchListener(var21);
      Button var22 = (Button)this.findViewById(2131361913);
      OnTouchListener var23 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 != 0 && var3 == 1) {
               Intent var4 = new Intent(var1.getContext(), Barcode.class);
               var1.getContext().startActivity(var4);
            }

            return false;
         }
      };
      var22.setOnTouchListener(var23);
      Button var24 = (Button)this.findViewById(2131361906);
      OnTouchListener var25 = new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            int var3 = var2.getAction();
            if(var3 == 0) {
               MainPage.this.ImageButton(11, 2);
            } else if(var3 == 1) {
               MainPage.this.ImageButton(11, 1);
               Intent var4 = new Intent(var1.getContext(), ReportPage.class);
               var1.getContext().startActivity(var4);
            }

            return false;
         }
      };
      var24.setOnTouchListener(var25);
      Button var26 = this.cancel;
      OnClickListener var27 = new OnClickListener() {
         public void onClick(View var1) {
            MainPage.this.cancel.setOnClickListener((OnClickListener)null);
            Intent var2 = new Intent(var1.getContext(), SampleActivity.class);
            MainPage.this.startActivityForResult(var2, 0);
         }
      };
      var26.setOnClickListener(var27);
      this.startActivityForResult(new Intent(var20.getContext(), SampleActivity.class), 0);
      this.back.setVisibility(8);
      this.next.setVisibility(8);

      try {
         this.changeLanguage(switchID);
      } catch (NullPointerException var40) {
         ;
      }

      Log.v("hello", "create main");
      this.passCreate = true;
      PhoneCallListener var30 = new PhoneCallListener((PhoneCallListener)null);
      ((TelephonyManager)this.getSystemService("phone")).listen(var30, 32);
      IntentFilter var31 = new IntentFilter("com.example.notificationservice.CATCH_NOTIFICATION");
      var31.addAction("com.example.notificationservice.CATCH_TOAST");
      this.registerReceiver(this.ussdCatcherReceiver, var31);
   }

   protected void onDestroy() {
      super.onDestroy();
      this.unregisterReceiver(this.ussdCatcherReceiver);
   }

   public void onPause() {
      Log.v("hello", "unbind");
      this.unbindService(this.service);
      pause = true;
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.cancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            MainPage.this.cancel.setOnClickListener((OnClickListener)null);
            Intent var2 = new Intent(var1.getContext(), SampleActivity.class);
            MainPage.this.startActivityForResult(var2, 0);
         }
      });
      sharepreferences = this.getSharedPreferences("menu.header", 0);
      this.changeLanguage(sharepreferences.getInt("lang_id", 0));
      if(!this.passCreate) {
         AudioDemo.Sound().playSound("a0");
      }

      this.passCreate = false;
      if(!this.getSharedPreferences("hello", 0).getBoolean("isICT", false)) {
         isBV20 = true;
         Log.v("hello", "isbv20 " + IOServiceBV20.class);
         Intent var11 = new Intent(this, IOServiceBV20.class);
         this.startService(var11);
         this.bindService(var11, this.service, 1);
      } else {
         Log.v("hello", "isnotbv20");
         Intent var2 = new Intent(this, IOService.class);
         this.startService(var2);
         this.bindService(var2, this.service, 1);
      }

      SharedPreferences var5 = this.getSharedPreferences("hello", 0);
      Log.v("hello", "box open " + var5.getBoolean("boxopen", true));
      if(var5.getBoolean("boxopen", true)) {
         if(isBV20) {
            IOServiceBV20.lockbox = false;
         } else {
            IOService.lockbox = false;
         }

         Log.v("hello", "can box open");
      } else {
         if(isBV20) {
            IOServiceBV20.lockbox = true;
         } else {
            IOService.lockbox = true;
         }

         Log.v("hello", "can\'t box open");
      }

      this.startService(new Intent(this, LogChecker.class));
   }

   private class PhoneCallListener extends PhoneStateListener {
      String TAG;
      private boolean phoneCalling;

      private PhoneCallListener() {
         this.TAG = "LOGGING PHONE CALL";
         this.phoneCalling = false;
      }

      // $FF: synthetic method
      PhoneCallListener(PhoneCallListener var2) {
         this();
      }

      public void onCallStateChanged(int var1, String var2) {
         if(1 == var1) {
            Log.i(this.TAG, "RINGING, number: " + var2);
         }

         if(2 == var1) {
            Log.i(this.TAG, "OFFHOOK");
            this.phoneCalling = true;
         }

         if(var1 == 0) {
            Log.i(this.TAG, "IDLE");
            if(this.phoneCalling) {
               Log.i(this.TAG, "restart app");
               Intent var5 = MainPage.this.getBaseContext().getPackageManager().getLaunchIntentForPackage(MainPage.this.getBaseContext().getPackageName());
               var5.addFlags(67108864);
               MainPage.this.startActivity(var5);
               this.phoneCalling = false;
            }
         }

      }
   }
}
