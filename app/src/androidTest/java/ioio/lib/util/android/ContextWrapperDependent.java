package ioio.lib.util.android;

import android.content.ContextWrapper;

public interface ContextWrapperDependent {
   void close();

   void onCreate(ContextWrapper var1);

   void onDestroy();

   void open();

   void reopen();
}
