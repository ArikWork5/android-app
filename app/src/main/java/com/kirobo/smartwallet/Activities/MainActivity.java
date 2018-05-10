package com.kirobo.smartwallet.Activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kirobo.smartwallet.Fragmetns.AccountFragment;
import com.kirobo.smartwallet.Fragmetns.MyAddresFragment;
import com.kirobo.smartwallet.Fragmetns.SendFragment;
import com.kirobo.smartwallet.Fragmetns.TranssactionFragment;
import com.kirobo.smartwallet.MyApplication;
import com.kirobo.smartwallet.R;
import com.kirobo.smartwallet.Utils.Utils;
import com.kirobo.smartwallet.Utils.Wallet;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.Future;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

  private ViewPager pager;
  private RelativeLayout transactions_lay,send_lay, my_addres_lay,account_lay;
  private ImageView transactions_img,send_img,my_addres_img,account_img;
  private TextView transactions_txt,send_txt,my_addres_txt,usd_txt,eth_txt,account_txt;
  private Web3j web3;
  private Wallet wallet;
  private MyApplication appstate;

  public static  String accountAddress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    InitView();

  }

  public void InitView(){

    wallet = new Wallet();
    web3 = Web3jFactory.build(new HttpService("https://rinkeby.infura.io/Mi3WQKlqLIU6IQtAvddB", false));
    wallet.GetUSDcurrencyOfToday(this);

    accountAddress =  Utils.readFromPreferences(this,"walletAddress","-1");

//    web3 = Web3jFactory.build(new Web3jService() {
//                                @Override
//                                public <T extends Response> T send(Request request, Class<T> responseType) throws IOException {
//                                  return null;
//                                }
//
//                                @Override
//                                public <T extends Response> Future<T> sendAsync(Request request, Class<T> responseType) {
//                                  return null;
//                                }
//                              }


    pager = (ViewPager) findViewById(R.id.vp_container);
    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

    transactions_lay = (RelativeLayout)findViewById(R.id.transactions_lay);
    send_lay = (RelativeLayout)findViewById(R.id.send_lay);
    my_addres_lay = (RelativeLayout)findViewById(R.id.my_address_lay);
    account_lay = (RelativeLayout) findViewById(R.id.account_lay);

    transactions_img = (ImageView) findViewById(R.id.transactions_img);
    send_img = (ImageView) findViewById(R.id.send_img);
    my_addres_img = (ImageView) findViewById(R.id.my_addres_img);
    account_img = (ImageView) findViewById(R.id.account_img);

    transactions_txt = (TextView) findViewById(R.id.transactions_txt);
    send_txt = (TextView) findViewById(R.id.send_txt);
    my_addres_txt = (TextView) findViewById(R.id.my_addres_txt);
    account_txt = (TextView) findViewById(R.id.account_txt);

    usd_txt = (TextView) findViewById(R.id.usd_txt);
    eth_txt = (TextView) findViewById(R.id.eth_txt);

    appstate = (MyApplication)this.getApplicationContext();
    appstate.CloseProgressBar();

   GetCurrentBalance();

    transactions_lay.setOnClickListener(this);
    send_lay.setOnClickListener(this);
    my_addres_lay.setOnClickListener(this);
    account_lay.setOnClickListener(this);

    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
        switch(position){

          case 0:
            SetTransactionsProperties();
            break;
          case 1:
            SetSendProperties();
            break;
          case 2:
            SetAddresProperties();
            break;
          case 3:
            SetAccountProperties();
            break;
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }

    });

  }

  @Override
  public void onClick(View v) {

    switch(v.getId()){

      case R.id.transactions_lay:
        pager.setCurrentItem(0, true);
         SetTransactionsProperties();
        break;

      case R.id.send_lay:
        pager.setCurrentItem(1, true);
        SetSendProperties();
        break;

      case R.id.my_address_lay:
        pager.setCurrentItem(2, true);
        SetAddresProperties();
        break;

      case R.id.account_lay:
        pager.setCurrentItem(3, true);
        SetAccountProperties();
        break;
    }
  }

  public void GetCurrentBalance(){
//1) Get Current Balance in eth;
        String currentEtaBalance = wallet.GetEthBalance(web3);
          eth_txt.setText(currentEtaBalance + " ETH");
//2) Get Current UD currency
    String updatedUSD = Utils.readFromPreferences(this,"currUSD","NA");
    if(updatedUSD != "NA")
      usd_txt.setText(Utils.ConvertEtherToUSD("23",updatedUSD) + " $");
  }

//  public void SetUSD(String updatedUSD){
////    String updatedUSD = Utils.readFromPreferences(this,"currUSD","NA");
//    if(updatedUSD != "NA")
//      usd_txt.setText(Utils.ConvertEtherToUSD("23",updatedUSD) + " $");
//  }

  public void SetTransactionsProperties(){
    transactions_img.setImageResource(R.drawable.transactions_y);
    send_img.setImageResource(R.drawable.send_n);
    my_addres_img.setImageResource(R.drawable.addres_n);
    account_img.setImageResource(R.drawable.account_n);

    transactions_txt.setTextColor(Color.parseColor("#7b06c4"));
    send_txt.setTextColor(Color.parseColor("#535252"));
    my_addres_txt.setTextColor(Color.parseColor("#535252"));
    account_txt.setTextColor(Color.parseColor("#535252"));
  }

  public void SetSendProperties(){
    send_img.setImageResource(R.drawable.send_y);
    my_addres_img.setImageResource(R.drawable.addres_n);
    transactions_img.setImageResource(R.drawable.transactions_n);
    account_img.setImageResource(R.drawable.account_n);

    transactions_txt.setTextColor(Color.parseColor("#535252"));
    send_txt.setTextColor(Color.parseColor("#7b06c4"));
    my_addres_txt.setTextColor(Color.parseColor("#535252"));
    account_txt.setTextColor(Color.parseColor("#535252"));
  }

  public void SetAddresProperties(){
    my_addres_img.setImageResource(R.drawable.addres_y);
    transactions_img.setImageResource(R.drawable.transactions_n);
    send_img.setImageResource(R.drawable.send_n);
    account_img.setImageResource(R.drawable.account_n);

    transactions_txt.setTextColor(Color.parseColor("#535252"));
    send_txt.setTextColor(Color.parseColor("#535252"));
    my_addres_txt.setTextColor(Color.parseColor("#7b06c4"));
    account_txt.setTextColor(Color.parseColor("#535252"));
  }

  public void SetAccountProperties(){
    my_addres_img.setImageResource(R.drawable.addres_n);
    transactions_img.setImageResource(R.drawable.transactions_n);
    send_img.setImageResource(R.drawable.send_n);
    account_img.setImageResource(R.drawable.account_y);

    transactions_txt.setTextColor(Color.parseColor("#535252"));
    send_txt.setTextColor(Color.parseColor("#535252"));
    my_addres_txt.setTextColor(Color.parseColor("#535252"));
    account_txt.setTextColor(Color.parseColor("#7b06c4"));
  }

  private class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
      switch(pos) {
        case 0: return TranssactionFragment.newInstance("FirstFragment, Instance 1");
        case 1: return SendFragment.newInstance("SecondFragment, Instance 2");
        case 2: return MyAddresFragment.newInstance("ThirdFragment, Instance 3");
        case 3: return AccountFragment.newInstance("ForthFragment, Instance 4");
        default: return TranssactionFragment.newInstance("FirstFragment, Default");
      }
    }

    @Override
    public int getCount() {
      return 4;
    }
  }
}
