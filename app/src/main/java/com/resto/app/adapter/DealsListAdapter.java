package com.resto.app.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.app.R;
import com.resto.app.model.DealsModel;
import com.resto.app.model.SignInModel;
import com.resto.app.model.SuccessModel;
import com.resto.app.network.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DealsListAdapter extends RecyclerView.Adapter<DealsListAdapter.DealsListViewHolder> {

    private Context context;
    private ArrayList<DealsModel> dealsModels;
    private String getRestCode;
    private int Deal_ID;

    public DealsListAdapter(Context context, ArrayList<DealsModel> dealsModels, String code) {
        this.context = context;
        this.dealsModels = dealsModels;
        this.getRestCode = code;
    }

    @NonNull
    @Override
    public DealsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_item, parent, false);
        return new DealsListAdapter.DealsListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DealsListViewHolder holder, int position) {

        try {
            holder.deal_title.setText(dealsModels.get(position).getDealTitle().toString());
            holder.deal_desc.setText(dealsModels.get(position).getDealDescription().toString());
            holder.meal_price.setText(dealsModels.get(position).getValueOfMeal().toString());
            holder.no_of_meal.setText(dealsModels.get(position).getNoOfMeals().toString());

            Deal_ID = (dealsModels.get(position).getId());

        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: " + e.getMessage());
        }
        // holder.deal_title.setText(dealsModels.get(position).getDealTitle().toString());

        holder.itemView.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_dialog);

            EditText edt_activation_code = (EditText) dialog.findViewById(R.id.edt_activation_code);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_Activation);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String activation_code = edt_activation_code.getText().toString();
                    if (activation_code.isEmpty()) {
                        edt_activation_code.setError("Enter valid code");
                        return;
                    } else {
                         ValidatedOtp(activation_code, getRestCode);
                    }

                    dialog.dismiss();
                }
            });

            dialog.show();
        });

    }

    private void ValidatedOtp(String activation_code, String restCode) {

        if (activation_code.equals(restCode)) {
            Toast.makeText(context, "hurrh done", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("SignUpDetails", Context.MODE_PRIVATE);
            String userId = String.valueOf(sharedPreferences.getInt("Id", 1));
            ActivateSubscription(restCode, Deal_ID, userId);

        } else {
            Toast.makeText(context, "Enter Valid Code", Toast.LENGTH_SHORT).show();
        }

    }

    private void ActivateSubscription(String restCode, int deal_id, String userId) {
        RequestBody restCode1 = RequestBody.create(MediaType.parse("text/plain"),
                restCode);

        RequestBody deal_id1 = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(deal_id));

        RequestBody userId1 = RequestBody.create(MediaType.parse("text/plain"),
                userId);

        /*Do API  call here*/
        Call<SuccessModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .ActivateSubscription(restCode1, deal_id1, userId1);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(@NotNull Call<SuccessModel> call, @NotNull Response<SuccessModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "your subscription is activated...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SuccessModel> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dealsModels.size();
    }

    public class DealsListViewHolder extends RecyclerView.ViewHolder {

        TextView deal_title, deal_desc, meal_price, no_of_meal;
        ImageView deal_banner;

        public DealsListViewHolder(@NonNull View itemView) {
            super(itemView);

            deal_title = itemView.findViewById(R.id.deal_title);
            deal_desc = itemView.findViewById(R.id.deal_desc);
            meal_price = itemView.findViewById(R.id.meal_price);
            no_of_meal = itemView.findViewById(R.id.no_of_meal);
            deal_banner = itemView.findViewById(R.id.deal_banner);
        }
    }
}
