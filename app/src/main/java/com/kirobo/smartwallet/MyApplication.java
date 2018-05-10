package com.kirobo.smartwallet;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.kirobo.smartwallet.View.Custom_Dialog_ProgressBar;


public class MyApplication extends Application {

   Custom_Dialog_ProgressBar pbd;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  public void OpenProgressBar(final Activity activity) {

    Log.d("BBB", "open: " + pbd);
    try {
      if (pbd == null || (pbd != null && !pbd.isShowing())) {
        Log.d("pigipigi", "222: ");

        pbd = new Custom_Dialog_ProgressBar(activity);
        pbd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pbd.setCanceledOnTouchOutside(false);
        pbd.setCancelable(false);
        pbd.show();
      }

    }catch(Exception e){
      Log.d("pigipigi", "errrorr: " + e.getMessage());
    }
  }
  public void CloseProgressBar(){

    Log.d("BBB", "close: " + pbd);
    try {
      if (pbd != null && pbd.isShowing()) {
         pbd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         pbd.dismiss();
      }
    }catch (Exception e){
      Log.d("pigipigi", "eeee:" + e.getMessage());
    }
  }

}
