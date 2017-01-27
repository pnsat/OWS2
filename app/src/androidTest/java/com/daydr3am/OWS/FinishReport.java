package com.daydr3am.OWS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.daydr3am.OWS.IORootActivity;
import com.daydr3am.OWS.MainPage;
import com.daydr3am.OWS.MessageTopup;

public class FinishReport extends IORootActivity {
   public void onCreate(Bundle var1) {
      this.setContentView(2130903048);
      super.onCreate(var1);
      ((TextView)this.findViewById(2131361859)).setTypeface(MessageTopup.setFont(this, 0));
      ((Button)this.findViewById(2131361797)).setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Intent var2 = new Intent(FinishReport.this.getBaseContext(), MainPage.class);
            var2.setFlags(67108864);
            FinishReport.this.startActivity(var2);
         }
      });
   }

   protected void onResume() {
      super.onResume();
      Log.d("debug", "onResume active");
   }
}
