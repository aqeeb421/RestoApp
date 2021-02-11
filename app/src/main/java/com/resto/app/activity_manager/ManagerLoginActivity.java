package com.resto.app.activity_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.resto.app.R;
import com.resto.app.activity.MainActivity;

public class ManagerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);
        init();
    }

    private void init() {
        findViewById(R.id.txt_SignUp).setOnClickListener(v -> {
            startActivity(new Intent(ManagerLoginActivity.this, ManagerSignUpActivity.class));
            finish();
        });

        findViewById(R.id.arrow_back).setOnClickListener(v -> {
            startActivity(new Intent(ManagerLoginActivity.this, MainActivity.class));
            finish();
        });


    }
}