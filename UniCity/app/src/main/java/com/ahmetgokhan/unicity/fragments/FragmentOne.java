package com.ahmetgokhan.unicity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Register.RegisterActivity;
import com.ahmetgokhan.unicity.config.Config;

public class FragmentOne extends Fragment {
    EditText registerName;
    EditText registerSurname;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_layout, container, false);

        registerName = view.findViewById(R.id.textRegisterName);
        registerSurname = view.findViewById(R.id.textRegisterSurname);


        sharedPreferences = this.getActivity().getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE);

        ProgressBar progressBar = view.findViewById(R.id.progressBar0);

        ImageView arrowRight = view.findViewById(R.id.arrowRight);
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registerName.getText().toString().trim().equals("") && !registerSurname.getText().toString().trim().equals("")) {
                    editor = sharedPreferences.edit();

                    String name = registerName.getText().toString().trim();
                    String surname = registerSurname.getText().toString().trim();

                    editor.putString(Config.NAME, name);
                    editor.putString(Config.SURNAME, surname);
                    editor.apply();

                    ((RegisterActivity) getActivity()).setCurrentItem(1, true);
                }else{
                    Toast.makeText(getContext(),"Please fill the spaces with your Name and Surname!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        progressBar.setScaleY(2f);
        progressBar.setMax(4);
        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(1);



        return view;
    }
}
