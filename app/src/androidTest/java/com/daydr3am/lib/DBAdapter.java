package com.daydr3am.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBAdapter {
   private static final String DATABASE_NAME = "DataBase1";
   private static final int DATABASE_VERSION = 2;
   public static final String OPTIMIZATION_SQL = "PRAGMA synchronous=OFF; PRAGMA count_changes=OFF; VACUUM;";
   private String base = "";
   private final Context mCtx;
   private SQLiteDatabase mDb;
   private DatabaseHelper mDbHelper;

   public DBAdapter(Context var1) {
      this.mCtx = var1;
   }

   static String getValue(String var0, String var1, String var2) {
      try {
         String var4 = var0.substring(var0.indexOf(var1.toString()), var0.indexOf(var2.toString())).replaceAll(var1, "").replace("<![CDATA[", "").replace("]]>", "").replace("\'", "");
         return var4;
      } catch (Exception var5) {
         return "";
      }
   }

   void chekDbAvailable() {
      do {
         if(this.isDbLockedByCurrentThread() || this.isDbLockedByOtherThreads()) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var3) {
               ;
            } catch (SQLiteException var4) {
               ;
            }
         }
      } while(this.isOpen() && (this.isDbLockedByCurrentThread() || this.isDbLockedByOtherThreads()));

   }

   public void deleteAritcles(String var1) {
      this.mDb.execSQL("delete from articles where stype = \'" + var1 + "\' ;");
   }

   public void deleteAritclesAll() {
      this.mDb.execSQL("delete from articles  ;");
   }

   public void deleteLogin() {
      this.mDb.execSQL("delete from login;");
   }

   public void feedrss(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void feedrss2(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean getAritcles(String var1) {
      int var2 = this.mDb.query(true, "articles", new String[]{"*"}, " stype = \'" + var1 + "\'", (String[])null, (String)null, (String)null, (String)null, (String)null).getCount();
      Log.d("dev", var1 + "numRows=====>" + var2);
      return false;
   }

   public boolean getAritclesEx(String var1) {
      int var4;
      byte var6;
      Cursor var3;
      label29: {
         Log.d("dev", var1 + "   stype=====>");
         var3 = this.mDb.query(true, "articles", new String[]{"*"}, " stype = \'" + var1 + "\'", (String[])null, (String)null, (String)null, (String)null, (String)null);
         var4 = var3.getCount();
         Log.d("dev", var1 + "numRows=====>" + var4);
         if(!var1.equals("4") && !var1.equals("5") && !var1.equals("6") && !var1.equals("7") && !var1.equals("8") && !var1.equals("9") && !var1.equals("12") && !var1.equals("15")) {
            boolean var8 = var1.equals("21");
            var6 = 0;
            if(!var8) {
               break label29;
            }
         }

         var6 = 1;
      }

      if(var4 > var6) {
         var3.moveToFirst();
         return true;
      } else {
         return false;
      }
   }

   public boolean getFirst(String var1) {
      return this.mDb.query(true, "first", new String[]{"*"}, " datetime = \'" + var1 + "\'", (String[])null, (String)null, (String)null, (String)null, (String)null).getCount() > 0;
   }

   public String getLogin() {
      Cursor var1 = this.mDb.query(true, "login", new String[]{"*"}, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var1.getCount() > 0) {
         var1.moveToFirst();
         return var1.getString(var1.getColumnIndex("user"));
      } else {
         return "0";
      }
   }

   public String getLoginemail() {
      Cursor var1 = this.mDb.query(true, "login", new String[]{"*"}, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var1.getCount() > 0) {
         var1.moveToFirst();
         return var1.getString(var1.getColumnIndex("email"));
      } else {
         return "";
      }
   }

   public String getLoginpwd() {
      Cursor var1 = this.mDb.query(true, "login", new String[]{"*"}, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var1.getCount() > 0) {
         var1.moveToFirst();
         return var1.getString(var1.getColumnIndex("pwd"));
      } else {
         return "0";
      }
   }

   public Cursor getValueAritcles(String var1) {
      Cursor var2 = this.mDb.query(true, "articles", new String[]{"*"}, " stype = \'" + var1 + "\'", (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var2.getCount() > 0) {
         var2.moveToFirst();
      }

      return var2;
   }

   public Cursor getValueAritclesbyID(int var1) {
      Cursor var2 = this.mDb.query(true, "articles", new String[]{"*"}, " _id = \'" + var1 + "\'  order by _id asc", (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var2.getCount() > 0) {
         var2.moveToFirst();
      }

      return var2;
   }

   public Cursor getValueAritclesbyID2(int var1, String var2) {
      Cursor var3 = this.mDb.query(true, "articles", new String[]{"*"}, " _id > \'" + var1 + "\' and stype = \'" + var2 + "\' order by _id ASC", (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var3.getCount() > 0) {
         ;
      }

      return var3;
   }

   public Cursor getValueAritclesbyID3(int var1, String var2) {
      Cursor var3 = this.mDb.query(true, "articles", new String[]{"*"}, " _id < \'" + var1 + "\' and stype = \'" + var2 + "\' ", (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var3.getCount() > 0) {
         ;
      }

      return var3;
   }

   public Cursor getValueAritclesbyStype(String var1) {
      Cursor var2 = this.mDb.query(true, "articles", new String[]{"*"}, " stype = \'" + var1 + "\'", (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var2.getCount() > 0) {
         ;
      }

      return var2;
   }

   public void insertAritcles(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12) {
      ContentValues var13 = new ContentValues();
      var13.put("stype", var1);
      var13.put("column", var2);
      var13.put("label", var3);
      var13.put("title", var4);
      var13.put("byline", var5);
      var13.put("description", var6);
      var13.put("detail", var7);
      var13.put("link", var8);
      var13.put("pubDate", var9);
      var13.put("thumbnail", var10);
      var13.put("photo", var11);
      var13.put("adpic", var12);
      this.mDb.insert("articles", (String)null, var13);
   }

   public void insertData(ContentValues var1) {
      this.mDb.insert("record", (String)null, var1);
   }

   public void install() {
      this.mDb.execSQL("CREATE TABLE IF NOT EXISTS record (Id integer primary key autoincrement , stype text,Date text,ServiceType text,PhoneNumber text,Price text,Status text);");
   }

   public boolean isDbLockedByCurrentThread() {
      return this.mDb != null && this.mDb.isDbLockedByCurrentThread();
   }

   public boolean isDbLockedByOtherThreads() {
      return this.mDb != null && this.mDb.isDbLockedByOtherThreads();
   }

   public boolean isOpen() {
      return this.mDb != null && this.mDb.isOpen();
   }

   public DBAdapter open() throws SQLException {
      // $FF: Couldn't be decompiled
   }

   public Cursor query() {
      Cursor var1 = this.mDb.query(true, "record", new String[]{"*"}, (String)null, (String[])null, (String)null, (String)null, (String)null, (String)null);
      if(var1.getCount() > 0) {
         ;
      }

      return var1;
   }

   public void saveFirst(String var1) {
      ContentValues var2 = new ContentValues();
      var2.put("datetime", var1);
      this.mDb.insert("first", (String)null, var2);
   }

   public void saveLogin(String var1, String var2, String var3) {
      ContentValues var4 = new ContentValues();
      var4.put("user", var1);
      var4.put("pwd", var2);
      var4.put("email", var3);
      this.mDb.insert("login", (String)null, var4);
   }

   private static class DatabaseHelper extends SQLiteOpenHelper {
      DatabaseHelper(Context var1) {
         super(var1, "DataBase1", (CursorFactory)null, 2);
      }

      public void onCreate(SQLiteDatabase var1) {
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         Log.w("nation", "Upgrading database from version " + var2 + " to " + var3 + ", which will destroy all old data");
         var1.execSQL("DROP TABLE IF EXISTS notes");
         this.onCreate(var1);
      }
   }
}
