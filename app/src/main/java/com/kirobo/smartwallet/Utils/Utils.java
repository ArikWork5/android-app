package com.kirobo.smartwallet.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kirobo.smartwallet.Inerfaces.CoinMarketCapAPI;
import com.kirobo.smartwallet.Models.CoinMarketModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

  public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(preferenceName, preferenceValue);
    editor.apply();
  }


  public static void saveToPreferences(Context context, String preferenceName, int preferenceValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(preferenceName, preferenceValue);
    editor.apply();
  }

  public static void saveToPreferences(Context context, String preferenceName, long preferenceValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putLong(preferenceName, preferenceValue);
    editor.apply();
  }

  public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(preferenceName, preferenceValue);
    editor.apply();
  }

  //Amit 10/01
  public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    return sharedPreferences.getString(preferenceName, defaultValue);
  }

  public static int readFromPreferences(Context context, String preferenceName, int defaultValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    return sharedPreferences.getInt(preferenceName, defaultValue);
  }

  public static long readFromPreferences(Context context, String preferenceName, long defaultValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    return sharedPreferences.getLong(preferenceName, defaultValue);
  }

  public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
    SharedPreferences sharedPreferences = context.getSharedPreferences("com.kirobo.smartwallet", Context.MODE_PRIVATE);
    return sharedPreferences.getBoolean(preferenceName, defaultValue);
  }

  /**
   * Convert ethBalance to USD
   */
  public static String ConvertEtherToUSD(String ethBalance, String USDcurrency) {
    BigDecimal usd = (new BigDecimal(USDcurrency).multiply(new BigDecimal(ethBalance)));
    usd = usd.setScale(2, RoundingMode.CEILING);

    return usd.toString();
  }

  public static void HideSoftKeyboard(Activity activity) {

    InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
  }
}
