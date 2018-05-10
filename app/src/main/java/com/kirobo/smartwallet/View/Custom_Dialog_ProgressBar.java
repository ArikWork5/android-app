package com.kirobo.smartwallet.View;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.kirobo.smartwallet.R;

public class Custom_Dialog_ProgressBar extends Dialog {


    public Activity context;


    public Custom_Dialog_ProgressBar(Activity context) {
      super(context, R.style.CustomDialog);
      // TODO Auto-generated constructor stub
      this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.custom_progress_bar);

    }


  }
