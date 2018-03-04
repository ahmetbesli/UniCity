package com.ahmetgokhan.unicity.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.LoginActivity;
import com.ahmetgokhan.unicity.activities.RegisterActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.lang.reflect.Field;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;


public class FragmentThree extends Fragment implements View.OnClickListener{

    String universitySelected;

    int universityValue;

    EditText registerUniversity;

    ArrayList<String> universityArray = new ArrayList<>();

    NumberPicker universityPicker;

    SharedPreferences sharedPreferences;

    LinearLayout linearLayout;

    //Oncreate
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three_layout, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE);

        registerUniversity = view.findViewById(R.id.registerUniversity);

        registerUniversity.setOnClickListener(this);

        ProgressBar progressBar = view.findViewById(R.id.progressBar3);
        ImageView arrowLeft = view.findViewById(R.id.arrowLeft3);
        ImageView arrowFinish = view.findViewById(R.id.arrowRight3);
        arrowLeft.setOnClickListener(this);
        arrowFinish.setOnClickListener(this);

        progressBar.setScaleY(2f);
        progressBar.setMax(4);
        progressBar.getProgressDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(3);


        return view;
    }

    public Dialog onCreateDialog() {


        universityPicker = new NumberPicker(getContext());


        final String[] universities = universityArray.toArray(new String[universityArray.size()]);

        universityPicker.setDisplayedValues(universities);
        universityPicker.setMinValue(0);
        universityPicker.setMaxValue(176);

        universityPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(universityPicker, Color.WHITE);
        universityPicker.setValue(universityValue);

        linearLayout = new LinearLayout(getContext());

        linearLayout.addView(universityPicker);
        linearLayout.setGravity(Gravity.CENTER);




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
        builder.setView(linearLayout);
        builder.setMessage("University :");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                universityValue = universityPicker.getValue();
                universitySelected = universities[universityPicker.getValue()];
                registerUniversity.setText(universitySelected);



            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getContext(),"Please select your university, If selected, you can complete your registration",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        return builder.show();
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(numberPicker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextCol", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextCol", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextCol", e);
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerUniversity:
                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<ArrayList<UniSocial>> call = apiInterface.getUniversities();
                call.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                      for(int i = 0; i < response.body().size(); i++) {

                            universityArray.add(response.body().get(i).getUniversities());
                        }
                        onCreateDialog();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                    }

                });
                break;
            case R.id.arrowLeft3:
                ((RegisterActivity)getActivity()).setCurrentItem (1, true);
                break;
            case R.id.arrowRight3:
                if (!registerUniversity.getText().toString().trim().equals("")) {

                    String university = registerUniversity.getText().toString().trim();

                    ApiInterface apiInterface3 = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<UniSocial> call3 = apiInterface3.register(sharedPreferences.getString(Config.NAME,"NAME"),
                            sharedPreferences.getString(Config.SURNAME,"NAME"),
                            sharedPreferences.getString(Config.EMAIL,"NAME"),
                            sharedPreferences.getString(Config.PASSWORD,"NAME"),
                            university);
                    call3.enqueue(new Callback<UniSocial>() {

                        @Override
                        public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                            if(response.body().getMessage().equals("success")){
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UniSocial> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                        }

                    });


                }else{
                    Toast.makeText(getContext(),"Please fill the spaces with your Name and Surname!",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
