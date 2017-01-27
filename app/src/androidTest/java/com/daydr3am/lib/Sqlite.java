package com.daydr3am.lib;

import android.os.Bundle;
import java.util.Random;

public class Sqlite {
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
}
