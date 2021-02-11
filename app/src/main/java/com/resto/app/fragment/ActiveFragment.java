package com.resto.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramijemli.percentagechartview.PercentageChartView;
import com.ramijemli.percentagechartview.callback.AdaptiveColorProvider;
import com.resto.app.R;
import com.resto.app.adapter.ActiveAdapter;
import com.resto.app.adapter.TransactionAdapter;


public class ActiveFragment extends Fragment {


    public ActiveFragment() {
        // Required empty public constructor
    }

    RecyclerView recycler_active;
    ActiveAdapter activeAdapter;
    PercentageChartView mPieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active, container, false);
        recycler_active = view.findViewById(R.id.recycler_active);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);

        GetActiveSubscriptions(Id);

        return view;
    }

    private void GetActiveSubscriptions(int id) {

    }


}