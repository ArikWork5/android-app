package com.kirobo.smartwallet.Fragmetns;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kirobo.smartwallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_send, container, false);

//    TextView tv = (TextView) view.findViewById(R.id.tvFragFirst);
//    tv.setText(getArguments().getString("msg"));

    return view;
  }

  public static SendFragment newInstance(String text) {

    SendFragment sf = new SendFragment();
    Bundle b = new Bundle();
    b.putString("msg", text);

    sf.setArguments(b);

    return sf;
  }
}
