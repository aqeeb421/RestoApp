package com.resto.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.app.R;
import com.resto.app.adapter.DealsListAdapter;
import com.resto.app.model.DealsModel;
import com.resto.app.model.RestaurantListModel;
import com.resto.app.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestautantActivity extends AppCompatActivity {

    private static final String TAG = "RestautantActivity";
    ImageView resto_item_image, resto_item_like;
    TextView resto_item_name, resto_item_address, resto_closeAt, resto_openAt;
    RatingBar ratingBar;
    ProgressBar progressBar;
    EditText edt_activation_code;
    RecyclerView recycler_deals;
    DealsListAdapter delaslistAdapter;
    String code;

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
        edt_activation_code = findViewById(R.id.edt_activation_code);
        recycler_deals = findViewById(R.id.recycler_deals);

        GetRestList(resto_id);

        Log.d(TAG, "onCreate: ");


    }

    private void GetDealsList(int resto_id) {
        Call<List<DealsModel>> call = RetrofitClient
                .getInstance()
                .getApi()
                .GetDeals(resto_id);

        call.enqueue(new Callback<List<DealsModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DealsModel>> call, @NotNull Response<List<DealsModel>> response) {
                if (response.code() == 200 && response.body().size() > 0) {
                    Log.d(TAG, "onResponse: " + response.isSuccessful());
                    ArrayList<DealsModel> dealsModels = (ArrayList<DealsModel>) response.body();
                    setRecyclerView(dealsModels);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<DealsModel>> call, @NotNull Throwable t) {

            }
        });
    }

    private void setRecyclerView(ArrayList<DealsModel> dealsModels) {
        recycler_deals.setLayoutManager(new LinearLayoutManager(this));
        delaslistAdapter = new DealsListAdapter(this, dealsModels,code );
        recycler_deals.setItemAnimator(new DefaultItemAnimator());
        recycler_deals.setAdapter(delaslistAdapter);
        delaslistAdapter.notifyDataSetChanged();
    }


    private void GetRestList(int resto_id) {
        Call<List<RestaurantListModel>> call = RetrofitClient
                .getInstance()
                .getApi()
                .GetRestList();

        call.enqueue(new Callback<List<RestaurantListModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantListModel>> call, Response<List<RestaurantListModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());


                    RestaurantListModel restaurantListModel = (RestaurantListModel) response.body().get(resto_id);
                    Log.d(TAG, "onResponse: " + restaurantListModel);

                    resto_item_name.setText(restaurantListModel.getName());
                    resto_item_address.setText(restaurantListModel.getAddress());
                    resto_openAt.setText(restaurantListModel.getOpenTime());
                    resto_closeAt.setText(restaurantListModel.getCloseTime());
                    float rating = Float.valueOf(restaurantListModel.getRating());
                    ratingBar.setRating(rating);
                    code = restaurantListModel.getRestCode();
                    if (restaurantListModel.getImage() != null) {
                        Picasso.get().load(restaurantListModel.getImage().toString()).fit().into(resto_item_image);
                    } else {
                        Picasso.get().load(R.drawable.resto).fit().into(resto_item_image);
                    }

                    GetDealsList(resto_id);

                }
            }

            @Override
            public void onFailure(Call<List<RestaurantListModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}