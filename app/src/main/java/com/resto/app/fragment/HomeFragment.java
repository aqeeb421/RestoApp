package com.resto.app.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.resto.app.R;
import com.resto.app.adapter.RestaurantListAdapter;
import com.resto.app.common.MessageModel;
import com.resto.app.model.RestaurantListModel;
import com.resto.app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView_resto;
    ProgressBar progressBar;
    private SearchView search_view;
    RestaurantListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView_resto = view.findViewById(R.id.recycler_view_resto);
        progressBar = view.findViewById(R.id.progressBar);
        search_view = view.findViewById(R.id.search_view);

        GetRestList();

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    listAdapter.getFilter().filter(newText);
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        return view;
    }

    private void GetRestList() {
        Call<List<RestaurantListModel>> call = RetrofitClient
                .getInstance()
                .getApi()
                .GetRestList();

        call.enqueue(new Callback<List<RestaurantListModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantListModel>> call, Response<List<RestaurantListModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse: " + response.body());

                    ArrayList<RestaurantListModel> restaurantListModel = (ArrayList<RestaurantListModel>) response.body();
                    setRecyclerView(restaurantListModel);

                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantListModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void setRecyclerView(ArrayList<RestaurantListModel> restaurantListModel) {
        recyclerView_resto.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new RestaurantListAdapter(getContext(), restaurantListModel);
        recyclerView_resto.setItemAnimator(new DefaultItemAnimator());
        recyclerView_resto.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}