package com.kirobo.smartwallet.Inerfaces;

import com.kirobo.smartwallet.Models.CoinMarketModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinMarketCapAPI {

    String API_BASE_URL = "https://api.coinmarketcap.com";

    @GET("v1/ticker/ethereum")
    Call<List<CoinMarketModel>> getCoinMarketList();
}
