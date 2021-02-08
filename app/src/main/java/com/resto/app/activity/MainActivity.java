package com.resto.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.resto.app.R;

public class MainActivity extends AppCompatActivity {
    Animation animFadeIn, animFadeOut;
    AppCompatButton btn_customer, btn_manager;
    TextView txt_appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_customer = findViewById(R.id.btn_customer);
        btn_manager = findViewById(R.id.btn_manager);
        txt_appName = findViewById(R.id.txt_appName);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        btn_customer.startAnimation(animFadeIn);
        btn_manager.startAnimation(animFadeIn);
        txt_appName.startAnimation(animFadeOut);


        btn_customer.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CustomerLoginActivity.class));
        });

        btn_manager.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ManagerLoginActivity.class));
        });
    }
}