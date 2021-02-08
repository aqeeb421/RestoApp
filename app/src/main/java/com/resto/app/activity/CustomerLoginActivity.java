package com.resto.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.resto.app.R;
import com.resto.app.common.utils;
import com.resto.app.model.SignInModel;
import com.resto.app.model.SignUpModel;
import com.resto.app.model.User;
import com.resto.app.network.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerLoginActivity extends AppCompatActivity {

    EditText edt_reg_email, edt_reg_password;
    private String TAG = "CustomerLoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        init();
    }

    private void init() {

        edt_reg_email = findViewById(R.id.edt_login_email);
        edt_reg_password = findViewById(R.id.edt_login_password);


        findViewById(R.id.txt_SignUp).setOnClickListener(v -> {
            startActivity(new Intent(CustomerLoginActivity.this, CustomerSignUpActivity.class));
            finish();
        });

        findViewById(R.id.arrow_back).setOnClickListener(v -> {
            startActivity(new Intent(CustomerLoginActivity.this, MainActivity.class));
            finish();
        });
        findViewById(R.id.btn_CSignIn).setOnClickListener(v -> {

            String Email = edt_reg_email.getText().toString().trim();
            String password = edt_reg_password.getText().toString().trim();
            if (Email.isEmpty()) {
                edt_reg_email.setError("Email is required");
                edt_reg_email.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                edt_reg_password.setError("Password is required");
                edt_reg_password.requestFocus();
                return;
            }

            if (password.length() < 4) {
                edt_reg_password.setError("Password should be atleast 5 char long");
                edt_reg_password.requestFocus();
                return;
            }

            SendSignInUser(Email, password);

        });


    }

    private SharedPreferences sharedPreferences;

    private void SendSignInUser(String email, String password) {

        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),
                email);

        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"),
                password);


        /*Do API  call here*/
        Call<SignInModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .SignInUser(email1, password1);


        call.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: ");
                    if (response.body().getToken() != null) {
                        sharedPreferences = getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("Id", response.body().getUser().getId());
                        editor.putString("name", response.body().getUser().getName());
                        editor.putString("email", response.body().getUser().getEmail());
                        editor.putString("phone", response.body().getUser().getPhone());
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CustomerLoginActivity.this, "please check once", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {

            }
        });

    }
}