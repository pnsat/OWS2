package com.daydr3am.OWS;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.util.Log;
import com.daydr3am.OWS.IORootActivity;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MessageTopup {
   public static final String EN_TEXT0 = "text test eng 0";
   private static String FONTS_PATH = "/mnt/sdcard/Resource/message/";
   public static final String TH_TEXT0 = "text test thai 0";
   public static Context baseContext;
   static NodeList nodes;

   public static void LoadMessage() {
      // $FF: Couldn't be decompiled
   }

   public static String getCharacterDataFromElement(Node var0) {
      Node var1 = var0.getFirstChild();
      return var1 instanceof CharacterData?((CharacterData)var1).getData():"";
   }

   public static void getData(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static String getMessage(int var0) {
      String var5;
      String var4;
      if(nodes == null) {
         LoadMessage();
         Node var7 = nodes.item(var0);
         Node var8 = var7.getChildNodes().item(1);
         Node var9 = var7.getChildNodes().item(3);
         var4 = getCharacterDataFromElement(var8);
         var5 = getCharacterDataFromElement(var9);
      } else {
         Node var1 = nodes.item(var0);
         Node var2 = var1.getChildNodes().item(1);
         Node var3 = var1.getChildNodes().item(3);
         var4 = getCharacterDataFromElement(var2);
         var5 = getCharacterDataFromElement(var3);
      }

      String var6;
      if(IORootActivity.switchID == 1) {
         var6 = var4;
      } else {
         var6 = var5;
      }

      if(var6 == null || var6.equalsIgnoreCase("null")) {
         var6 = "Not message";
      }

      return var6;
   }

   public static String getValue(String var0, String var1, String var2) {
      Log.d("debug_data", "getvalue --------------------- star ");

      try {
         String var6 = var0.substring(var0.indexOf(var1.toString()), var0.indexOf(var2.toString())).replaceAll(var1, "").replace("<![CDATA[", "").replace("]]>", "").replace("\'", "");
         return var6;
      } catch (Exception var7) {
         Log.d("debug", "Exception " + var7);
         return "";
      }
   }

   public static Typeface setFont(Context var0, int var1) {
      String[] var2 = new String[]{"PSLxKittithada Bold.ttf", "PSLxKittithada BoldItalic.ttf", "PSLxKittithada Italic.ttf", "PSLxKittithada.ttf"};

      try {
         Typeface var4 = Typeface.createFromAsset(((ContextWrapper)var0).getBaseContext().getAssets(), var2[var1]);
         return var4;
      } catch (Exception var5) {
         return null;
      }
   }
}
