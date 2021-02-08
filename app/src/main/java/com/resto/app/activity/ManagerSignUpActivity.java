package com.resto.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.resto.app.R;
import com.resto.app.activity_manager.MHomeActivity;
import com.resto.app.model.SignUpModel;
import com.resto.app.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerSignUpActivity extends AppCompatActivity {

    EditText edt_reg_userName, edt_reg_phoneNumber, edt_reg_email, edt_reg_password, edt_reg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_sign_up);
        init();
    }

    private void init() {

        edt_reg_userName = findViewById(R.id.edt_reg_userName);
        edt_reg_phoneNumber = findViewById(R.id.edt_reg_phoneNumber);
        edt_reg_email = findViewById(R.id.edt_reg_email);
        edt_reg_password = findViewById(R.id.edt_reg_password);
        edt_reg_id = findViewById(R.id.edt_reg_id);


        findViewById(R.id.txt_SignIn).setOnClickListener(v -> {
            startActivity(new Intent(ManagerSignUpActivity.this, ManagerLoginActivity.class));
            finish();
        });
        findViewById(R.id.arrow_back).setOnClickListener(v -> {
            startActivity(new Intent(ManagerSignUpActivity.this, MainActivity.class));
            finish();
        });

        findViewById(R.id.btn_MSignUp).setOnClickListener(v -> {

            String UserName = edt_reg_userName.getText().toString().trim();
            String PhoneNumber = edt_reg_phoneNumber.getText().toString().trim();
            String Email = edt_reg_email.getText().toString().trim();
            String password = edt_reg_password.getText().toString().trim();
            String ID = edt_reg_id.getText().toString().trim();

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
            if (ID.isEmpty()) {
                edt_reg_id.setError("ID is required");
                edt_reg_id.requestFocus();
                return;
            }

            if (password.length() < 4) {
                edt_reg_password.setError("Password should be atleast 5 char long");
                edt_reg_password.requestFocus();
                return;
            }


            SendSignUpManager(UserName, PhoneNumber, Email, password, ID);
        });


    }

    private void SendSignUpManager(String userName, String phoneNumber, String email, String password, String ID) {
        /*Do API  call here*/
        Call<SignUpModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .SendSignUpManager(userName, email, phoneNumber, password, "M", ID);

        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if (response.code() == 201) {
                    Toast.makeText(ManagerSignUpActivity.this, "Registered successfully...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManagerSignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Toast.makeText(ManagerSignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}