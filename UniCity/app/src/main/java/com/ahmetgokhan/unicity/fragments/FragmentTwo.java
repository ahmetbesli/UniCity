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
import com.ahmetgokhan.unicity.activities.RegisterActivity;
import com.ahmetgokhan.unicity.config.Config;


public class FragmentTwo extends Fragment implements View.OnClickListener{

    EditText registerEmail;
    EditText registerPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Character[] bannedChars = {'[', ']', ':', ';', '|', '=', '+', '?', '<', '>', '*', '\'', '[', ']', '|', '=', '+',  '*', '\\', '"'};
    Character[] bannedNumbers = {'1','2','3','4','5','6','7','8','9','0'};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two_layout,container,false);

        registerEmail = view.findViewById(R.id.textRegisterEmail);
        registerPassword = view.findViewById(R.id.textRegisterPassword);
        sharedPreferences = this.getActivity().getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE);

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
                if(isEmailValid(registerEmail.getText().toString().trim()) && isPasswordValid(registerPassword.getText().toString().trim())) {
                    editor = sharedPreferences.edit();

                    String email = registerEmail.getText().toString().trim();
                    String password = registerPassword.getText().toString().trim();

                    editor.putString(Config.EMAIL, email);
                    editor.putString(Config.PASSWORD, password);
                    editor.apply();

                    ((RegisterActivity) getActivity()).setCurrentItem(2, true);
                }else{
                    Toast.makeText(getContext(),"Please fill the spaces with Email and Password!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.arrowLeft2:
                ((RegisterActivity)getActivity()).setCurrentItem (0, true);
                break;
            default:
                break;
        }
    }

    private boolean isEmailValid(String email){

        if(email.length() < 5){
            Toast.makeText(getContext(),"Email length must be longer than 5!",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!email.contains("@")){
            Toast.makeText(getContext(),"Email must contain '@' character!",Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.indexOf("@") == email.length() - 1){
            Toast.makeText(getContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
        }else if(email.contains("@")){
            int x = email.indexOf("@");
            String y = email.substring(x,email.length());
            if(!y.contains(".")){
                Toast.makeText(getContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
                return false;
            }else if(y.indexOf(".") == y.length() - 1){
                Toast.makeText(getContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        int counterEmail = 0;
        String sub = email.substring(0,email.indexOf("@"));
        for(int i = 0; i < sub.length(); i++) {
            for (Character bannedNumber : bannedNumbers) {
                if (sub.charAt(i) == bannedNumber) {
                    counterEmail += 1;
                }
            }
        }

        if(counterEmail == sub.length()){
            Toast.makeText(getContext(),"Email cannot have just numbers before '@'",Toast.LENGTH_SHORT).show();
            return false;
        }

        for(Character i : bannedChars){
            if(email.contains(i.toString())){
                Toast.makeText(getContext(),"Email cannot contain not allowed character(s) \n * ( ) . & - _ [ ] ` ~ | @ $ % ^ & ? : | ",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean isPasswordValid(String password){

        if(password.length() < 5){
            Toast.makeText(getContext(),"Password length must be longer than 5!",Toast.LENGTH_SHORT).show();
            return false;
        }

        for(Character i : bannedChars){
            if(password.contains(i.toString())){
                Toast.makeText(getContext(),"Password cannot contain not allowed character(s) \n * ( ) . & - _ [ ] ` ~ | @ $ % ^ & ? : | ",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}
