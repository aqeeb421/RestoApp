package com.resto.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.resto.app.R;
import com.resto.app.activity.MainActivity;
import com.resto.app.common.MessageModel;
import com.resto.app.model.SignUpModel;
import com.resto.app.model.UpdateModel;
import com.resto.app.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {// Required empty public constructor
    }

    SharedPreferences sharedPreferences;
    TextView txt_username, txt_email, txt_Phone;
    String userName, email, phone;
    int Id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);
        userName = sharedPreferences.getString("name", "Default");
        email = sharedPreferences.getString("email", "Default");
        phone = sharedPreferences.getString("phone", "Default");

        txt_username = view.findViewById(R.id.txt_username);
        txt_email = view.findViewById(R.id.txt_email);
        txt_Phone = view.findViewById(R.id.txt_Phone);

        if (Id != 0) {
            txt_username.setText(userName);
            txt_email.setText(email);
            txt_Phone.setText(phone);
        } else {
            sharedPreferences = getActivity().getSharedPreferences("SignUpDetails", Context.MODE_PRIVATE);
            Id = sharedPreferences.getInt("Id", 0);
            userName = sharedPreferences.getString("name", "Default");
            email = sharedPreferences.getString("email", "Default");
            phone = sharedPreferences.getString("phone", "Default");

            txt_username = view.findViewById(R.id.txt_username);
            txt_email = view.findViewById(R.id.txt_email);
            txt_Phone = view.findViewById(R.id.txt_Phone);

            if (Id != 0) {
                txt_username.setText(userName);
                txt_email.setText(email);
                txt_Phone.setText(phone);
            }
        }


        view.findViewById(R.id.txt_update).setOnClickListener(v -> {

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.update_pop, null);

            EditText edt_reg_userName = dialogView.findViewById(R.id.edt_reg_userName);
            EditText edt_reg_phoneNumber = dialogView.findViewById(R.id.edt_reg_phoneNumber);
            EditText edt_reg_email = dialogView.findViewById(R.id.edt_reg_email);
            EditText edt_reg_password = dialogView.findViewById(R.id.edt_reg_password);
            Button btn_Cancel = dialogView.findViewById(R.id.btn_Cancel);
            Button btn_Update = dialogView.findViewById(R.id.btn_Update);
            //setting
            edt_reg_userName.setText(txt_username.getText().toString().trim());
            edt_reg_phoneNumber.setText(txt_Phone.getText().toString().trim());
            edt_reg_email.setText(txt_email.getText().toString().trim());


            builder.setView(dialogView);
            builder.setCancelable(false);
            final androidx.appcompat.app.AlertDialog show = builder.show();
            btn_Update.setOnClickListener(v1 -> {

                String UserName = edt_reg_userName.getText().toString().trim();
                String PhoneNumber = edt_reg_phoneNumber.getText().toString().trim();
                String Email = edt_reg_email.getText().toString().trim();
                String password = edt_reg_password.getText().toString().trim();

                UpdateProfile(1, UserName, PhoneNumber, Email, password);
                show.dismiss();

            });

            btn_Cancel.setOnClickListener(v1 -> show.dismiss());

        });


        view.findViewById(R.id.txt_logout).setOnClickListener(v -> {

            Logout(Id);

        });


        return view;
    }

    private void UpdateProfile(int id, String userName, String phoneNumber, String email, String password) {

        UpdateModel updateModel = new UpdateModel();

        updateModel.setName(userName);
        updateModel.setEmail(email);
        updateModel.setPhone(phoneNumber);
        updateModel.setPassword(password);


        Call<SignUpModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .UpdateProfile(1, updateModel);

        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: ");
                    startActivity(new Intent());
                } else {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void Logout(int id) {
        Call<MessageModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .Logout(1);

        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful()) {

                    sharedPreferences = getActivity().getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("Id");
                    editor.remove("name");
                    editor.remove("email");
                    editor.remove("phone");
                    editor.apply();

                    sharedPreferences = getActivity().getSharedPreferences("SignUpDetails", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.remove("Id");
                    editor.remove("name");
                    editor.remove("email");
                    editor.remove("phone");
                    editor.apply();

                    Toast.makeText(getActivity(), "logout successfully...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {

            }
        });

    }
}