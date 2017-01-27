package com.daydr3am.OWS;

import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MessageTopup;

public class PlayVideo extends IORootActivity {
   int id_text_play;
   int id_text_topic;

   public void changeLanguage(int var1) {
      Log.d("debug", "changeLanguage main " + var1);
      TextView var3 = (TextView)this.findViewById(2131361918);
      Button var4 = (Button)this.findViewById(2131361920);
      if(this.id_text_topic == 36) {
         this.id_text_topic = 89;
      } else if(this.id_text_topic == 37) {
         this.id_text_topic = 36;
      }

      var3.setText(MessageTopup.getMessage(this.id_text_topic));
      var4.setText(MessageTopup.getMessage(this.id_text_play));
      var3.setTypeface(MessageTopup.setFont(this, 0));
      var4.setTypeface(MessageTopup.setFont(this, 0));
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130903060);
      super.onCreate(var1);
      this.findViewById(2131361863).setVisibility(8);
      final Bundle var2 = this.getIntent().getExtras();
      String var3 = var2.getString("param1");
      this.id_text_play = 90 + var2.getInt("video_id");
      this.id_text_topic = 33 + var2.getInt("video_id");
      Log.v("test", var3);
      ((Button)this.findViewById(2131361920)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            String var2x = Environment.getExternalStorageDirectory() + "/Resource/Video/video" + var2.getInt("video_id") + ".mp4";
            Log.v("hello", "play path" + var2x);
            VideoView var4 = (VideoView)PlayVideo.this.findViewById(2131361919);
            var4.setVideoPath(var2x);
            var4.start();
            DisplayMetrics var5 = new DisplayMetrics();
            PlayVideo.this.getWindowManager().getDefaultDisplay().getMetrics(var5);
            int var6 = var5.heightPixels;
            int var7 = var5.widthPixels;
            Log.v("hello", "width " + var7 + " height " + var6);
         }
      });
      this.changeLanguage(switchID);
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
   }
}
