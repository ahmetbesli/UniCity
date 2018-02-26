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
import com.ahmetgokhan.unicity.activities.ProfileActivity;
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
    String facultySelected;
    String departmentSelected;

    String[] faculties = null;
    String[] departments = null;

    int facultyValue = -1;
    int universityValue;
    int deparmentValue = -1;

    EditText registerUniversity;
    EditText registerFaculty;
    EditText registerDepartment;
    ArrayList<String> universityArray = new ArrayList<>();
    ArrayList<String> facultyArray = new ArrayList<>();
    ArrayList<String> deparmentArray = new ArrayList<>();

    NumberPicker universityPicker;
    NumberPicker facultyPicker;
    NumberPicker departmentPicker;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView finishTick;
    LinearLayout linearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three_layout, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(Config.app_name, Context.MODE_PRIVATE);

        registerUniversity = view.findViewById(R.id.registerUniversity);
        registerFaculty = view.findViewById(R.id.registerFaculty);
        registerDepartment = view.findViewById(R.id.registerDepartment);

        registerUniversity.setOnClickListener(this);
        registerFaculty.setOnClickListener(this);
        registerDepartment.setOnClickListener(this);

        ProgressBar progressBar = view.findViewById(R.id.progressBar3);
        ImageView arrowLeft = view.findViewById(R.id.arrowLeft3);
        ImageView arrowFinish = view.findViewById(R.id.arrowRight3);
        arrowLeft.setOnClickListener(this);
        arrowFinish.setOnClickListener(this);


        progressBar.setScaleY(2f);
        progressBar.setMax(4);
        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(3);


        return view;
    }

    public Dialog onCreateDialogDepartment() {

        departmentPicker = new NumberPicker(getContext());

        if(facultyArray.size() != 0) {
            departments = deparmentArray.toArray(new String[deparmentArray.size()]);

            departmentPicker.setDisplayedValues(departments);

            departmentPicker.setMinValue(0);
            departmentPicker.setMaxValue(departments.length - 1);
        }
        departmentPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(departmentPicker, Color.WHITE);
        departmentPicker.setValue(deparmentValue);

        linearLayout = new LinearLayout(getContext());

        linearLayout.addView(departmentPicker);
        linearLayout.setGravity(Gravity.CENTER);




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
        builder.setView(linearLayout);
        builder.setMessage("Deparment :");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(deparmentArray.size() != 0) {
                    deparmentValue = departmentPicker.getValue();
                    departmentSelected = departments[departmentPicker.getValue()];
                    registerDepartment.setText(departmentSelected);
                }


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
    public Dialog onCreateDialogFaculty() {


        facultyPicker = new NumberPicker(getContext());

        if(facultyArray.size() != 0) {
            faculties = facultyArray.toArray(new String[facultyArray.size()]);

            facultyPicker.setDisplayedValues(faculties);

            facultyPicker.setMinValue(0);
            facultyPicker.setMaxValue(faculties.length - 1);
        }
        facultyPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(facultyPicker, Color.WHITE);
        facultyPicker.setValue(facultyValue);

        linearLayout = new LinearLayout(getContext());

        linearLayout.addView(facultyPicker);
        linearLayout.setGravity(Gravity.CENTER);




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
        builder.setView(linearLayout);
        builder.setMessage("Faculty :");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(facultyArray.size() != 0) {
                    facultyValue = facultyPicker.getValue();
                    facultySelected = faculties[facultyPicker.getValue()];
                    registerFaculty.setText(facultySelected);
                }


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
            case R.id.registerFaculty:
                ApiInterface apiInterface0 = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<ArrayList<UniSocial>> call0 = apiInterface0.getFaculty(universitySelected);
                call0.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                        for(int i = 0; i < response.body().size(); i++) {
                            facultyArray.add(response.body().get(i).getFaculty());
                        }
                        onCreateDialogFaculty();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                    }

                });
                break;
            case R.id.registerDepartment:
                ApiInterface apiInterface2 = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<ArrayList<UniSocial>> call2 = apiInterface2.getDeparment(facultySelected,universitySelected);
                call2.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                        for(int i = 0; i < response.body().size(); i++) {
                            deparmentArray.add(response.body().get(i).getDepartment());
                        }
                        onCreateDialogDepartment();
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
                if (!registerUniversity.getText().toString().trim().equals("") && !registerFaculty.getText().toString().trim().equals("") && !registerDepartment.getText().toString().trim().equals("")) {
                    editor = sharedPreferences.edit();

                    String university = registerUniversity.getText().toString().trim();
                    String faculty = registerFaculty.getText().toString().trim();
                    String department = registerDepartment.getText().toString().trim();
                    editor.putString(Config.university, university);
                    editor.putString(Config.faculty, faculty);
                    editor.putString(Config.department, department);
                    editor.apply();

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(),"Please fill the spaces with your Name and Surname!",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
