package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.Loading;

public class Record extends IORootActivity {
   int page;
   String[] service = new String[]{"1 2 Call", "Dtac", "Truemove", "Hutch", "CAT-CDMA", "Truemoney", "@Cash", "True D Serial", "Cookie Card", "Winner Online", "TOT Prepaid", "Play Card", "Gcash", "Tolld", "ถูกดี", "trueh"};

   private void createHeader() {
      TableLayout var1 = (TableLayout)this.findViewById(2131361923);
      TableRow var2 = new TableRow(this);
      var2.setId(99);
      var2.setLayoutParams(new LayoutParams(-1, -2));
      TextView var3 = new TextView(this);
      var3.setText("id");
      var3.setTextColor(-16777216);
      var3.setLayoutParams(new LayoutParams(30, -2));
      var2.addView(var3);
      TextView var4 = new TextView(this);
      var4.setText("วันที่");
      var4.setTextColor(-16777216);
      var4.setLayoutParams(new LayoutParams(170, -2));
      var2.addView(var4);
      TextView var5 = new TextView(this);
      var5.setText("เครื่อข่าย");
      var5.setTextColor(-16777216);
      var5.setLayoutParams(new LayoutParams(80, -2));
      var2.addView(var5);
      TextView var6 = new TextView(this);
      var6.setText("เบอร์โทร");
      var6.setTextColor(-16777216);
      var6.setLayoutParams(new LayoutParams(100, -2));
      var2.addView(var6);
      TextView var7 = new TextView(this);
      var7.setText("ราคา");
      var7.setTextColor(-16777216);
      var7.setLayoutParams(new LayoutParams(50, -2));
      var2.addView(var7);
      TextView var8 = new TextView(this);
      var8.setText("สถานะ");
      var8.setTextColor(-16777216);
      var8.setLayoutParams(new LayoutParams(-1, -2));
      var2.addView(var8);
      var1.addView(var2);
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903062);
      super.onCreate(var1);
      this.findViewById(2131361862).setVisibility(8);
      this.findViewById(2131361863).setVisibility(8);
      this.findViewById(2131361924).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putInt("Service", 15);
            var2.putInt("Page", 1 + Record.this.page);
            Intent var3 = new Intent(Record.this, Loading.class);
            var3.putExtras(var2);
            Record.this.startActivityForResult(var3, 0);
         }
      });
      this.findViewById(2131361925).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putInt("Service", 15);
            var2.putInt("Page", -1 + Record.this.page);
            if(-1 + Record.this.page >= 0) {
               Intent var3 = new Intent(Record.this, Loading.class);
               var3.putExtras(var2);
               Record.this.startActivityForResult(var3, 0);
            }

         }
      });
      Bundle var2 = this.getIntent().getExtras();
      if(var2 != null && var2.getInt("Page") != 0) {
         this.page = var2.getInt("Page");
         Log.v("hello", "newpage" + var2.getInt("Page"));
      }

   }

   protected void onDestroy() {
      super.onDestroy();
      System.gc();
   }

   protected void onResume() {
      // $FF: Couldn't be decompiled
   }
}
