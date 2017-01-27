package com.daydr3am.lib;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.daydr3am.lib.Coding;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

public class Service {
   public static SharedPreferences globalPref;
   public static SharedPreferences mainPref;
   public static SharedPreferences pref;

   public static String Connection(Bundle var0, String var1, int var2) throws Exception {
      Log.w("hello", "pass inside " + globalPref);
      Object[] var4 = new Object[]{Integer.valueOf(var2)};
      String var5 = String.format("api%05d,", var4) + var1;
      String var6 = globalPref.getString("HARDWARE_ID", "");
      String var7 = globalPref.getString("KEYTABLE", "");
      Log.v("hello", "test123" + var6 + " " + var7 + " " + var5 + " " + Coding.newencoding(var5, var7));
      HttpPost var9 = new HttpPost("http://ows.dyndns.biz/owstopup/oldkiosk_api/kioskapi.php");
      ArrayList var10 = new ArrayList();
      var10.add(new BasicNameValuePair("hwid", var6));
      var10.add(new BasicNameValuePair("data", var5));
      var10.add(new BasicNameValuePair("sum", Coding.newencoding(var5, var7)));
      HttpClient var14 = createHttpClient();
      HttpConnectionParams.setConnectionTimeout(var14.getParams(), '\uea60');
      HttpConnectionParams.setSoTimeout(var14.getParams(), '\uea60');
      var9.setHeader("Content-Language", "en-US");
      var9.setHeader("Host", "ows.dyndns.biz");
      var9.setEntity(new UrlEncodedFormEntity(var10, "UTF-8"));
      Log.w("hello", "before ");
      HttpResponse var16 = var14.execute(var9);
      Log.w("hello", "callpass ");
      String var18 = EntityUtils.toString(var16.getEntity());
      Log.w("hello", "entity " + var18);
      return var18;
   }

   public static String cancelInputMoney(Bundle var0) throws Exception {
      Log.v("hello", "input " + var0.getString("TotalCoin1"));
      StringBuilder var2 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",");
      Object[] var3 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      StringBuilder var4 = var2.append(String.format("%03d", var3)).append(",");
      Object[] var5 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Price")))};
      StringBuilder var6 = var4.append(String.format("%04d", var5)).append(",");
      Object[] var7 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalPrice")))};
      StringBuilder var8 = var6.append(String.format("%04d", var7)).append(",");
      Object[] var9 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin1")))};
      StringBuilder var10 = var8.append(String.format("%04d", var9)).append(",");
      Object[] var11 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin2")))};
      StringBuilder var12 = var10.append(String.format("%04d", var11)).append(",");
      Object[] var13 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin3")))};
      StringBuilder var14 = var12.append(String.format("%04d", var13)).append(",");
      Object[] var15 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank1")))};
      StringBuilder var16 = var14.append(String.format("%04d", var15)).append(",");
      Object[] var17 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank2")))};
      StringBuilder var18 = var16.append(String.format("%04d", var17)).append(",");
      Object[] var19 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("OperationRate")))};
      StringBuilder var20 = var18.append(String.format("%02d", var19)).append(",");
      Object[] var21 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RandomKey")))};
      String var22 = var20.append(String.format("%04d", var21)).toString();
      Log.v("test", var22);
      String var24 = Connection(var0, var22, 4);
      Log.v("test", "return" + var24);
      return var24;
   }

   public static String cancelInputMoneyUtil(Bundle var0) throws Exception {
      StringBuilder var1 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",");
      Object[] var2 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      StringBuilder var3 = var1.append(String.format("%03d", var2)).append(",");
      Object[] var4 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      StringBuilder var5 = var3.append(String.format("%08.2f", var4)).append(",");
      Object[] var6 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalPrice")))};
      StringBuilder var7 = var5.append(String.format("%04d", var6)).append(",");
      Object[] var8 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin1")))};
      StringBuilder var9 = var7.append(String.format("%04d", var8)).append(",");
      Object[] var10 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin2")))};
      StringBuilder var11 = var9.append(String.format("%04d", var10)).append(",");
      Object[] var12 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin3")))};
      StringBuilder var13 = var11.append(String.format("%04d", var12)).append(",");
      Object[] var14 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank1")))};
      StringBuilder var15 = var13.append(String.format("%04d", var14)).append(",");
      Object[] var16 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank2")))};
      StringBuilder var17 = var15.append(String.format("%04d", var16)).append(",");
      Object[] var18 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("OperationRate")))};
      StringBuilder var19 = var17.append(String.format("%02d", var18)).append(",");
      Object[] var20 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RandomKey")))};
      StringBuilder var21 = var19.append(String.format("%04d", var20)).append(",");
      Object[] var22 = new Object[]{Long.valueOf(Long.parseLong(var0.getString("OTP")))};
      String var23 = var21.append(String.format("%010d", var22)).append(",").toString();

      for(int var24 = 0; var24 < 4; ++var24) {
         if(var0.getString("Data" + (var24 + 1)) != null && var0.getString("Data" + (var24 + 1)).length() != 0) {
            var23 = var23 + var0.getString("Data" + (var24 + 1)) + ",";
         } else {
            var23 = var23 + "0,";
         }
      }

      String var25;
      if(var0.getString("dateUser") != null && var0.getString("dateUser").length() != 0) {
         var25 = var23 + var0.getString("dateUser");
      } else {
         var25 = var23 + "0";
      }

      Log.v("test", "cancel" + var25);
      String var27 = Connection(var0, var25, 13);
      Log.v("test", "cancel return" + var27);
      return var27;
   }

   public static String checkPassword(Bundle var0) throws Exception {
      StringBuilder var1 = (new StringBuilder(String.valueOf(var0.getString("Password")))).append(",");
      Object[] var2 = new Object[]{Integer.valueOf(var0.getInt("PassType"))};
      String var3 = var1.append(String.format("%01d", var2)).toString();
      Log.v("test", var3);
      String var5 = Connection(var0, var3, 5);
      Log.v("test", "return" + var5);
      return var5;
   }

   public static HttpClient createHttpClient() {
      BasicHttpParams var0 = new BasicHttpParams();
      HttpProtocolParams.setVersion(var0, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(var0, "ISO-8859-1");
      HttpProtocolParams.setUseExpectContinue(var0, true);
      SchemeRegistry var1 = new SchemeRegistry();
      var1.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      var1.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
      return new DefaultHttpClient(new ThreadSafeClientConnManager(var0, var1), var0);
   }

   public static String getBarcodeDetail(Bundle var0) throws Exception {
      String var1 = var0.getString("DataBarcode");
      Log.e("test", "data send" + var1);
      String var3 = Connection(var0, var1, 18);
      Log.v("test", "return" + var3);
      return var3;
   }

   @SuppressLint({"SdCardPath"})
   public static void getContent(String var0) throws IOException {
      String[] var1 = var0.split("/");
      Log.v("hello", "url " + var0 + " " + var0.length());
      URL var3 = new URL(var0);
      File var4 = new File("/mnt/sdcard/Resource/save/loaddata/");
      if(!var4.exists()) {
         var4.mkdirs();
      }

      File var5 = new File("/mnt/sdcard/Resource/save/loaddata/" + var1[-1 + var1.length]);
      System.currentTimeMillis();
      BufferedInputStream var8 = new BufferedInputStream(var3.openConnection().getInputStream());
      ByteArrayBuffer var9 = new ByteArrayBuffer(50);

      while(true) {
         int var10 = var8.read();
         if(var10 == -1) {
            FileOutputStream var11 = new FileOutputStream(var5);
            var11.write(var9.toByteArray());
            var11.close();
            return;
         }

         var9.append((byte)var10);
      }
   }

   public static String getProfile() throws Exception {
      String var0 = Connection(new Bundle(), (String)null, 16);
      Log.v("test", "return" + var0);
      return var0;
   }

   public static String getScrollText() throws Exception {
      String var0 = Connection(new Bundle(), (String)null, 14);
      Log.v("test", "return scroll text" + var0);
      return var0;
   }

   public static Bundle[] getSponser() throws Exception {
      String var0 = Connection(new Bundle(), (String)null, 9);
      Log.v("hello", "return sponser " + var0);
      String var2 = var0.split("lastupdate=")[1].split("&total")[0];
      Bundle[] var3 = parserData(var0);
      Log.v("hello", "data len " + var3.length);
      boolean var5 = mainPref.getString("lasttime", "").equals(var2);
      boolean var6 = false;
      if(!var5) {
         var6 = true;
      }

      Log.v("hello", var6 + "returnData sponser " + var0);
      if(var0.contains("OK") && !mainPref.getString("lasttime", "").equals(var2)) {
         for(int var8 = 0; var8 < var3.length; ++var8) {
            Log.v("hello", "before send data " + var3[var8].getString("bannerURL"));
            getContent(var3[var8].getString("bannerURL"));
            Log.v("hello", "load success " + var3[var8].getString("bannerURL"));
            if(var3[var8].getString("soundURL") != null && var3[var8].getString("soundURL").length() > 5) {
               getContent(var3[var8].getString("soundURL"));
            }
         }

         Editor var11 = mainPref.edit();
         var11.putString("lasttime", var2);
         Log.v("hello", "save time");
         var11.commit();
      }

      return var3;
   }

   public static String getTime(Bundle var0) throws Exception {
      Log.v("hello", "gettime data " + var0.getString("phonenumraw") + " " + var0.getString("phonecreditraw"));
      float var2 = Float.parseFloat(var0.getString("Latititude"));
      float var3 = Float.parseFloat(var0.getString("Longtitude"));
      String var4 = var0.getString("MacAddress");
      StringBuilder var5 = (new StringBuilder("1,")).append(var0.getString("phonenum")).append(",").append(var0.getString("phonecredit")).append(",").append(var0.getString("AppVersion")).append(",");
      Object[] var6 = new Object[]{Float.valueOf(var2), Float.valueOf(var3)};
      String var7 = var5.append(String.format("%010.6f,%010.6f,", var6)).append(var4).append(",").append(var0.getString("phonenumraw")).append(",").append(var0.getString("phonecreditraw")).toString();
      Log.v("hello", "gettime data " + var7 + " " + var0.getString("phonecredit"));
      return Connection(var0, var7, 3);
   }

   public static String getTranSaction(Bundle var0) throws Exception {
      Object[] var1 = new Object[]{Integer.valueOf(var0.getInt("PerPage"))};
      StringBuilder var2 = (new StringBuilder(String.valueOf(String.format("%03d", var1)))).append(",");
      Object[] var3 = new Object[]{Integer.valueOf(var0.getInt("Page"))};
      String var4 = var2.append(String.format("%03d", var3)).toString();
      Log.v("test", "data send" + var4);
      String var6 = Connection(var0, var4, 15);
      Log.v("test", "return" + var6);
      return var6;
   }

   public static String inputMoney(Bundle var0) throws Exception {
      Integer.parseInt(var0.getString("Network"));
      StringBuilder var2 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",");
      Object[] var3 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      StringBuilder var4 = var2.append(String.format("%03d", var3)).append(",");
      Object[] var5 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Price")))};
      StringBuilder var6 = var4.append(String.format("%04d", var5)).append(",");
      Object[] var7 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalPrice")))};
      StringBuilder var8 = var6.append(String.format("%04d", var7)).append(",");
      Object[] var9 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin1")))};
      StringBuilder var10 = var8.append(String.format("%04d", var9)).append(",");
      Object[] var11 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin2")))};
      StringBuilder var12 = var10.append(String.format("%04d", var11)).append(",");
      Object[] var13 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin3")))};
      StringBuilder var14 = var12.append(String.format("%04d", var13)).append(",");
      Object[] var15 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank1")))};
      StringBuilder var16 = var14.append(String.format("%04d", var15)).append(",");
      Object[] var17 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank2")))};
      StringBuilder var18 = var16.append(String.format("%04d", var17)).append(",");
      Object[] var19 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("OperationRate")))};
      StringBuilder var20 = var18.append(String.format("%02d", var19)).append(",");
      Object[] var21 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RandomKey")))};
      String var22 = var20.append(String.format("%04d", var21)).toString();
      Log.v("", var22);
      String var24 = Connection(var0, var22, 2);
      Log.v("TopupTime", var24);
      return var24;
   }

   public static String inputMoneyBarcode(Bundle var0) throws Exception {
      Object[] var1 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      String var2 = String.format("%03d", var1);
      if(var0.containsKey("RealNetwork")) {
         Object[] var30 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RealNetwork")))};
         var2 = String.format("%03d", var30);
      }

      StringBuilder var3 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",").append(var2).append(",");
      Object[] var4 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      StringBuilder var5 = var3.append(String.format("%08.2f", var4)).append(",");
      Object[] var6 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalPrice")))};
      StringBuilder var7 = var5.append(String.format("%04d", var6)).append(",");
      Object[] var8 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin1")))};
      StringBuilder var9 = var7.append(String.format("%04d", var8)).append(",");
      Object[] var10 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin2")))};
      StringBuilder var11 = var9.append(String.format("%04d", var10)).append(",");
      Object[] var12 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin3")))};
      StringBuilder var13 = var11.append(String.format("%04d", var12)).append(",");
      Object[] var14 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank1")))};
      StringBuilder var15 = var13.append(String.format("%04d", var14)).append(",");
      Object[] var16 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank2")))};
      StringBuilder var17 = var15.append(String.format("%04d", var16)).append(",");
      Object[] var18 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("OperationRate")))};
      StringBuilder var19 = var17.append(String.format("%02d", var18)).append(",");
      Object[] var20 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RandomKey")))};
      StringBuilder var21 = var19.append(String.format("%04d", var20)).append(",");
      Object[] var22 = new Object[]{Long.valueOf(Long.parseLong(var0.getString("OTP")))};
      String var23 = var21.append(String.format("%010d", var22)).append(",").toString();

      for(int var24 = 0; var24 < 4; ++var24) {
         if(var0.getString("Data" + (var24 + 1)) != null && var0.getString("Data" + (var24 + 1)).length() != 0) {
            var23 = var23 + var0.getString("Data" + (var24 + 1)) + ",";
         } else {
            var23 = var23 + "0,";
         }
      }

      String var25;
      if(var0.getString("dateUser") != null && var0.getString("dateUser").length() != 0) {
         var25 = var23 + var0.getString("dateUser") + ",";
      } else {
         var25 = var23 + "0,";
      }

      String var26 = var25 + var0.getString("TY") + "," + var0.getString("TRAN");
      Log.e("test", "pay 20" + var26);
      String var28 = Connection(var0, var26, 20);
      Log.e("test", "return pay 20" + var28);
      return var28;
   }

   public static String inputMoneyUtil(Bundle var0) throws Exception {
      Object[] var1 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      String var2 = String.format("%03d", var1);
      if(var0.containsKey("RealNetwork")) {
         Object[] var30 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RealNetwork")))};
         var2 = String.format("%03d", var30);
      }

      Log.v("hello", "call 12 " + var2);
      StringBuilder var4 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",").append(var2).append(",");
      Object[] var5 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      StringBuilder var6 = var4.append(String.format("%08.2f", var5)).append(",");
      Object[] var7 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalPrice")))};
      StringBuilder var8 = var6.append(String.format("%04d", var7)).append(",");
      Object[] var9 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin1")))};
      StringBuilder var10 = var8.append(String.format("%04d", var9)).append(",");
      Object[] var11 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin2")))};
      StringBuilder var12 = var10.append(String.format("%04d", var11)).append(",");
      Object[] var13 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalCoin3")))};
      StringBuilder var14 = var12.append(String.format("%04d", var13)).append(",");
      Object[] var15 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank1")))};
      StringBuilder var16 = var14.append(String.format("%04d", var15)).append(",");
      Object[] var17 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("TotalBank2")))};
      StringBuilder var18 = var16.append(String.format("%04d", var17)).append(",");
      Object[] var19 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("OperationRate")))};
      StringBuilder var20 = var18.append(String.format("%02d", var19)).append(",");
      Object[] var21 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RandomKey")))};
      StringBuilder var22 = var20.append(String.format("%04d", var21)).append(",");
      Object[] var23 = new Object[]{Long.valueOf(Long.parseLong(var0.getString("OTP")))};
      String var24 = var22.append(String.format("%010d", var23)).append(",").toString();

      for(int var25 = 0; var25 < 4; ++var25) {
         if(var0.getString("Data" + (var25 + 1)) != null && var0.getString("Data" + (var25 + 1)).length() != 0) {
            var24 = var24 + var0.getString("Data" + (var25 + 1)) + ",";
         } else {
            var24 = var24 + "0,";
         }
      }

      String var26;
      if(var0.getString("dateUser") != null && var0.getString("dateUser").length() != 0) {
         var26 = var24 + var0.getString("dateUser");
      } else {
         var26 = var24 + "0";
      }

      Log.v("test", "pay" + var26);
      String var28 = Connection(var0, var26, 12);
      Log.v("test", "return pay" + var28);
      return var28;
   }

   public static Bundle[] parserData(String var0) {
      Log.v("hello", "return data " + var0);
      String[] var2 = var0.split("&");
      Bundle[] var3 = null;

      for(int var4 = 0; var4 < var2.length; ++var4) {
         if(var2[var4].contains("total")) {
            String var11 = var2[var4].replace("total=", "").replace("#", "").trim();
            var3 = new Bundle[Integer.parseInt(var11)];

            for(int var12 = 0; var12 < Integer.parseInt(var11); ++var12) {
               var3[var12] = new Bundle();
            }
         } else if(var2[var4].contains("banner")) {
            String[] var9 = var2[var4].split("=");
            String var10 = var9[1];
            var3[-1 + Integer.parseInt(var9[0].replace("banner", ""))].putString("bannerURL", var10);
         } else if(var2[var4].contains("sound")) {
            String[] var7 = var2[var4].split("=");
            if(var7.length == 2) {
               String var8 = var7[1];
               var3[-1 + Integer.parseInt(var7[0].replace("sound", ""))].putString("soundURL", var8);
            }
         } else if(var2[var4].contains("timewait")) {
            String[] var5 = var2[var4].split("=");
            String var6 = var5[1];
            var3[-1 + Integer.parseInt(var5[0].replace("timewait", ""))].putInt("timeShow", Integer.parseInt(var6.replace("#", "").trim()));
         }
      }

      return var3;
   }

   public static String putPicture(Bundle var0) throws Exception {
      String var1 = var0.getString("FileName");
      Log.v("test", var1);
      String var3 = Connection(var0, var1, 7);
      Log.v("test", "return" + var3);
      return var3;
   }

   public static String ready(Bundle var0) throws Exception {
      Integer.parseInt(var0.getString("Network"));
      StringBuilder var2 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",");
      Object[] var3 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      StringBuilder var4 = var2.append(String.format("%03d", var3)).append(",");
      Object[] var5 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Price")))};
      String var6 = var4.append(String.format("%04d", var5)).toString();
      Log.v("test", var6);
      String var8 = Connection(var0, var6, 1);
      Log.v("test", var8);
      return var8;
   }

   public static String readyBarcode(Bundle var0) throws Exception {
      StringBuilder var1 = new StringBuilder("price ");
      Object[] var2 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      Log.v("test", var1.append(String.format("%05f", var2)).toString());
      Object[] var4 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      String var5 = String.format("%03d", var4);
      if(var0.containsKey("RealNetwork")) {
         Object[] var17 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RealNetwork")))};
         var5 = String.format("%03d", var17);
      }

      Log.v("hello", "str " + var5);
      StringBuilder var7 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",").append(var5).append(",");
      Object[] var8 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      StringBuilder var9 = var7.append(String.format("%08.2f", var8)).append(",");
      Object[] var10 = new Object[]{Long.valueOf(Long.parseLong(var0.getString("OTP")))};
      String var11 = var9.append(String.format("%010d", var10)).append(",").toString();

      for(int var12 = 0; var12 < 4; ++var12) {
         if(var0.getString("Data" + (var12 + 1)) != null) {
            var11 = var11 + var0.getString("Data" + (var12 + 1)) + ",";
         } else {
            var11 = var11 + "0,";
         }
      }

      String var13;
      if(var0.getString("dateUser") != null) {
         var13 = var11 + var0.getString("dateUser");
      } else {
         var13 = var11 + "0";
      }

      Log.v("test", "data send 19 " + var13);
      String var15 = Connection(var0, var13, 19);
      Log.v("test", "return 19 " + var15);
      return var15;
   }

   public static String readyUtility(Bundle var0) throws Exception {
      StringBuilder var1 = new StringBuilder("price ");
      Object[] var2 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      Log.v("test", var1.append(String.format("%05f", var2)).toString());
      Object[] var4 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("Network")))};
      String var5 = String.format("%03d", var4);
      if(var0.containsKey("RealNetwork")) {
         Object[] var17 = new Object[]{Integer.valueOf(Integer.parseInt(var0.getString("RealNetwork")))};
         var5 = String.format("%03d", var17);
      }

      Log.v("hello", "str " + var5);
      StringBuilder var7 = (new StringBuilder(String.valueOf(var0.getString("Mobile")))).append(",").append(var5).append(",");
      Object[] var8 = new Object[]{Float.valueOf(Float.parseFloat(var0.getString("Price")))};
      StringBuilder var9 = var7.append(String.format("%08.2f", var8)).append(",");
      Object[] var10 = new Object[]{Long.valueOf(Long.parseLong(var0.getString("OTP")))};
      String var11 = var9.append(String.format("%010d", var10)).append(",").toString();

      for(int var12 = 0; var12 < 4; ++var12) {
         if(var0.getString("Data" + (var12 + 1)) != null) {
            var11 = var11 + var0.getString("Data" + (var12 + 1)) + ",";
         } else {
            var11 = var11 + "0,";
         }
      }

      String var13;
      if(var0.getString("dateUser") != null) {
         var13 = var11 + var0.getString("dateUser");
      } else {
         var13 = var11 + "0";
      }

      Log.e("test", "data send " + var13);
      String var15 = Connection(var0, var13, 11);
      Log.e("test", "return 11 " + var15);
      return var15;
   }

   public static String resetAmount(Bundle var0) throws Exception {
      String var1 = var0.getString("Password");
      Log.v("test", var1);
      String var3 = Connection(var0, var1, 6);
      Log.v("test", "return" + var3);
      return var3;
   }

   public static String sendProblem(Bundle var0) throws Exception {
      String var1 = var0.getString("Mobile") + "," + var0.getString("Word");
      Log.v("test", "send" + var1);
      String var3 = Connection(var0, var1, 10);
      Log.v("test", "send return" + var3);
      return var3;
   }

   public static String setBoxStatus(Bundle var0) throws Exception {
      String var1 = var0.getString("Status");
      Log.v("test", "service17 data" + var1);
      String var3 = Connection(var0, var1, 17);
      Log.v("test", "return" + var3);
      return var3;
   }

   public static String sirenAlarm(Bundle var0) throws Exception {
      String var1 = "Silence Alarm " + globalPref.getString("HARDWARE_ID", "") + "|";
      Log.v("test", "silen " + var1);
      String var3 = Connection(var0, var1, 8);
      Log.v("test", "return" + var3);
      return var3;
   }
}
