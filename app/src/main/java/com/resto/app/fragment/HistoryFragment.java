package com.resto.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.resto.app.R;
import com.resto.app.adapter.DealsListAdapter;
import com.resto.app.adapter.TransactionAdapter;
import com.resto.app.model.DealsModel;
import com.resto.app.model.TransactionModel;
import com.resto.app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    RecyclerView recycler_transaction;
    TransactionAdapter transactionAdapter;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recycler_transaction = view.findViewById(R.id.recycler_transaction);
        progressBar = view.findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);

        GetTransactionHistory(Id);
        return view;
    }

    private void GetTransactionHistory(int id) {
        Call<List<TransactionModel>> call = RetrofitClient
                .getInstance()
                .getApi()
                .GetTransactionHistory(id);

        call.enqueue(new Callback<List<TransactionModel>>() {
            @Override
            public void onResponse(Call<List<TransactionModel>> call, Response<List<TransactionModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body().size() > 0) {

                    ArrayList<TransactionModel> transactionModel = (ArrayList<TransactionModel>) response.body();
                    setRecyclerView(transactionModel);
                }
            }

            @Override
            public void onFailure(Call<List<TransactionModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void setRecyclerView(ArrayList<TransactionModel> transactionModel) {
        recycler_transaction.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionAdapter = new TransactionAdapter(getContext(), transactionModel);
        recycler_transaction.setItemAnimator(new DefaultItemAnimator());
        recycler_transaction.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();
    }
}