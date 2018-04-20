package com.ahmetgokhan.unicity.overridden;

import android.widget.Toast;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import retrofit2.Call;
import retrofit2.Callback;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String TAG = "Registration";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> callToken = apiInterface.saveToken(refreshedToken,getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).getString(Config.TOKEN,""));
        callToken.enqueue(new Callback<UniSocial>() {

            @Override
            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                if(response.body().getMessage().equals("true")){
                    System.out.println("Token Saved");
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
