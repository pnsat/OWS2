package com.daydr3am.OWS;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;
import com.daydr3am.OWS.MainPage;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.OWS.SampleActivity;
import com.daydr3am.lib.AudioDemo;
import com.daydr3am.lib.CallImage;
import com.daydr3am.lib.IOService;
import com.daydr3am.lib.IOServiceBV20;
import com.daydr3am.lib.LogController;

public class InputCoin extends IORootActivity implements IOService.ReceiveMoney {
   boolean ToLoading = false;
   boolean backButton = false;
   int bank = 0;
   boolean cancelInput;
   OnClickListener clickBack = new OnClickListener() {
      public void onClick(View var1) {
         var1.setClickable(false);
         InputCoin.this.cancelInput = true;
         InputCoin.this.backButton = true;
         InputCoin.this.setting.enableInput(false);
         (new Thread(new Runnable() {
            public void run() {
               try {
                  Log.v("hello", "chk cancel case " + InputCoin.this.moneyInput);
                  if(InputCoin.this.moneyInput == 0) {
                     Log.v("hello", "come if " + InputCoin.this.moneyInput);
                     InputCoin.this.finish();
                  } else {
                     AudioDemo.Sound().playSound("a9");
                     Thread.sleep(10000L);
                     InputCoin.this.finishInputMoney(true);
                  }
               } catch (InterruptedException var2) {
                  var2.printStackTrace();
               }
            }
         })).start();
      }
   };
   OnClickListener clickCancel = new OnClickListener() {
      public void onClick(View var1) {
         var1.setClickable(false);
         InputCoin.this.cancelInput = true;
         InputCoin.this.backButton = false;
         InputCoin.this.setting.enableInput(false);
         (new Thread(new Runnable() {
            public void run() {
               try {
                  if(InputCoin.this.moneyInput == 0) {
                     Log.v("hello", "come if " + InputCoin.this.moneyInput);
                     Intent var3 = new Intent(InputCoin.this.getBaseContext(), MainPage.class);
                     var3.setFlags(67108864);
                     InputCoin.this.startActivity(var3);
                  } else {
                     AudioDemo.Sound().playSound("a9");
                     Thread.sleep(10000L);
                     InputCoin.this.finishInputMoney(false);
                  }
               } catch (InterruptedException var4) {
                  var4.printStackTrace();
               }
            }
         })).start();
      }
   };
   int coin = 0;
   Boolean connectService = Boolean.valueOf(false);
   boolean endInput;
   LogController log;
   private int moneyInput;
   public ServiceConnection service = new ServiceConnection() {
      public void onServiceConnected(ComponentName var1, IBinder var2) {
         InputCoin.this.setting = (IOService.SettingService)var2;
         InputCoin.this.setting.serviceSetup(InputCoin.this);
         InputCoin.this.setting.setupReceiveMoney(InputCoin.this);
         InputCoin.this.setting.enableBox();
      }

      public void onServiceDisconnected(ComponentName var1) {
         Log.v("hello", "serviced");
      }
   };
   IOService.SettingService setting;
   TextView text_price0;
   TextView text_price1;
   TextView text_price2;

   private void finishInputMoney(boolean var1) {
      if(!this.ToLoading) {
         this.ToLoading = true;
         Log.w("hello", "finish");
         this.moneyInput = this.coin + this.bank;
         Bundle var3 = this.getIntent().getExtras();
         var3.putString("TotalPrice", "" + this.moneyInput);
         var3.putString("TotalCoin1", "" + this.coin);
         var3.putString("TotalCoin2", "0");
         var3.putString("TotalCoin3", "0");
         var3.putString("TotalCoin4", "0");
         var3.putString("TotalBank1", "" + this.bank);
         var3.putString("TotalBank2", "0");
         var3.putString("OperationRate", String.valueOf(var3.getInt("OR")));
         if(this.endInput) {
            var3.putInt("Service", 2);
            AudioDemo.Sound().playSound("a6");
         } else {
            Log.v("hello", "come finish else");
            var3.putInt("Service", 4);
            if(var1) {
               this.finish();
            } else {
               Intent var5 = new Intent(this.getBaseContext(), MainPage.class);
               var5.setFlags(67108864);
               this.startActivity(var5);
            }
         }

         if(!this.getSharedPreferences("hello", 0).getBoolean("inDebugMode", false)) {
            Log.w("hello", "to loading");
            Intent var10 = new Intent(this.getBaseContext(), Loading.class);
            var10.putExtras(var3);
            this.startActivity(var10);
         } else {
            Intent var7 = new Intent(this.getBaseContext(), MainPage.class);
            var7.setFlags(67108864);
            this.startActivity(var7);
         }
      }
   }

   private void processMoney() throws Exception {
      Bundle var1 = this.getIntent().getExtras();
      int var2 = Integer.parseInt(var1.getString("param3"));
      int var3 = var1.getInt("OR");
      int var4 = var1.getInt("MC");
      this.moneyInput = this.coin + this.bank;
      Log.v("hello", "process money before " + var2 + " " + var3 + " " + var4 + " " + this.moneyInput);
      int var6 = var2 + var3 - var4;
      this.text_price1.setText(String.valueOf(this.moneyInput));
      this.text_price2.setText(String.valueOf(var6 - this.moneyInput));
      if(var6 - this.moneyInput < 0) {
         this.text_price2.setText("0");
      }

      if(this.moneyInput >= var6) {
         Log.v("hello", "process money pass " + this.moneyInput);
         this.endInput = true;
         this.setting.enableInput(false);
         this.finishInputMoney(true);
      }

   }

   public void backToMainMenu() {
      try {
         Thread.sleep(480000L);
      } catch (InterruptedException var4) {
         var4.printStackTrace();
      }

      if(this.activityActive && !this.getClass().equals(SampleActivity.class)) {
         AudioDemo.Sound().playSound("a9");

         try {
            Thread.sleep(10000L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }

         this.finishInputMoney(false);
      }

   }

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361889);
      TextView var4 = (TextView)this.findViewById(2131361890);
      TextView var5 = (TextView)this.findViewById(2131361893);
      TextView var6 = (TextView)this.findViewById(2131361896);
      TextView var7 = (TextView)this.findViewById(2131361892);
      TextView var8 = (TextView)this.findViewById(2131361895);
      TextView var9 = (TextView)this.findViewById(2131361898);
      var3.setText(MessageTopup.getMessage(19));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(15));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(20));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(21));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(16));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(16));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      var9.setText(MessageTopup.getMessage(16));
      var9.setTypeface(MessageTopup.setFont(this, 0));
      this.changeLanguageCheck(var1);
      if(this.landTextShow != null) {
         this.landTextShow.setText(MessageTopup.getMessage(19));
      }

   }

   public void changeLanguageCheck(int var1) {
      TextView var2 = (TextView)this.findViewById(2131361803);
      TextView var3 = (TextView)this.findViewById(2131361805);
      TextView var4 = (TextView)this.findViewById(2131361807);
      TextView var5 = (TextView)this.findViewById(2131361804);
      TextView var6 = (TextView)this.findViewById(2131361806);
      TextView var7 = (TextView)this.findViewById(2131361809);
      var2.setText(MessageTopup.getMessage(12));
      var2.setTypeface(MessageTopup.setFont(this, 0));
      var3.setText(MessageTopup.getMessage(13));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(14));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(16));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(16));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(16));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      String var8 = "";
      if(var1 == 1) {
         var8 = var8 + "_th";
      } else if(var1 == 2) {
         var8 = var8 + "_en";
      }

      if(this.next != null) {
         this.next.setBackgroundDrawable(CallImage.imageDrawableCard("bt_pay" + var8));
      }

   }

   public void createCheck() {
      Bundle var1 = this.getIntent().getExtras();
      EditText var2 = (EditText)this.findViewById(2131361802);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var1.getString("img_background")));
      var2.setText(var1.getString("param4"));
      var2.setEnabled(false);
      int var3 = Integer.parseInt(var1.getString("param3"));
      int var4 = var1.getInt("OR");
      int var5 = var1.getInt("MC");
      ((TextView)this.findViewById(2131361796)).setText(String.valueOf(var3));
      ((TextView)this.findViewById(2131361798)).setText(String.valueOf(var4));
      ((TextView)this.findViewById(2131361808)).setText(String.valueOf(var5));
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903052);
      super.onCreate(var1);
      if(LogController.checkFileList() == 0) {
         if(MainPage.isBV20) {
            this.bindService(new Intent(this, IOServiceBV20.class), this.service, 1);
         } else {
            this.bindService(new Intent(this, IOService.class), this.service, 1);
         }

         this.connectService = Boolean.valueOf(true);
         Log.v("hello", "on create " + this.moneyInput);
         this.findViewById(2131361863).setVisibility(8);
         this.text_price0 = (TextView)this.findViewById(2131361891);
         this.text_price1 = (TextView)this.findViewById(2131361894);
         this.text_price2 = (TextView)this.findViewById(2131361897);
         EditText var4 = (EditText)this.findViewById(2131361802);
         Bundle var5 = this.getIntent().getExtras();
         this.log = new LogController(var5, this.getSharedPreferences("hello", 0).getBoolean("inDebugMode", false));
         ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var5.getString("img_background")));
         var4.setText(var5.getString("param4"));
         var4.setCursorVisible(false);
         int var6 = Integer.parseInt(var5.getString("param3"));
         int var7 = var5.getInt("OR");
         int var8 = var5.getInt("MC");
         int var9;
         if(var6 + var7 - var8 > 0) {
            var9 = var6 + var7 - var8;
         } else {
            var9 = 0;
         }

         this.text_price0.setText(String.valueOf(var9));
         this.text_price1.setText("0");
         this.text_price2.setText(String.valueOf(var9));
         Button var10 = (Button)this.findViewById(2131361899);
         if(var10 != null) {
            var10.setOnClickListener(new OnClickListener() {
               public void onClick(View var1) {
                  InputCoin.this.receiveCoin(5);
               }
            });
         }

         (new Handler()).postDelayed(new Runnable() {
            public void run() {
               try {
                  Log.v("hello", "process money create");
                  InputCoin.this.processMoney();
               } catch (Exception var2) {
                  var2.printStackTrace();
               }
            }
         }, 3000L);
         this.back.setOnClickListener(this.clickBack);
         this.cancel.setOnClickListener(this.clickCancel);
         AudioDemo.Sound().playSound("a5");
         this.createCheck();
         this.changeLanguage(switchID);
      }
   }

   public void onDestroy() {
      super.onDestroy();
      if(this.connectService.booleanValue()) {
         this.unbindService(this.service);
      }

   }

   public void onPause() {
      super.onPause();
      this.moneyInput = 0;
      this.coin = 0;
      this.bank = 0;
      if(this.setting != null) {
         this.setting.disableBox();
      }

   }

   protected void onResume() {
      super.onResume();
      if(LogController.checkFileList() != 0) {
         Intent var1 = new Intent(this.getBaseContext(), MainPage.class);
         var1.setFlags(67108864);
         this.startActivity(var1);
      } else {
         Log.v("hello", "on resume " + this.moneyInput);
         ImageView var4 = (ImageView)this.findViewById(2131361800);
         String[][] var5 = new String[][]{{"logo_one2call", "logo_happy", "logo_true", "logo_hutch", "logo_cat", "logo_true_h"}, {"logo_true", "logo_cash", "logo_dserial", "logo_cookiecard", "logo_winner", "logo_tot", "logo_play", "logo_gcash"}, {"logo_tolld", "logo_tookdee"}};
         Bundle var6 = this.getIntent().getExtras();
         String var7 = var6.getString("param1");
         String var8 = var6.getString("param2");
         int var9 = -1 + Integer.parseInt(String.valueOf(var7.charAt(6)));
         int var10 = Integer.parseInt(var8);
         Log.v("test", "index " + var9 + " " + var10);
         Log.v("test", "picture " + var5[var9][var10]);
         Log.d("debug", "logo " + this.findViewById(2131361820));
         var4.setImageDrawable(CallImage.imageDrawableCard(var5[var9][var10]));
         if(this.landCancel != null) {
            this.landCancel.setOnClickListener(this.clickCancel);
         }

         if(this.landBack != null) {
            this.landBack.setOnClickListener(this.clickBack);
            return;
         }
      }

   }

   public void receiveBank(int var1) {
      this.bank += var1;
      this.writeLog();

      try {
         Log.v("hello", "process money receive bank" + var1);
         SharedPreferences var4 = this.getSharedPreferences("hello", 0);
         Editor var5 = var4.edit();
         if(!var4.getBoolean("inDebugMode", false)) {
            var5.putInt("MoneyLog", var1 + var4.getInt("MoneyLog", 0));
         }

         var5.commit();
         this.processMoney();
      } catch (Exception var6) {
         ;
      }
   }

   public void receiveCoin(int var1) {
      this.coin += var1;
      this.writeLog();
      SharedPreferences var2 = this.getSharedPreferences("hello", 0);
      Editor var3 = var2.edit();
      if(!var2.getBoolean("inDebugMode", false)) {
         var3.putInt("MoneyLog", var1 + var2.getInt("MoneyLog", 0));
      }

      var3.commit();

      try {
         Log.v("hello", "process money receive coin" + var1);
         this.processMoney();
      } catch (Exception var6) {
         ;
      }
   }

   public void receiveText(String var1) {
   }

   public void writeLog() {
      int var1 = this.coin + this.bank;
      Bundle var2 = this.getIntent().getExtras();
      var2.putString("TotalPrice", "" + var1);
      var2.putString("TotalCoin1", "" + this.coin);
      var2.putString("TotalCoin2", "0");
      var2.putString("TotalCoin3", "0");
      var2.putString("TotalCoin4", "0");
      var2.putString("TotalBank1", "" + this.bank);
      var2.putString("TotalBank2", "0");
      var2.putString("OperationRate", String.valueOf(var2.getInt("OR")));
      this.log.writerLog(var2);
   }
}
