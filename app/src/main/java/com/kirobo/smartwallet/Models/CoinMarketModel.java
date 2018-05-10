package com.kirobo.smartwallet.Models;

public class CoinMarketModel {

  private String price_usd;

  public CoinMarketModel(String price_usd) {
    this.price_usd = price_usd;
  }

  public String getPrice_usd() {
    return price_usd;
  }
}
