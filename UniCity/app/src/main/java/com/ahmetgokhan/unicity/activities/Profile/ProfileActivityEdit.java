package com.ahmetgokhan.unicity.activities.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityEdit extends AppCompatActivity {

    EditText numberPickerFaculty;
    EditText numberPickerDepartment;
    EditText updateName;
    EditText updateSurname;
    ArrayList<String> faculties = new ArrayList<>();
    ArrayList<String> departments = new ArrayList<>();
    NumberPicker facultyPicker;
    NumberPicker deparmentPicker;
    int facultyValue;
    int departmentValue;
    LinearLayout linearLayout;
    String facultySelected;
    String departmentSelected;
    ImageView cover_photo;
    ImageView profile_photo;
    ImageView click_to_upload_cover_photo;
    String coverPhotoUrl;
    String profilePhotoUrl;
    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    String selectedPath1 = "NONE";
    String selectedPath2 = "NONE";
    Button complete;
    private static final int STORAGE_PERMISSION_CODE = 123;
    CircleImageView circleImageView_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        requestStoragePermission();
        complete = findViewById(R.id.button_edit_profile_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        circleImageView_edit = findViewById(R.id.circleImageView_edit_camera);
        circleImageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE2);
            }
        });
        click_to_upload_cover_photo = findViewById(R.id.click_to_upload_cover_photo);
        click_to_upload_cover_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE1);
            }
        });






        updateSurname = findViewById(R.id.update_profile_surname);
        cover_photo = findViewById(R.id.cover_photo_edit);
        profile_photo = findViewById(R.id.circleImageView_edit);
        updateName = findViewById(R.id.update_profile_name);
        numberPickerFaculty = findViewById(R.id.faculty_profile_edit);
        numberPickerFaculty.setText("");
        numberPickerFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<ArrayList<UniSocial>> call = apiInterface.getFaculty("Kadir Has Üniversitesi");
                call.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                        for (int i = 0; i < response.body().size(); i++) {

                            faculties.add(response.body().get(i).getFaculty());
                        }

                        onCreateDialog();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });


        numberPickerDepartment = findViewById(R.id.department_profile_edit);
        numberPickerFaculty.setText("");
        numberPickerDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<ArrayList<UniSocial>> call = apiInterface.getCourses(null, facultySelected, null);
                call.enqueue(new Callback<ArrayList<UniSocial>>() {

                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                        for (int i = 0; i < response.body().size(); i++) {

                            departments.add(response.body().get(i).getDepartments());
                        }

                        onCreateDialogDepartment();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });


        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> call = apiInterface.getProfile(getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN, ""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                updateName.setText(response.body().getName());
                updateSurname.setText(response.body().getSurname());
                //numberPickerDepartment.setText(response.body().getDepartment());
                //profile_working_adverts.setText(response.body().getNumber_adverts());
                //profile_subscribed_courses.setText(response.body().getNumber_subs());

                coverPhotoUrl = Config.BASE_URL + response.body().getCover_photo();
                profilePhotoUrl = Config.BASE_URL + response.body().getProfile_photo();

                AsyncTask<String, Void, Bitmap> coverTask = new ProfileActivityEdit.BitmapTask().execute(response.body().getCover_photo());
                AsyncTask<String, Void, Bitmap> profileTask = new ProfileActivityEdit.BitmapTask().execute(response.body().getProfile_photo());


                try {
                    cover_photo.setImageBitmap(coverTask.get());
                    profile_photo.setImageBitmap(profileTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });


    }

    protected String encoder(String x){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 1;
        options.inPurgeable = true;
        Bitmap bm = BitmapFactory.decodeFile(x,options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] byteImage_photo = baos.toByteArray();

        return Base64.encodeToString(byteImage_photo,Base64.DEFAULT);
    }
    protected void upload() {
        String cover = "null";
        String profile = "null";
        if(selectedPath1.equals("NONE") && !selectedPath2.equals("NONE")) {
            profile = encoder(selectedPath2);
        }else if(!selectedPath1.equals("NONE") && selectedPath2.equals("NONE")){
            cover = encoder(selectedPath1);
        }else if(selectedPath1.equals("NONE") && selectedPath2.equals("NONE")){
            System.out.println("sorry");
        }else{
            cover = encoder(selectedPath1);
            profile = encoder(selectedPath2);
        }
        if (numberPickerFaculty.getText().toString().equals("") || numberPickerFaculty.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "You need to fill the Faculty and Department", Toast.LENGTH_LONG).show();
        } else {
            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<UniSocial> call = apiInterface.updateProfile(updateName.getText().toString(), updateSurname.getText().toString(), numberPickerDepartment.getText().toString(), profile, cover, getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN, ""));
            call.enqueue(new Callback<UniSocial>() {

                @Override
                public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                    if (response.body().getMessage().equals("true")) {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.for_snackbar);
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Your profile succesfully updated.", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<UniSocial> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    public Dialog onCreateDialog() {


        facultyPicker = new NumberPicker(getApplicationContext());


        final String[] universities = faculties.toArray(new String[faculties.size()]);
        System.out.println(Arrays.toString(faculties.toArray(new String[faculties.size()])));
        facultyPicker.setMaxValue(faculties.size() - 1);
        facultyPicker.setDisplayedValues(universities);
        facultyPicker.setMinValue(0);


        facultyPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(facultyPicker, Color.WHITE);
        facultyPicker.setValue(facultyValue);

        linearLayout = new LinearLayout(getApplicationContext());

        linearLayout.addView(facultyPicker);
        linearLayout.setGravity(Gravity.CENTER);


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivityEdit.this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setView(linearLayout);
        builder.setMessage("University :");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                facultyValue = facultyPicker.getValue();
                facultySelected = universities[facultyPicker.getValue()];
                numberPickerFaculty.setText(facultySelected);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Please select your university, If selected, you can complete your registration", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        return builder.show();
    }

    public Dialog onCreateDialogDepartment() {


        deparmentPicker = new NumberPicker(getApplicationContext());


        final String[] universities = departments.toArray(new String[departments.size()]);
        System.out.println(Arrays.toString(departments.toArray(new String[departments.size()])));
        deparmentPicker.setMaxValue(departments.size() - 1);
        deparmentPicker.setDisplayedValues(universities);
        deparmentPicker.setMinValue(0);


        deparmentPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(deparmentPicker, Color.WHITE);
        deparmentPicker.setValue(departmentValue);

        linearLayout = new LinearLayout(getApplicationContext());

        linearLayout.addView(deparmentPicker);
        linearLayout.setGravity(Gravity.CENTER);


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivityEdit.this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setView(linearLayout);
        builder.setMessage("University :");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                departmentValue = deparmentPicker.getValue();
                departmentSelected = universities[deparmentPicker.getValue()];
                numberPickerDepartment.setText(departmentSelected);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Please select your university, If selected, you can complete your registration", Toast.LENGTH_SHORT).show();
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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.w("setNumberPickerTextCol", e);
                } catch (IllegalAccessException e) {
                    Log.w("setNumberPickerTextCol", e);
                } catch (IllegalArgumentException e) {
                    Log.w("setNumberPickerTextCol", e);
                }
            }
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private class BitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream(new URL(Config.BASE_URL + strings[0]).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    public void openGallery(int req_code){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), req_code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == SELECT_FILE1)
            {
                selectedPath1 = getPath(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    cover_photo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("selectedPath1 : " + selectedPath1);
            }
            if (requestCode == SELECT_FILE2)
            {
                selectedPath2 = getPath(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    profile_photo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("selectedPath2 : " + selectedPath2);
            }

        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
