package com.daydr3am.lib;

import android.os.Bundle;
import android.util.Log;
import java.util.Date;
import java.util.Random;

public class Coding {
   public static String encoding(Bundle var0) {
      int var1 = var0.getInt("SERVICE_ID");
      int var2 = var0.getInt("API_ID");
      int var3 = var0.getInt("PARTNER_ID");
      int var4 = var0.getInt("HARDWARE_ID");
      String var5 = generate200(var0.getString("COMMAND_DATA"));
      String var6 = var0.getString("HARDWARE_SN");
      Random var7 = new Random();
      int var8 = 10000 + var7.nextInt('鱀');
      int var9 = 10000 + var7.nextInt('鱀');
      int var10 = 10000 + var7.nextInt('鱀');
      int var11 = 10000 + var7.nextInt('鱀');
      String var12 = String.valueOf(var8 + var1 * 1);
      String var13 = String.valueOf(var9 + var2 * 1);
      String var14 = String.valueOf(var10 + var3 * 1);
      String var15 = String.valueOf(var11 + var4 * 1);
      String var16 = var5.substring(0, 100);
      String var17 = var5.substring(100, 200);
      String var18 = "" + var10 + var8 + var16 + var12 + var14 + var11 + var9 + var17 + var13 + var15;
      String var19 = var6;

      for(int var20 = 0; var20 < 23; ++var20) {
         var19 = var19 + var6;
      }

      int var21 = 1 + var7.nextInt(8);
      int var22 = 1 + var7.nextInt(8);
      String var23 = "";
      int var24 = 0;

      while(true) {
         int var25 = var19.length();
         if(var24 >= var25) {
            String var29 = var21 + var23 + var22;
            int var30 = 0;
            int var31 = 0;

            while(true) {
               int var32 = -1 + var29.length();
               if(var31 > var32) {
                  Object[] var33 = new Object[]{Integer.valueOf(var30)};
                  String var34 = String.format("%04d", var33);
                  String var35 = var34.substring(0, 2);
                  String var36 = var34.substring(2, 4);
                  String var37 = var29.substring(0, 361);
                  String var38 = var29.substring(361, 722);
                  return var35 + var37 + var36 + var38;
               }

               var30 += Integer.parseInt("" + var29.charAt(var31));
               ++var31;
            }
         }

         int var26 = var22 + var21 + (short)var19.charAt(var24) + (short)var18.charAt(var24);
         Object[] var27 = new Object[]{Integer.valueOf(var26)};
         String var28 = String.format("%03d", var27);
         var23 = var23 + var28;
         ++var24;
      }
   }

   public static String generate200(String var0) {
      String var1 = var0 + "|";

      for(Random var2 = new Random(); var1.length() < 200; var1 = var1 + var2.nextInt(10)) {
         ;
      }

      return var1;
   }

   public static String newencoding(String var0, String var1) {
      String var2 = var1 + var1;
      int var3 = 0;

      for(int var4 = 0; var4 < 32; ++var4) {
         char var18;
         if(var4 < var0.length()) {
            var18 = var0.charAt(var4);
         } else {
            var18 = 48;
         }

         var3 += (var18 + var2.charAt(var4)) % 16;
      }

      Date var5 = new Date();
      int var6 = 100 * var5.getMinutes() + var5.getSeconds();
      StringBuilder var7 = new StringBuilder("serviceme  ");
      Object[] var8 = new Object[]{Integer.valueOf(var3), Integer.valueOf(var6), Integer.valueOf(var6), Integer.valueOf(var3 ^ var6)};
      Log.v("hello", var7.append(String.format("%x %x %d %x", var8)).toString());
      Object[] var10 = new Object[]{Integer.valueOf(var3 ^ var6)};
      String var11 = String.format("%04x", var10);
      Object[] var12 = new Object[]{Integer.valueOf(var6)};
      String var13 = String.format("%04d", var12);
      Log.v("hello", "time " + var6 + " " + var13);
      char[] var15 = new char[8];

      for(int var16 = 0; var16 < 4; ++var16) {
         var15[var16 * 2] = var11.charAt(var16);
         var15[1 + var16 * 2] = var13.charAt(var16);
      }

      Log.v("hello", "serviceme " + new String(var15));
      return new String(var15);
   }
}
