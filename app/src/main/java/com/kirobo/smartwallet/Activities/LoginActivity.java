package com.kirobo.smartwallet.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kirobo.smartwallet.MyApplication;
import com.kirobo.smartwallet.R;
import com.kirobo.smartwallet.Utils.Utils;
import com.kirobo.smartwallet.Utils.Wallet;

public class LoginActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

      private RadioGroup wallet_rg;
      private EditText private_key;
      private RelativeLayout enter_lay;
      private TextView import_wallet,validation_key_txt;
      private MyApplication appstate;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitViews();
    }

    /**
     * Main Init Function
     */
    private void InitViews() {

      appstate = (MyApplication)this.getApplicationContext();
      appstate.CloseProgressBar();

      private_key = (EditText) findViewById(R.id.private_key);
      wallet_rg = (RadioGroup) findViewById(R.id.wallet_rg);
      enter_lay = (RelativeLayout) findViewById(R.id.enter_lay);
      import_wallet = (TextView) findViewById(R.id.import_wallet);
      validation_key_txt = (TextView) findViewById(R.id.validation_key_txt);

      import_wallet.setOnClickListener(this);
      wallet_rg.setOnCheckedChangeListener(this);
    }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
    switch(checkedId) {
      case R.id.enter_your_wallet:

            YoYo.with(Techniques.SlideInDown).duration(700).playOn(enter_lay);

        break;
      case R.id.open_new_wallet:

            YoYo.with(Techniques.SlideOutUp).duration(700).playOn(enter_lay);

        break;
    }
  }

  @Override
  public void onClick(View v) {
    //Close keyboard if open
   // Utils.HideSoftKeyboard(this);

      String privateKeyInput = ""+private_key.getText();
          // Private key length validation

          if(privateKeyInput.length() != 64 || privateKeyInput == null || privateKeyInput.length() == 0) {
                validation_key_txt.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Shake).duration(700).playOn(validation_key_txt);
          } else {
                appstate.OpenProgressBar(this);
                Wallet iw = new Wallet();
            if (iw.ImportExistingWallet(this,""+privateKeyInput,"password")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }else{
                 appstate.CloseProgressBar();
            }

          }
  }

}
