package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLogin;
    EditText editTextRegister;
    Button buttonLogin;
    String emailText;
    String passwordText;
    TextView noAccount;
    TextView createAdvert;
    Character[] bannedChars = {'[', ']', ':', ';', '|', '=', '+', '?', '<', '>', '*', '\'', '[', ']', '|', '=', '+',  '*', '\\', '"'};
    Character[] bannedNumbers = {'1','2','3','4','5','6','7','8','9','0'};
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
    }

    private void setupUI() {
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextRegister = findViewById(R.id.editTextRegister);
        buttonLogin = findViewById(R.id.button_login);
        noAccount = findViewById(R.id.text_no_account);
        createAdvert = findViewById(R.id.createAdvert);
        createAdvert.setOnClickListener(this);
        noAccount.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

    }

    private void attemptLogin() {
        emailText = editTextLogin.getText().toString().trim();
        passwordText = editTextRegister.getText().toString().trim();
        if (isEmailValid(emailText) && isPasswordValid(passwordText)) {
            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<UniSocial> call = apiInterface.login(emailText, passwordText);
            call.enqueue(new Callback<UniSocial>() {

                @Override
                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                    if(response.body().getMessage().equals("true")){
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        editor = getSharedPreferences(Config.APP_NAME, MODE_PRIVATE).edit();
                        editor.putString(Config.TOKEN,response.body().getToken());
                        editor.apply();
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Email or Password is not correct!",Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<UniSocial> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private boolean isEmailValid(String email){

        if(email.length() < 5){
            Toast.makeText(getApplicationContext(),"Email length must be longer than 5!",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!email.contains("@")){
            Toast.makeText(getApplicationContext(),"Email must contain '@' character!",Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.indexOf("@") == email.length() - 1){
            Toast.makeText(getApplicationContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
        }else if(email.contains("@")){
            int x = email.indexOf("@");
            String y = email.substring(x,email.length());
            if(!y.contains(".")){
                Toast.makeText(getApplicationContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
                return false;
            }else if(y.indexOf(".") == y.length() - 1){
                Toast.makeText(getApplicationContext(),"Please write your EMAIL in regular type: '*******@***.com / .de / .com.tr'",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"Email cannot have just numbers before '@'",Toast.LENGTH_SHORT).show();
            return false;
        }

        for(Character i : bannedChars){
            if(email.contains(i.toString())){
                Toast.makeText(getApplicationContext(),"Email cannot contain not allowed character(s) \n * ( ) . & - _ [ ] ` ~ | @ $ % ^ & ? : | ",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean isPasswordValid(String password){

        if(password.length() < 5){
            Toast.makeText(getApplicationContext(),"Password length must be longer than 5!",Toast.LENGTH_SHORT).show();
            return false;
        }

        for(Character i : bannedChars){
            if(password.contains(i.toString())){
                Toast.makeText(getApplicationContext(),"Password cannot contain not allowed character(s) \n * ( ) . & - _ [ ] ` ~ | @ $ % ^ & ? : | ",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                attemptLogin();
                break;
            case R.id.text_no_account:
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.createAdvert:
                Intent intq = new Intent(getApplicationContext(),AdvertActivity.class);
                startActivity(intq);
                break;
            default:
                break;
        }
    }
}




