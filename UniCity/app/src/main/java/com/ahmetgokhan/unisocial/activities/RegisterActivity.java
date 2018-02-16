package com.ahmetgokhan.unisocial.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.ahmetgokhan.unisocial.R;
import com.ahmetgokhan.unisocial.overridden.PagerAdapter;


public class RegisterActivity extends FragmentActivity {
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewpager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        viewpager.setCurrentItem(item, smoothScroll);
    }
}
