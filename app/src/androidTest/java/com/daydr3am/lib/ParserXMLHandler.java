package com.daydr3am.lib;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserXMLHandler extends DefaultHandler {
   public static final int DID_FINISH_ELEMENT = 8;
   public static final int DID_START_ELEMENT = 1;
   public static final int END_DOCUMENT = 10;
   public static final int START_DOCUMENT;
   private Boolean currentElement = Boolean.valueOf(false);
   private String currentValue = null;
   private Handler handler;

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      if(this.currentElement.booleanValue()) {
         this.currentValue = new String(var1, var2, var3);
         this.currentElement = Boolean.valueOf(false);
      }

   }

   public void endDocument() throws SAXException {
      this.handler.sendMessage(Message.obtain(this.handler, 10));
   }

   public void endElement(String var1, String var2, String var3) throws SAXException {
      this.currentElement = Boolean.valueOf(false);
      ElementData var4 = new ElementData();
      var4.name = var2;
      var4.value = this.currentValue;
      this.handler.sendMessage(Message.obtain(this.handler, 8, var4));
      this.currentValue = "";
   }

   public void startDocument() throws SAXException {
      this.handler.sendMessage(Message.obtain(this.handler, 0));
   }

   public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
      this.currentElement = Boolean.valueOf(true);
      ElementData var5 = new ElementData();
      var5.name = var2;

      for(int var6 = 0; var6 < var4.getLength(); ++var6) {
         String var7 = var4.getLocalName(var6);
         String var8 = var4.getValue(var7);
         var5.attributes.put(var7, var8);
         Log.d("Parse Element", "Type :" + var7 + " Value :" + var8);
      }

      this.handler.sendMessage(Message.obtain(this.handler, 1, var5));
   }

   public class ElementData {
      public final Map attributes = new HashMap();
      public String name = null;
      public String value = null;
   }
}
