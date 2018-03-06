package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapter;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gokhankilic on 6.03.2018.
 */

public class AdvertActivity2 extends AppCompatActivity{

    String description,advertName,selectedFaculty,selectedDepartment;
    int numberOfPerson;
    ArrayList<String>faculties;
    ArrayList<String>departments;
    ArrayList<String>courses;
    Spinner facultiesSpinner;
    Spinner departmentsSpinner;
    ListView coursesListView;
    ArrayAdapter<String> dataAdapterForFaculties;
    ArrayAdapter<String> getDataAdapterForDepartments;
    ArrayAdapter<String> dataAdapterForCourses;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert_listcourses);

        Intent intent = getIntent();

        advertName = intent.getStringExtra("advertName");
        description = intent.getStringExtra("description");
        numberOfPerson = intent.getIntExtra("numberOfPerson",0);

        facultiesSpinner = findViewById(R.id.facultiesSpinner);
        departmentsSpinner = findViewById(R.id.departmentsSpinner);
        coursesListView = findViewById(R.id.coursesListview);


        faculties = new ArrayList<>();
        departments = new ArrayList<>();
        courses = new ArrayList<>();

        dataAdapterForFaculties = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item,faculties);
        dataAdapterForFaculties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getDataAdapterForDepartments = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item,departments);
        getDataAdapterForDepartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdapterForCourses = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courses);

        final ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses("Kadir Has Üniversitesi",null,null);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, final retrofit2.Response<ArrayList<UniSocial>> response) {
                System.out.println(response.body().size());

                for (int i = 0; i < response.body().size(); i++) {

                    faculties.add(response.body().get(i).getFaculty());



                }





                facultiesSpinner.setAdapter(dataAdapterForFaculties);

                facultiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedFaculty = parent.getSelectedItem().toString();

                        System.out.println(selectedFaculty);
                        departments.clear();




                        Call<ArrayList<UniSocial>> call1 = apiInterface.getCourses(null,selectedFaculty,null);
                        call1.enqueue(new Callback<ArrayList<UniSocial>>() {

                            @Override
                            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                                for(int i = 0;i<response.body().size(); i++){

                                    departments.add(response.body().get(i).getDepartments());
                                    System.out.println(departments.get(i));


                                }
                                departmentsSpinner.setAdapter(getDataAdapterForDepartments);

                                departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        selectedDepartment = parent.getSelectedItem().toString();

                                        courses.clear();

                                        Call<ArrayList<UniSocial>> call2 = apiInterface.getCourses(null,null,selectedDepartment);
                                        call2.enqueue(new Callback<ArrayList<UniSocial>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                                                for(int i = 0;i<response.body().size(); i++){

                                                    courses.add(response.body().get(i).getCourses());
                                                    System.out.println(courses.get(i));


                                                }

                                                coursesListView.setAdapter(dataAdapterForCourses);


                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });



                            }

                            @Override
                            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });










    }







}
