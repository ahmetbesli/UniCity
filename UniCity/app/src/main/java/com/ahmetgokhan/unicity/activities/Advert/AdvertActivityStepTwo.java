package com.ahmetgokhan.unicity.activities.Advert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Profile.ProfileActivity;
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

public class AdvertActivityStepTwo extends AppCompatActivity {

    String description, advertName, selectedFaculty, selectedDepartment, courseName;
    Button createAdvertButton;
    TextView selectedCourseText;
    int numberOfPerson;
    ArrayList<String> faculties;
    ArrayList<String> departments;
    ArrayList<String> courses;
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
        numberOfPerson = intent.getIntExtra("numberOfPerson", 0);

        facultiesSpinner = findViewById(R.id.facultiesSpinner);
        departmentsSpinner = findViewById(R.id.departmentsSpinner);
        coursesListView = findViewById(R.id.coursesListview);
        selectedCourseText = findViewById(R.id.selectedCourseTextView);
        createAdvertButton = findViewById(R.id.createAdvertButton);


        faculties = new ArrayList<>();
        departments = new ArrayList<>();
        courses = new ArrayList<>();

        dataAdapterForFaculties = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, faculties);
        dataAdapterForFaculties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getDataAdapterForDepartments = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        getDataAdapterForDepartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdapterForCourses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);

        final ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses("Kadir Has Üniversitesi", null, null);
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
                        departments.clear();

                        Call<ArrayList<UniSocial>> call1 = apiInterface.getCourses(null, selectedFaculty, null);
                        call1.enqueue(new Callback<ArrayList<UniSocial>>() {

                            @Override
                            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                                for (int i = 0; i < response.body().size(); i++) {

                                    departments.add(response.body().get(i).getDepartments());


                                }

                                departmentsSpinner.setAdapter(getDataAdapterForDepartments);
                                departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        selectedDepartment = parent.getSelectedItem().toString();


                                        courses.clear();

                                        Call<ArrayList<UniSocial>> call2 = apiInterface.getCourses(null, null, selectedDepartment);
                                        call2.enqueue(new Callback<ArrayList<UniSocial>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                                                for (int i = 0; i < response.body().size(); i++) {

                                                    courses.add(response.body().get(i).getCourses());


                                                }

                                                coursesListView.setAdapter(dataAdapterForCourses);
                                                coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                        courseName = parent.getItemAtPosition(position).toString();
                                                        selectedCourseText.setText(parent.getItemAtPosition(position).toString());

                                                        createAdvertButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {


                                                                Call<UniSocial> call3 = apiInterface.createAdvert(advertName, description, numberOfPerson, courseName);
                                                                call3.enqueue(new Callback<UniSocial>() {
                                                                    @Override
                                                                    public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                                                                        if (response.body().getMessage().equals("success")) {
                                                                            Toast.makeText(AdvertActivityStepTwo.this, "Advert was created successfully", Toast.LENGTH_SHORT).show();
                                                                            Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                                                                            startActivity(intent1);
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Advert was not created ", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<UniSocial> call, Throwable t) {

                                                                    }
                                                                });
                                                            }


                                                        });

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


        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdvertActivityStepTwo.this, "Please select a course", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
