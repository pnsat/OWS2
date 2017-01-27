package com.daydr3am.OWS;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class InputCoinUtil extends IORootActivity implements IOService.ReceiveMoney {
   boolean ToLoading = false;
   boolean backButton = false;
   int bank = 0;
   boolean cancelInput;
   OnClickListener clickBack = new OnClickListener() {
      public void onClick(View var1) {
         var1.setClickable(false);
         InputCoinUtil.this.cancelInput = true;
         InputCoinUtil.this.backButton = true;
         InputCoinUtil.this.setting.enableInput(false);
         (new Thread(new Runnable() {
            public void run() {
               try {
                  if(InputCoinUtil.this.moneyInput == 0) {
                     Log.v("hello", "come if " + InputCoinUtil.this.moneyInput);
                     InputCoinUtil.this.finish();
                  } else {
                     AudioDemo.Sound().playSound("a9");
                     Thread.sleep(10000L);
                     InputCoinUtil.this.finishInputMoney(true);
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
         InputCoinUtil.this.cancelInput = true;
         InputCoinUtil.this.backButton = false;
         InputCoinUtil.this.setting.enableInput(false);
         (new Thread(new Runnable() {
            public void run() {
               try {
                  if(InputCoinUtil.this.moneyInput == 0) {
                     Log.v("hello", "come if " + InputCoinUtil.this.moneyInput);
                     Intent var3 = new Intent(InputCoinUtil.this.getBaseContext(), MainPage.class);
                     var3.setFlags(67108864);
                     InputCoinUtil.this.startActivity(var3);
                  } else {
                     AudioDemo.Sound().playSound("a9");
                     Thread.sleep(10000L);
                     InputCoinUtil.this.finishInputMoney(false);
                  }
               } catch (InterruptedException var4) {
                  var4.printStackTrace();
               }
            }
         })).start();
      }
   };
   int coin = 0;
   boolean endInput;
   LogController log;
   private int moneyInput;
   public ServiceConnection service = new ServiceConnection() {
      public void onServiceConnected(ComponentName var1, IBinder var2) {
         InputCoinUtil.this.setting = (IOService.SettingService)var2;
         InputCoinUtil.this.setting.serviceSetup(InputCoinUtil.this);
         InputCoinUtil.this.setting.setupReceiveMoney(InputCoinUtil.this);
         InputCoinUtil.this.setting.enableBox();
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
         this.moneyInput = this.coin + this.bank;
         Bundle var2 = this.getIntent().getExtras();
         var2.putString("TotalPrice", "" + this.moneyInput);
         var2.putString("TotalCoin1", "" + this.coin);
         var2.putString("TotalCoin2", "0");
         var2.putString("TotalCoin3", "0");
         var2.putString("TotalCoin4", "0");
         var2.putString("TotalBank1", "" + this.bank);
         var2.putString("TotalBank2", "0");
         var2.putString("OperationRate", String.valueOf(var2.getInt("OR")));
         Log.v("hello", "finish " + this.endInput);
         if(this.endInput) {
            if(var2.getString("DataBarcode", "").length() > 0) {
               var2.putInt("Service", 20);
            } else {
               var2.putInt("Service", 12);
            }

            AudioDemo.Sound().playSound("a6");
         } else {
            var2.putInt("Service", 13);
            if(var1) {
               this.finish();
            } else {
               Intent var4 = new Intent(this.getBaseContext(), MainPage.class);
               var4.setFlags(67108864);
               this.startActivity(var4);
            }
         }

         if(!this.getSharedPreferences("hello", 0).getBoolean("inDebugMode", false)) {
            Intent var6 = new Intent(this.getBaseContext(), Loading.class);
            var6.putExtras(var2);
            this.startActivity(var6);
         } else {
            Intent var8 = new Intent(this.getBaseContext(), MainPage.class);
            var8.setFlags(67108864);
            this.startActivity(var8);
         }
      }
   }

   private void processMoney() throws Exception {
      Bundle var1 = this.getIntent().getExtras();
      float var2 = Float.parseFloat(var1.getString("Price"));
      int var3 = var1.getInt("OR");
      int var4 = var1.getInt("MC");
      this.moneyInput = this.coin + this.bank;
      Object[] var5 = new Object[]{Float.valueOf(var2), Float.valueOf(var2)};
      Log.v("test", String.format("money %.02f %f", var5));
      float var7 = var2 + (float)var3 - (float)var4;
      this.text_price1.setText(String.valueOf(this.moneyInput));
      TextView var8 = this.text_price2;
      Object[] var9 = new Object[]{Float.valueOf(var7 - (float)this.moneyInput)};
      var8.setText(String.format("%.02f", var9));
      if(var7 - (float)this.moneyInput < 0.0F) {
         this.text_price2.setText("0.00");
      }

      StringBuilder var10 = (new StringBuilder("check ")).append(this.moneyInput).append(" ").append(var7).append(" ");
      boolean var11;
      if((float)this.moneyInput >= var7) {
         var11 = true;
      } else {
         var11 = false;
      }

      Log.v("hello", var10.append(var11).toString());
      if((float)this.moneyInput >= var7) {
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
      TextView var2 = (TextView)this.findViewById(2131361889);
      TextView var3 = (TextView)this.findViewById(2131361890);
      TextView var4 = (TextView)this.findViewById(2131361893);
      TextView var5 = (TextView)this.findViewById(2131361896);
      TextView var6 = (TextView)this.findViewById(2131361892);
      TextView var7 = (TextView)this.findViewById(2131361895);
      TextView var8 = (TextView)this.findViewById(2131361898);
      var2.setText(MessageTopup.getMessage(19));
      var2.setTypeface(MessageTopup.setFont(this, 0));
      var3.setText(MessageTopup.getMessage(15));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(20));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(21));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(16));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(16));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(16));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      this.changeLanguageCheck(var1);
   }

   public void changeLanguageCheck(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361803);
      TextView var4 = (TextView)this.findViewById(2131361805);
      TextView var5 = (TextView)this.findViewById(2131361807);
      TextView var6 = (TextView)this.findViewById(2131361804);
      TextView var7 = (TextView)this.findViewById(2131361806);
      TextView var8 = (TextView)this.findViewById(2131361809);
      var3.setText(MessageTopup.getMessage(12));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(13));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(14));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(16));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(16));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(16));
      var8.setTypeface(MessageTopup.setFont(this, 0));
      String var9 = "";
      if(var1 == 1) {
         var9 = var9 + "_th";
      } else if(var1 == 2) {
         var9 = var9 + "_en";
      }

      if(this.next != null) {
         this.next.setBackgroundDrawable(CallImage.imageDrawableCard("bt_pay" + var9));
      }

   }

   public void createCheck() {
      Bundle var1 = this.getIntent().getExtras();
      EditText var2 = (EditText)this.findViewById(2131361802);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var1.getString("img_background")));
      var2.setText(var1.getString("Mobile"));
      var2.setEnabled(false);
      String var3 = var1.getString("Price");
      Float.parseFloat(var3);
      int var5 = var1.getInt("OR");
      int var6 = var1.getInt("MC");
      TextView var7 = (TextView)this.findViewById(2131361796);
      Object[] var8 = new Object[]{Float.valueOf(Float.parseFloat(var3))};
      var7.setText(String.format("%7.2f", var8));
      ((TextView)this.findViewById(2131361798)).setText(String.valueOf(var5));
      ((TextView)this.findViewById(2131361808)).setText(String.valueOf(var6));
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

         Log.v("hello", "on create " + this.moneyInput);
         this.findViewById(2131361863).setVisibility(8);
         this.text_price0 = (TextView)this.findViewById(2131361891);
         this.text_price1 = (TextView)this.findViewById(2131361894);
         this.text_price2 = (TextView)this.findViewById(2131361897);
         EditText var4 = (EditText)this.findViewById(2131361802);
         Bundle var5 = this.getIntent().getExtras();
         this.log = new LogController(var5, this.getSharedPreferences("hello", 0).getBoolean("inDebugMode", false));
         ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard(var5.getString("img_background")));
         var4.setText(var5.getString("Mobile"));
         var4.setCursorVisible(false);
         float var6 = Float.parseFloat(var5.getString("Price"));
         int var7 = var5.getInt("OR");
         int var8 = var5.getInt("MC");
         float var9;
         if(var6 - (float)var8 > 0.0F) {
            var9 = var6 + (float)var7 - (float)var8;
         } else {
            var9 = 0.0F;
         }

         TextView var10 = this.text_price0;
         Object[] var11 = new Object[]{Float.valueOf(var9)};
         var10.setText(String.format("%7.2f", var11));
         this.text_price1.setText("0");
         TextView var12 = this.text_price2;
         Object[] var13 = new Object[]{Float.valueOf(var9)};
         var12.setText(String.format("%7.2f", var13));
         (new Handler()).postDelayed(new Runnable() {
            public void run() {
               try {
                  Log.v("hello", "process money create");
                  InputCoinUtil.this.processMoney();
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

   public void onPause() {
      super.onPause();
      this.moneyInput = 0;
      this.coin = 0;
      this.bank = 0;
      if(this.setting != null) {
         this.setting.disableBox();
         this.unbindService(this.service);
      }

   }

   protected void onResume() {
      super.onResume();
      if(LogController.checkFileList() != 0) {
         Intent var1 = new Intent(this.getBaseContext(), MainPage.class);
         var1.setFlags(67108864);
         this.startActivity(var1);
      } else {
         ImageView var3 = (ImageView)this.findViewById(2131361800);
         Bundle var4 = this.getIntent().getExtras();
         Resources var5 = this.getResources();
         String var6 = "logo_service" + var4.getString("Network");

         try {
            var3.setImageDrawable(var5.getDrawable(var5.getIdentifier(var6, "drawable", this.getPackageName())));
         } catch (NotFoundException var9) {
            var3.setVisibility(8);
            TextView var8 = (TextView)this.findViewById(2131361888);
            var8.setText(var4.getString("NetworkName", ""));
            var8.setVisibility(0);
         }

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
      SharedPreferences var2 = this.getSharedPreferences("hello", 0);
      Editor var3 = var2.edit();
      if(!var2.getBoolean("inDebugMode", false)) {
         var3.putInt("MoneyLog", var1 + var2.getInt("MoneyLog", 0));
      }

      var3.commit();

      try {
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
      this.log.writerUtilityLog(var2);
   }
}
