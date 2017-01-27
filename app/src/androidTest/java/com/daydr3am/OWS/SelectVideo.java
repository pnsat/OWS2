package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;
import com.daydr3am.OWS.PlayVideo;
import com.daydr3am.lib.CallImage;

public class SelectVideo extends IORootActivity {
   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361941);
      Button var4 = (Button)this.findViewById(2131361942);
      Button var5 = (Button)this.findViewById(2131361943);
      Button var6 = (Button)this.findViewById(2131361944);
      Button var7 = (Button)this.findViewById(2131361945);
      Button var8 = (Button)this.findViewById(2131361946);
      var4.setBackgroundDrawable(CallImage.imageDrawableCard("bt_menu"));
      var5.setBackgroundDrawable(CallImage.imageDrawableCard("bt_menu"));
      var6.setBackgroundDrawable(CallImage.imageDrawableCard("bt_menu"));
      var7.setBackgroundDrawable(CallImage.imageDrawableCard("bt_menu"));
      var8.setBackgroundDrawable(CallImage.imageDrawableCard("bt_menu"));
      var3.setText(MessageTopup.getMessage(7));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setText(MessageTopup.getMessage(33));
      var4.setTypeface(MessageTopup.setFont(this, 0));
      var5.setText(MessageTopup.getMessage(34));
      var5.setTypeface(MessageTopup.setFont(this, 0));
      var6.setText(MessageTopup.getMessage(35));
      var6.setTypeface(MessageTopup.setFont(this, 0));
      var7.setText(MessageTopup.getMessage(89));
      var7.setTypeface(MessageTopup.setFont(this, 0));
      var8.setText(MessageTopup.getMessage(36));
      var8.setTypeface(MessageTopup.setFont(this, 0));
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903066);
      super.onCreate(var1);
      this.findViewById(2131361863).setVisibility(8);
      ((FrameLayout)this.findViewById(2131361799)).setBackgroundDrawable(CallImage.imageDrawableCard("bg_orange"));
      ((Button)this.findViewById(2131361942)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putString("param1", "click01");
            var2.putInt("video_id", 0);
            Intent var3 = new Intent(var1.getContext(), PlayVideo.class);
            var3.putExtras(var2);
            SelectVideo.this.startActivityForResult(var3, 0);
         }
      });
      ((Button)this.findViewById(2131361943)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putString("param1", "click01");
            var2.putInt("video_id", 1);
            Intent var3 = new Intent(var1.getContext(), PlayVideo.class);
            var3.putExtras(var2);
            SelectVideo.this.startActivityForResult(var3, 0);
         }
      });
      ((Button)this.findViewById(2131361944)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putString("param1", "click01");
            var2.putInt("video_id", 2);
            Intent var3 = new Intent(var1.getContext(), PlayVideo.class);
            var3.putExtras(var2);
            SelectVideo.this.startActivityForResult(var3, 0);
         }
      });
      ((Button)this.findViewById(2131361945)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putString("param1", "click01");
            var2.putInt("video_id", 3);
            Intent var3 = new Intent(var1.getContext(), PlayVideo.class);
            var3.putExtras(var2);
            SelectVideo.this.startActivityForResult(var3, 0);
         }
      });
      ((Button)this.findViewById(2131361946)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Bundle var2 = new Bundle();
            var2.putString("param1", "click01");
            var2.putInt("video_id", 4);
            Intent var3 = new Intent(var1.getContext(), PlayVideo.class);
            var3.putExtras(var2);
            SelectVideo.this.startActivityForResult(var3, 0);
         }
      });
      this.changeLanguage(switchID);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
   }
}
