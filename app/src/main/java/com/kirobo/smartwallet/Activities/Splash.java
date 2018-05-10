package com.kirobo.smartwallet.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kirobo.smartwallet.MyApplication;
import com.kirobo.smartwallet.R;
import com.kirobo.smartwallet.Utils.UtilPermissions;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Splash extends Activity {

  private static final int PERMISSION_ALL = 0;
  private Handler h;
  private Runnable r;
  private MyApplication appstate;

  /*
    SharedPreferences mPrefs;
    final String settingScreenShownPref = "settingScreenShown";
    final String versionCheckedPref = "versionChecked";
  */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    appstate = (MyApplication)this.getApplicationContext();
    appstate.OpenProgressBar(this);

    h = new Handler();
    r = new Runnable() {
      @Override
      public void run() {
//        Toast.makeText(Splash.this, "Runnable started", Toast.LENGTH_SHORT).show();

  /*          // (OPTIONAL) these lines to check if the `First run` ativity is required
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPrefs.edit();

                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);

                if (!settingScreenShown || savedVersionCode != versionCode) {
                    startActivity(new Intent(Splash.this, FirstRun.class));
                    editor.putBoolean(settingScreenShownPref, true);
                    editor.putInt(versionCheckedPref, versionCode);
                    editor.commit();
                }
                else
  */
        startActivity(new Intent(Splash.this, LoginActivity.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
      }
    };

    String[] PERMISSIONS = {
      WRITE_EXTERNAL_STORAGE,

    };

    if(!UtilPermissions.hasPermissions(this, PERMISSIONS)){
      ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }
    else
      h.postDelayed(r, 1500);
  }

  // Put the below OnRequestPermissionsResult code here
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    int index = 0;
    Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
    for (String permission : permissions){
      PermissionsMap.put(permission, grantResults[index]);
      index++;
    }

    if(PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != 0) {
      Toast.makeText(this, "Storage permissions are a must", Toast.LENGTH_SHORT).show();
      finish();
    }
    else
    {
      h.postDelayed(r, 1500);
    }
  }
}
