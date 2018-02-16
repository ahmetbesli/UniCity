package com.ahmetgokhan.unisocial.fragments;

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
import com.ahmetgokhan.unisocial.R;
import com.ahmetgokhan.unisocial.activities.RegisterActivity;
import com.ahmetgokhan.unisocial.config.Config;


public class FragmentTwo extends Fragment implements View.OnClickListener{
    EditText registerEmail;
    EditText registerPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two_layout,container,false);

        registerEmail = view.findViewById(R.id.textRegisterEmail);
        registerPassword = view.findViewById(R.id.textRegisterPassword);
        sharedPreferences = this.getActivity().getSharedPreferences(Config.app_name, Context.MODE_PRIVATE);

        ProgressBar progressBar = view.findViewById(R.id.progressBar2);

        ImageView arrowRight = view.findViewById(R.id.arrowRight2);
        ImageView arrowLeft = view.findViewById(R.id.arrowLeft2);
        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);

        progressBar.setScaleY(2f);
        progressBar.setMax(4);
        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(2);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrowRight2:

                editor = sharedPreferences.edit();

                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();

                editor.putString(Config.email,email);
                editor.putString(Config.password,password);

                editor.apply();

                ((RegisterActivity)getActivity()).setCurrentItem (2, true);

                break;
            case R.id.arrowLeft2:
                ((RegisterActivity)getActivity()).setCurrentItem (0, true);
                break;
            default:
                break;
        }
    }
}
