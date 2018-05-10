package com.kirobo.smartwallet.Fragmetns;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirobo.smartwallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranssactionFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_transsaction, container, false);

//    TextView tv = (TextView) view.findViewById(R.id.tvFragFirst);
//    tv.setText(getArguments().getString("msg"));

    return view;
  }

  public static TranssactionFragment newInstance(String text) {

    TranssactionFragment tf = new TranssactionFragment();
    Bundle b = new Bundle();
    b.putString("msg", text);

    tf.setArguments(b);

    return tf;
  }
}
