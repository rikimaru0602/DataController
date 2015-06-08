package com.example.rainbowapps.datacontroller01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

//    public static MenuFragment newInstance(String param1, String param2) {
//        MenuFragment fragment = new MenuFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        final View importButton = rootView.findViewById(R.id.button_import);
        final View exportButton = rootView.findViewById(R.id.button_export);
        final View playButton = rootView.findViewById(R.id.button_play);


        return rootView;
    }

//    @Override
//    public void onClick(View v){
//        final int id=v.getId();
//        if(id ==R.id.button_import){
//            Intent intent = new Intent(this, ImpActivity.class);
//            startActivity(intent);
//        }else if(id ==R.id.button_export){
//            Intent intent = new Intent(this, RegActivity.class);
//            startActivity(intent);
//        }else if(id ==R.id.button_play) {
//            Intent intent = new Intent(this, RegActivity.class);
//            startActivity(intent);
//        }
//    }
}