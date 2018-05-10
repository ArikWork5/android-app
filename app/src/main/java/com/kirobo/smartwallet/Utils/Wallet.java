package com.kirobo.smartwallet.Utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.kirobo.smartwallet.Activities.MainActivity;
import com.kirobo.smartwallet.Inerfaces.CoinMarketCapAPI;
import com.kirobo.smartwallet.Models.CoinMarketModel;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.web3j.crypto.Keys.PRIVATE_KEY_LENGTH_IN_HEX;

public class Wallet {
//  private Web3j web3;
  private Web3ClientVersion web3ClientVersion;
  public static Credentials credentials;
  private Thread thread;
  private Context mContext;

  public void BuildWeb3Client(Context loginActivity) {

    mContext = loginActivity;
//    web3 = Web3jFactory.build(new HttpService("https://rinkeby.infura.io/Mi3WQKlqLIU6IQtAvddB", false));
//
//    thread = new Thread() {
//      @Override
//      public void run() {
//        try {
//
//          web3.web3ClientVersion().observable().subscribe(x -> {
//            String clientVersion = x.getWeb3ClientVersion();
////                  Log.d("aaaa", "3: " + clientVersion);
//
//            //CreateNewWallet("password");
//            //ImportExistingWallet("8f7b0257f82d48fcd61935a7fb6876a9cbc405d809f4b6502916b6b54943d126", "password");
//            GetEthBalance();
//            GetUSDcurrencyOfToday();
////          GetUSDcurrencyOfToday();
////
//          });
//
////          web3ClientVersion = web3.web3ClientVersion().send();
////          String clientVersion = web3ClientVersion.getWeb3ClientVersion();
////          Log.d("TESTTT", "3: " + clientVersion);
//
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      }
//    };
//
//    thread.start();
  }

  public void CreateNewWallet(final String password) {
    String fileName;

    try {
      File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
      if (!path.exists()) {
        path.mkdir();
      }

      fileName = WalletUtils.generateLightNewWalletFile(password, new File(String.valueOf(path)));
      Log.d("aaaa", "Wallet File: " + path + "/" + fileName);

      //Enter the
      credentials = WalletUtils.loadCredentials(
        password,
        path + "/" + fileName);

      Log.d("aaaa", "Wallet Address: " + credentials.getAddress());
      Log.d("aaaa", "Wallet GetPublicKey: " + credentials.getEcKeyPair().getPublicKey());
      Log.d("aaaa", "Wallet GetPrivateKey: " + credentials.getEcKeyPair().getPrivateKey());

//      SendTransaction();

    } catch (NoSuchAlgorithmException
      | NoSuchProviderException
      | InvalidAlgorithmParameterException
      | IOException
      | CipherException e) {
      Log.d("aaaa", "ERRRORRR: " + e);

      e.printStackTrace();
    }
  }

  /**
   * Import existing wallet by privateKey
   * @param privateKey
   * @param password
   */
  public boolean ImportExistingWallet(Context mContext, String privateKey, String password) {

    //If path doesn't exist - create
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    if (!path.exists()) {
      path.mkdir();
    }

    credentials = Credentials.create(privateKey);

    try {
    //Enter the wallet
       Log.d("aaaa", "Wallet Address: " + credentials.getAddress());

       Utils.saveToPreferences(mContext,"walletAddress",credentials.getAddress());

       Log.d("aaaa", "Wallet GetPublicKey: " + credentials.getEcKeyPair().getPublicKey());
       Log.d("aaaa", "Wallet GetPrivateKey: " + credentials.getEcKeyPair().getPrivateKey());

      String walletFileName = WalletUtils.generateWalletFile(password, credentials.getEcKeyPair(), path, false);
      Log.d("kirobo", "" + walletFileName + " successfully created in download directory!");
     //SendTransaction();
      return true;

    } catch (CipherException | IOException e) {

      Log.d("kirobo", "Error with importing wallet: " + e);
      return false;
    }
  }

  public void SendTransaction(Web3j web3){

    try {
      TransactionReceipt transferReceipt = Transfer.sendFunds(
        web3, credentials,
        "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
        BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether // BigDecimal.valueOf(1.0) // Convert.Unit.ETHER)
        .send();

      Log.d("aaaa", "Transaction complete, view it at https://rinkeby.etherscan.io/tx/" + transferReceipt.getTransactionHash());
      Log.d("aaaa", "Status" + transferReceipt.getStatus());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get current Ether Balance
   */
  public String GetEthBalance(Web3j web3) {

    EthGetBalance ethGetBalance = null;
    try {
      ethGetBalance = web3
        .ethGetBalance("" + credentials.getAddress(), DefaultBlockParameterName.LATEST)
        .sendAsync()
        .get();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    BigInteger wei = ethGetBalance.getBalance();
    String convert = String.valueOf(wei);

    BigDecimal ethBalance = Convert.fromWei(convert, Convert.Unit.ETHER);
    ethBalance = ethBalance.setScale(3, RoundingMode.CEILING); //Show only 2 digits toRightOf Decimal point

    return ethBalance.toString();
  }

  /**
   * Get current USD currency (1 Ether = 730.859$)
   */
  public void GetUSDcurrencyOfToday(Context loginActivity) {
    Retrofit retrofit;
    mContext = loginActivity;
    retrofit = new Retrofit.Builder()
      .baseUrl(CoinMarketCapAPI.API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    CoinMarketCapAPI api = retrofit.create(CoinMarketCapAPI.class);
    Call<List<CoinMarketModel>> call = api.getCoinMarketList();

    call.enqueue(new Callback<List<CoinMarketModel>>() {
      @Override
      public void onResponse(Call<List<CoinMarketModel>> call, Response<List<CoinMarketModel>> response) {

        List<CoinMarketModel> list = response.body();

        String currentUSD = list.get(0).getPrice_usd();
        //1) Save the last USD currency
        Utils.saveToPreferences(mContext,"currUSD",""+currentUSD);
        //2) Show To The U1
//        MainActivity m1 = new MainActivity();
//        m1.SetUSD(currentUSD);
      }

      @Override
      public void onFailure(Call<List<CoinMarketModel>> call, Throwable t) {
        Log.d("ttttt", "onResponse: " + t.getMessage());

        Toast.makeText(mContext, "Problem to retrieve USD Currency:  " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });

  }

}

