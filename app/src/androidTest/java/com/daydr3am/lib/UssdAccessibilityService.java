package com.daydr3am.lib;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils.SimpleStringSplitter;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Iterator;

public class UssdAccessibilityService extends AccessibilityService {
   private static final String TAG = "MyAccessibilityService";
   AccessibilityNodeInfo accessibilitynodeinfo1;
   Runnable clickUpdate = new Runnable() {
      public void run() {
         AccessibilityNodeInfo var1 = UssdAccessibilityService.getButtonUpdate(UssdAccessibilityService.this.accessibilitynodeinfo1, "Button");
         if(var1 != null) {
            var1.performAction(16);
         }

      }
   };
   Runnable close = new Runnable() {
      public void run() {
         AccessibilityNodeInfo var1 = UssdAccessibilityService.getButton(UssdAccessibilityService.this.accessibilitynodeinfo1, "Button");
         if(var1 != null) {
            var1.performAction(16);
         }

      }
   };
   String data;
   private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
   Runnable sendData = new Runnable() {
      public void run() {
         Log.e("hello", "send value " + UssdAccessibilityService.this.data);
         AccessibilityNodeInfo var2 = UssdAccessibilityService.getButton(UssdAccessibilityService.this.accessibilitynodeinfo1, "Button");
         if(var2 != null) {
            var2.performAction(16);
         }

         try {
            Intent var3 = new Intent("com.example.notificationservice.CATCH_TOAST");
            var3.putExtra("extra_cutmessage", UssdAccessibilityService.this.getUSSDValue(UssdAccessibilityService.this.data));
            var3.putExtra("extra_message", UssdAccessibilityService.this.data);
            UssdAccessibilityService.this.getApplicationContext().sendBroadcast(var3);
         } catch (Exception var5) {
            Log.v("MyAccessibilityService", var5.toString());
         }
      }
   };

   private static AccessibilityNodeInfo getButton(AccessibilityNodeInfo var0, String var1) {
      int var2 = 0;

      AccessibilityNodeInfo var3;
      while(true) {
         if(var2 >= var0.getChildCount()) {
            var3 = null;
            break;
         }

         var3 = var0.getChild(var2);
         if(var3.getClassName().toString().contains(var1)) {
            break;
         }

         var3.recycle();
         ++var2;
      }

      return var3;
   }

   private static AccessibilityNodeInfo getButtonUpdate(AccessibilityNodeInfo var0, String var1) {
      return var0.getChildCount() == 5 && var0.getChild(4).getClassName().toString().contains(var1)?var0.getChild(4):null;
   }

   private String getEventText(AccessibilityEvent var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var1.getText().iterator();

      while(var3.hasNext()) {
         var2.append((CharSequence)var3.next());
      }

      return var2.toString();
   }

   private String getEventType(AccessibilityEvent var1) {
      switch(var1.getEventType()) {
      case 1:
         return "TYPE_VIEW_CLICKED";
      case 2:
         return "TYPE_VIEW_LONG_CLICKED";
      case 4:
         return "TYPE_VIEW_SELECTED";
      case 8:
         return "TYPE_VIEW_FOCUSED";
      case 16:
         return "TYPE_VIEW_TEXT_CHANGED";
      case 32:
         return "TYPE_WINDOW_STATE_CHANGED";
      case 64:
         return "TYPE_NOTIFICATION_STATE_CHANGED";
      default:
         return "default";
      }
   }

   private static String getMessage(AccessibilityNodeInfo param0) {
      // $FF: Couldn't be decompiled
   }

   private String getUSSDValue(String var1) {
      String var2 = "";

      String var12;
      label74: {
         String var13;
         label75: {
            try {
               String var4 = this.getString(2131230730);
               String var5 = this.getString(2131230731);
               String var6 = this.getString(2131230732);
               String var7 = this.getString(2131230725);
               String var8 = this.getString(2131230726);
               String var9 = this.getString(2131230727);
               String var10 = this.getString(2131230728);
               String var11 = this.getString(2131230729);
               if(var1.contains(var4)) {
                  var13 = var1.substring(0, var1.indexOf(var4));
                  break label75;
               }

               if(var1.contains(var5) || var1.contains(var6)) {
                  var2 = var1.substring(var1.indexOf(var6) + var6.length(), var1.length());
                  return var2.trim();
               }

               if(var1.contains(var7) || var1.contains(var8)) {
                  var2 = var1.substring(var7.length(), var1.indexOf(var8));
                  return var2.trim();
               }

               if(var1.contains(var9) || var1.contains(var10)) {
                  var12 = var1.substring(var1.indexOf(var9) + var9.length(), var1.indexOf(var11));
                  break label74;
               }
            } catch (Exception var14) {
               var14.printStackTrace();
               return var2.trim();
            }

            var2 = var1;
            return var2.trim();
         }

         var2 = var13;
         return var2.trim();
      }

      var2 = var12;
      return var2.trim();
   }

   public static boolean isAccessibilitySettingsOn(Context var0) {
      int var1 = 0;

      try {
         var1 = Secure.getInt(var0.getApplicationContext().getContentResolver(), "accessibility_enabled");
         Log.v("MyAccessibilityService", "accessibilityEnabled = " + var1);
      } catch (SettingNotFoundException var9) {
         Log.e("MyAccessibilityService", "Error finding setting, default accessibility to not found: " + var9.getMessage());
      }

      SimpleStringSplitter var4 = new SimpleStringSplitter(':');
      if(var1 != 1) {
         Log.v("MyAccessibilityService", "***ACCESSIBILIY IS DISABLED***");
         return false;
      } else {
         Log.v("MyAccessibilityService", "***ACCESSIBILIY IS ENABLED*** -----------------");
         String var7 = Secure.getString(var0.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
         if(var7 != null) {
            var4.setString(var7);

            while(var4.hasNext()) {
               String var8 = var4.next();
               Log.v("MyAccessibilityService", "-------------- > accessabilityService :: " + var8);
               if(var8.equalsIgnoreCase("com.example.notificationservice/com.example.notificationservice.MyAccessibilityService")) {
                  Log.v("MyAccessibilityService", "We\'ve found the correct setting - accessibility is switched on!");
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void onAccessibilityEvent(AccessibilityEvent var1) {
      int var2 = var1.getEventType();
      Log.v("hello", "message onAccessibilityEvent");
      Object[] var4 = new Object[]{this.getEventType(var1), var1.getClassName(), var1.getPackageName(), Long.valueOf(var1.getEventTime()), this.getEventText(var1), Integer.valueOf(var2)};
      Log.i("MyAccessibilityService", String.format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s [eventCode] %s", var4));
      if(var2 == 32) {
         if(var1.getClassName().toString().contains("UssdAlertActivity") && var1.getSource() != null) {
            Object[] var17 = new Object[]{this.getEventType(var1), var1.getClassName(), var1.getPackageName(), Long.valueOf(var1.getEventTime()), this.getEventText(var1), Integer.valueOf(var2)};
            Log.i("MyAccessibilityService", String.format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s [eventCode] %s", var17));
         }

         this.accessibilitynodeinfo1 = var1.getSource();
         this.data = getMessage(this.accessibilitynodeinfo1);
         Log.e("MyAccessibilityService", "data " + this.data);
         Log.e("MyAccessibilityService", "event " + this.getEventText(var1));
         if(this.data != null) {
            Log.e("hello", this.getString(2131230723) + " " + this.getEventText(var1));
            if(!this.getEventText(var1).equalsIgnoreCase("phone") && !this.getEventText(var1).equalsIgnoreCase(this.getString(2131230723))) {
               Log.e("MyAccessibilityService", "close data " + this.data);
               Log.e("MyAccessibilityService", "close event " + this.getEventText(var1));
               (new Handler()).post(this.close);
            } else {
               (new Handler()).postDelayed(this.sendData, 5000L);
            }
         }

         if(this.getEventText(var1).equalsIgnoreCase(this.getString(2131230724))) {
            (new Handler()).post(this.clickUpdate);
         }
      } else {
         Log.v("hello", "tag6");
         if(this.getEventType(var1).equals("default") && var1.getPackageName().equals("com.android.mms")) {
            Object[] var7 = new Object[]{this.getEventType(var1), var1.getClassName(), var1.getPackageName(), Long.valueOf(var1.getEventTime()), this.getEventText(var1), Integer.valueOf(var2)};
            Log.v("MyAccessibilityService", String.format("onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s [eventCode] %s", var7));
            return;
         }
      }

   }

   public void onInterrupt() {
   }

   public static final class Constants {
      public static final String ACTION_CATCH_NOTIFICATION = "com.example.notificationservice.CATCH_NOTIFICATION";
      public static final String ACTION_CATCH_TOAST = "com.example.notificationservice.CATCH_TOAST";
      public static final String EXTRA_CUTMESSAGE = "extra_cutmessage";
      public static final String EXTRA_MESSAGE = "extra_message";
      public static final String EXTRA_PACKAGE = "extra_package";
   }
}
