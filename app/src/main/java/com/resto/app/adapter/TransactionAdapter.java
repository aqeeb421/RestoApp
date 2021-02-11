package com.resto.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.app.R;
import com.resto.app.model.TransactionModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    ArrayList<TransactionModel> transactionModels;
    Context context;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactionModels) {
        this.transactionModels = transactionModels;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.trans_resto_name.setText(transactionModels.get(position).getRestrauntId().getName());
        holder.trans_resto_id.setText(transactionModels.get(position).getTransId());
        holder.trans_resto_desc.setText(transactionModels.get(position).getDescription());
        holder.trans_resto_amt.setText("$ "+transactionModels.get(position).getTransAmt());

        // Note, MM is months, not mm
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy  |  HH:mm a", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

        String inputText = transactionModels.get(position).getTransDate().substring(0, 20);
        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        holder.trans_resto_date.setText(outputText);


    }

    @Override
    public int getItemCount() {
        return transactionModels.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView trans_resto_name, trans_resto_id, trans_resto_desc, trans_resto_amt, trans_resto_date;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            trans_resto_name = itemView.findViewById(R.id.trans_resto_name);
            trans_resto_id = itemView.findViewById(R.id.trans_resto_id);
            trans_resto_desc = itemView.findViewById(R.id.trans_resto_desc);
            trans_resto_amt = itemView.findViewById(R.id.trans_resto_amt);
            trans_resto_date = itemView.findViewById(R.id.trans_resto_date);
        }
    }
}
