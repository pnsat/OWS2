package com.daydr3am.lib;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class LogController {
   File filePosition;
   boolean isTest;
   JSONObject json;

   public LogController(Bundle var1, boolean var2) {
      this.isTest = var2;
      File var3 = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/");
      if(!var3.exists()) {
         var3.mkdirs();
      }

      try {
         this.json = new JSONObject();
         Log.v("hello", "input money " + var1.getInt("OR") + " " + var1.getInt("MC"));
         this.json.put("OR", String.valueOf(var1.getInt("OR")));
         this.json.put("MC", String.valueOf(var1.getInt("MC")));
         this.json.put("Mobile", var1.getString("Mobile"));
         this.json.put("Network", var1.getString("Network"));
         this.json.put("Price", var1.getString("Price"));
         this.json.put("RandomKey", var1.getString("RandomKey"));
      } catch (JSONException var5) {
         var5.printStackTrace();
      }
   }

   public static int checkFileList() {
      File var0 = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/");
      if(!var0.exists()) {
         var0.mkdir();
      }

      Log.v("hello", "length " + var0.list().length);
      return var0.list().length;
   }

   public static void deleteLog() {
      File var0 = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/");
      if(var0.isDirectory()) {
         String[] var1 = var0.list();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            (new File(var0, var1[var2])).delete();
         }
      }

   }

   public static void deletefile(String var0) {
      String var1 = Environment.getExternalStorageDirectory() + "/resource/Logs/" + var0 + ".txt";
      Log.v("hello", "delete" + var1);
      File var3 = new File(var1);
      if(var3.exists()) {
         var3.delete();
      }

   }

   public static Bundle getBundle(String var0) {
      try {
         char[] var4 = new char[1024];
         String var5 = Environment.getExternalStorageDirectory() + "/resource/Logs/" + var0;
         Log.v("hello", "log shoot " + var5);
         Log.v("hello", "pathFile " + var5);
         FileReader var8 = new FileReader(new File(var5));
         var8.read(var4);
         var8.close();
         Log.v("hello", "buffer " + new String(var4));
         JSONObject var11 = new JSONObject(new String(var4));
         Bundle var12 = new Bundle();
         var12.putString("OTP", var11.optString("OTP"));
         var12.putBoolean("isUtility", var11.optBoolean("isUtility"));
         if(var11.optString("DataBarcode", (String)null) != null) {
            var12.putBoolean("isBarcode", true);
            var12.putString("TY", var11.optString("TY"));
            var12.putString("TRAN", var11.optString("TRAN"));
         }

         var12.putString("Data1", var11.optString("Data1"));
         var12.putString("Data2", var11.optString("Data2"));
         var12.putString("Data3", var11.optString("Data3"));
         var12.putString("Data4", var11.optString("Data4"));
         var12.putString("dateUser", var11.optString("dateUser"));
         var12.putInt("OR", Integer.parseInt(var11.getString("OR")));
         var12.putInt("MC", Integer.parseInt(var11.getString("MC")));
         var12.putString("Mobile", var11.getString("Mobile"));
         var12.putString("Network", var11.getString("Network"));
         var12.putString("Price", var11.getString("Price"));
         var12.putString("RandomKey", var11.getString("RandomKey"));
         var12.putString("TotalPrice", var11.getString("TotalPrice"));
         var12.putString("TotalCoin1", var11.getString("TotalCoin1"));
         var12.putString("TotalCoin2", var11.getString("TotalCoin2"));
         var12.putString("TotalCoin3", var11.getString("TotalCoin3"));
         var12.putString("TotalCoin4", var11.getString("TotalCoin4"));
         var12.putString("TotalBank1", var11.getString("TotalBank1"));
         var12.putString("TotalBank2", var11.getString("TotalBank2"));
         var12.putString("OperationRate", var11.getString("OperationRate"));
         Log.v("hello", "total coin1 " + var12.getString("TotalCoin1"));
         return var12;
      } catch (FileNotFoundException var13) {
         var13.printStackTrace();
      } catch (IOException var14) {
         var14.printStackTrace();
      } catch (JSONException var15) {
         var15.printStackTrace();
      }

      return null;
   }

   public void writerLog(Bundle var1) {
      if(!this.isTest) {
         Boolean var2 = Boolean.valueOf(false);
         String var3 = var1.getString("Mobile");
         this.filePosition = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/" + var3 + ".txt");
         if(!this.filePosition.exists()) {
            label42: {
               Boolean var22;
               try {
                  this.filePosition.createNewFile();
                  var22 = Boolean.valueOf(true);
               } catch (IOException var26) {
                  var26.printStackTrace();
                  break label42;
               }

               var2 = var22;
            }
         }

         try {
            this.json.put("TotalPrice", var1.getString("TotalPrice"));
            this.json.put("TotalCoin1", var1.getString("TotalCoin1"));
            this.json.put("TotalCoin2", var1.getString("TotalCoin2"));
            this.json.put("TotalCoin3", var1.getString("TotalCoin3"));
            this.json.put("TotalCoin4", var1.getString("TotalCoin4"));
            this.json.put("TotalBank1", var1.getString("TotalBank1"));
            this.json.put("TotalBank2", var1.getString("TotalBank2"));
            this.json.put("OperationRate", var1.getString("OperationRate"));
            FileWriter var18 = new FileWriter(this.filePosition.getAbsolutePath());
            Log.v("hello", "write " + this.json.toString());
            var18.write(this.json.toString());
            var18.flush();
            var18.close();
         } catch (JSONException var23) {
            if(var2.booleanValue()) {
               this.filePosition.delete();
            }

            var23.printStackTrace();
         } catch (IOException var24) {
            if(var2.booleanValue()) {
               this.filePosition.delete();
            }

            var24.printStackTrace();
         } catch (Exception var25) {
            if(var2.booleanValue()) {
               this.filePosition.delete();
            }

            var25.printStackTrace();
         }
      }
   }

   public void writerUtilityLog(Bundle var1) {
      if(!this.isTest) {
         String var2 = var1.getString("Mobile");
         this.filePosition = new File(Environment.getExternalStorageDirectory() + "/resource/Logs/" + var2 + ".txt");
         if(!this.filePosition.exists()) {
            try {
               this.filePosition.createNewFile();
            } catch (IOException var28) {
               var28.printStackTrace();
            }
         }

         try {
            this.json.put("isUtility", true);
            if(var1.getString("DataBarcode", (String)null) != null) {
               this.json.put("DataBarcode", var1.getString("DataBarcode"));
            }

            if(var1.getString("TY", (String)null) != null) {
               this.json.put("TY", var1.getString("TY"));
            }

            if(var1.getString("TRAN", (String)null) != null) {
               this.json.put("TRAN", var1.getString("TRAN"));
            }

            this.json.put("OTP", var1.getString("OTP"));
            this.json.put("Data1", var1.getString("Data1"));
            this.json.put("Data2", var1.getString("Data2"));
            this.json.put("Data3", var1.getString("Data3"));
            this.json.put("Data4", var1.getString("Data4"));
            this.json.put("dateUser", var1.getString("dateUser"));
            this.json.put("TotalPrice", var1.getString("TotalPrice"));
            this.json.put("TotalCoin1", var1.getString("TotalCoin1"));
            this.json.put("TotalCoin2", var1.getString("TotalCoin2"));
            this.json.put("TotalCoin3", var1.getString("TotalCoin3"));
            this.json.put("TotalCoin4", var1.getString("TotalCoin4"));
            this.json.put("TotalBank1", var1.getString("TotalBank1"));
            this.json.put("TotalBank2", var1.getString("TotalBank2"));
            this.json.put("OperationRate", var1.getString("OperationRate"));
            FileWriter var20 = new FileWriter(this.filePosition.getAbsolutePath());
            Log.v("hello", "write " + this.json.toString());
            var20.write(this.json.toString());
            var20.flush();
            var20.close();
         } catch (JSONException var26) {
            var26.printStackTrace();
         } catch (IOException var27) {
            var27.printStackTrace();
         }
      }
   }
}
