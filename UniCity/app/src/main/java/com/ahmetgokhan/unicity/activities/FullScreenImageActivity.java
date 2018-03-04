package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class FullScreenImageActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private ImageView arrowBack;
    private ImageView verTer;
    Uri imageUri;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        arrowBack = findViewById(R.id.arrow_back);
        verTer = findViewById(R.id.photo_menu);
        arrowBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);
        Intent callingActivityIntent = getIntent();


        if (callingActivityIntent != null) {
            imageUri = callingActivityIntent.getData();
            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(fullScreenImageView);
            }
        }


        verTer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(FullScreenImageActivity.this, verTer);
                popupMenu.getMenuInflater().inflate(R.menu.menu_photo, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.savePhoto:
                                fullScreenImageView.setDrawingCacheEnabled(true);
                                fullScreenImageView.buildDrawingCache();
                                bitmap = Bitmap.createBitmap(fullScreenImageView.getDrawingCache());

                                if (galleryAddPic(bitmap)) {
                                    Toast.makeText(FullScreenImageActivity.this, "Fotoğraf Kaydedildi.", Toast.LENGTH_LONG).show();
                                }

                                break;
                            case R.id.sharePhoto:
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private boolean galleryAddPic(Bitmap bitmap) {
        String root = Environment.getExternalStoragePublicDirectory("").toString();
        File myDir = new File(root + "/Pictures/UniCity");
        boolean x = myDir.mkdirs();
        System.err.println(x);
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
