package com.kirobo.smartwallet.Fragmetns;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kirobo.smartwallet.Activities.MainActivity;
import com.kirobo.smartwallet.R;
import com.kirobo.smartwallet.Utils.Utils;

import static android.widget.ImageView.ScaleType.FIT_XY;

public class MyAddresFragment extends Fragment {

  private View view;
  private ImageView QR_img;
  private TextView address_txt;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     view = inflater.inflate(R.layout.fragment_myaddres, container, false);

     InitViews();

    return view;
  }

  public void InitViews(){
    QR_img = (ImageView) view.findViewById(R.id.qr_img);
    address_txt =(TextView) view.findViewById(R.id.address_myaddress);
    address_txt.setText( MainActivity.accountAddress.toString());
    EncodeToQrCode("" + MainActivity.accountAddress,800,800);
  }

  public static MyAddresFragment newInstance(String text) {

    MyAddresFragment af = new MyAddresFragment();
    Bundle b = new Bundle();
    b.putString("msg", text);

    af.setArguments(b);

    return af;
  }

  /**
   * Create qr scan according your wallet address
   * @param text
   * @param width
   * @param height
   * @return
   */
  public Bitmap EncodeToQrCode(String text, int width, int height){
    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix matrix = null;
    try {
      matrix = writer.encode(text, BarcodeFormat.QR_CODE, 800, 800);
    } catch (WriterException ex) {
      ex.printStackTrace();
    }
    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    for (int x = 0; x < width; x++){
      for (int y = 0; y < height; y++){
        bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
      }
    }

    QR_img.setImageBitmap(bmp);
    QR_img.setScaleType(ImageView.ScaleType.FIT_XY);
    return bmp;
  }

}

