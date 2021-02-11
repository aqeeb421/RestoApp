package com.resto.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.resto.app.R;
import com.resto.app.utils.MyApplication;
import com.resto.app.model.SignUpModel;
import com.resto.app.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSignUpActivity extends AppCompatActivity {

    EditText edt_reg_userName, edt_reg_phoneNumber, edt_reg_email, edt_reg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        init();
    }

    private void init() {

        edt_reg_userName = findViewById(R.id.edt_reg_userName);
        edt_reg_phoneNumber = findViewById(R.id.edt_reg_phoneNumber);
        edt_reg_email = findViewById(R.id.edt_reg_email);
        edt_reg_password = findViewById(R.id.edt_reg_password);

        findViewById(R.id.txt_SignIn).setOnClickListener(v -> {
            startActivity(new Intent(CustomerSignUpActivity.this, CustomerLoginActivity.class));
            finish();
        });
        findViewById(R.id.arrow_back).setOnClickListener(v -> {
            startActivity(new Intent(CustomerSignUpActivity.this, MainActivity.class));
            finish();
        });
        findViewById(R.id.btn_CSignUp).setOnClickListener(v -> {

            String UserName = edt_reg_userName.getText().toString().trim();
            String PhoneNumber = edt_reg_phoneNumber.getText().toString().trim();
            String Email = edt_reg_email.getText().toString().trim();
            String password = edt_reg_password.getText().toString().trim();

            if (UserName.isEmpty()) {
                edt_reg_userName.setError("UserName is required");
                edt_reg_userName.requestFocus();
                return;
            }
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


            SendSignUpUser(UserName, PhoneNumber, Email, password);
        });


    }

    private SharedPreferences sharedPreferences;

    private void SendSignUpUser(String userName, String phoneNumber, String email, String password) {
        /*Do API  call here*/
        Call<SignUpModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .SendSignUpUser(userName, email, phoneNumber, password, "A" );

        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if (response.code() == 201) {
                    Toast.makeText(CustomerSignUpActivity.this, "Registered successfully...", Toast.LENGTH_SHORT).show();

                    assert response.body() != null;
                    MyApplication.setUserId(response.body().getId());

                    sharedPreferences = getSharedPreferences("SignUpDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Id", response.body().getId());
                    editor.putString("name", response.body().getName());
                    editor.putString("email", response.body().getEmail());
                    editor.putString("phone", response.body().getPhone());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(CustomerSignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Toast.makeText(CustomerSignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}