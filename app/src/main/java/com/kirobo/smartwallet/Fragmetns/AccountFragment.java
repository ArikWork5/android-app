package com.kirobo.smartwallet.Fragmetns;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirobo.smartwallet.MyApplication;
import com.kirobo.smartwallet.R;

public class AccountFragment extends Fragment  implements View.OnClickListener{

   private TextView connect_btn;
   private MyApplication appstate;
   private View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_account, container, false);

    InitView();

    return view;
  }

  public void InitView(){

    appstate = (MyApplication)getActivity().getApplicationContext();
    connect_btn = (TextView) view.findViewById(R.id.connect_btn);
    connect_btn.setOnClickListener(this);

  }

  public static AccountFragment newInstance(String text) {

    AccountFragment tf = new AccountFragment();
    Bundle b = new Bundle();
    b.putString("msg", text);
    tf.setArguments(b);

    return tf;
  }

  @Override
  public void onClick(View v) {
    appstate.OpenProgressBar(getActivity());


  }
}
