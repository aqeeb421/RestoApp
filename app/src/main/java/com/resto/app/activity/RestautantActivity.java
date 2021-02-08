package com.resto.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.resto.app.R;
import com.resto.app.model.RestaurantListModel;
import com.resto.app.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RestautantActivity extends AppCompatActivity {

    private static final String TAG = "RestautantActivity";
    ImageView resto_item_image, resto_item_like;
    TextView resto_item_name, resto_item_address, resto_closeAt, resto_openAt;
    RatingBar ratingBar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restautant);

        int resto_id = getIntent().getIntExtra("resto_id", 0);
        progressBar = findViewById(R.id.progressBar);
        resto_item_image = findViewById(R.id.resto_item_image);
        resto_item_like = findViewById(R.id.resto_item_like);
        resto_item_name = findViewById(R.id.resto_item_name);
        resto_item_address = findViewById(R.id.resto_item_address);
        ratingBar = findViewById(R.id.app_ratingBar);
        resto_closeAt = findViewById(R.id.resto_closeAt);
        resto_openAt = findViewById(R.id.resto_openAt);

        GetRestList(resto_id);

        Log.d(TAG, "onCreate: ");
    }

    private void GetRestList(int resto_id) {
        Call<List<RestaurantListModel>> call = RetrofitClient
                .getInstance()
                .getApi()
                .GetRestList();

        call.enqueue(new Callback<List<RestaurantListModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantListModel>> call, Response<List<RestaurantListModel>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    progressBar.setVisibility(View.GONE);

                    RestaurantListModel restaurantListModel = (RestaurantListModel) response.body().get(resto_id);
                    Log.d(TAG, "onResponse: " + restaurantListModel);

                    resto_item_name.setText(restaurantListModel.getName());
                    resto_item_address.setText(restaurantListModel.getAddress());
                    resto_openAt.setText(restaurantListModel.getOpenTime());
                    resto_closeAt.setText(restaurantListModel.getCloseTime());
                    float rating = Float.valueOf(restaurantListModel.getRating());
                    ratingBar.setRating(rating);

                    if (restaurantListModel.getImage() != null) {
                        Picasso.get().load(restaurantListModel.getImage().toString()).fit().into(resto_item_image);
                    } else {
                        Picasso.get().load(R.drawable.resto).fit().into(resto_item_image);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<RestaurantListModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}