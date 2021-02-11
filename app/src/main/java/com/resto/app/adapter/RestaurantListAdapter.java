package com.resto.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.app.R;
import com.resto.app.activity.RestautantActivity;
import com.resto.app.model.RestaurantListModel;
import com.resto.app.utils.RestaurantDiffCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> implements Filterable {

    private Context context;
    private ArrayList<RestaurantListModel> restaurantListModel;
    private ArrayList<RestaurantListModel> restaurantListModelFull;

    public RestaurantListAdapter(Context context, ArrayList<RestaurantListModel> restaurantListModel) {
        this.context = context;
        this.restaurantListModel = restaurantListModel;
        restaurantListModelFull = new ArrayList<>(restaurantListModel);
    }

    public void updateRestaurantListItems(List<RestaurantListModel> employees) {
        final RestaurantDiffCallback diffCallback = new RestaurantDiffCallback(restaurantListModel, employees);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.restaurantListModel.clear();
        this.restaurantListModel.addAll(employees);
        diffResult.dispatchUpdatesTo(this);
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        holder.resto_item_name.setText(restaurantListModel.get(position).getName());
        holder.resto_item_address.setText(restaurantListModel.get(position).getAddress());
        Double rating = Double.valueOf(restaurantListModel.get(position).getRating());
        holder.resto_item_rating.setText(rating.toString());

        if (restaurantListModel.get(position).getImage() != null) {
            Picasso.get().load(restaurantListModel.get(position).getImage().toString()).fit().into(holder.resto_item_image);
        } else {
            Picasso.get().load(R.drawable.resto).fit().into(holder.resto_item_image);
        }

        holder.resto_item_image.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestautantActivity.class);
            intent.putExtra("resto_id", restaurantListModel.get(position).getId());
            context.startActivity(intent);
        });


    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return restaurantListModel.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RestaurantListModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(restaurantListModelFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RestaurantListModel restaurantListModel : restaurantListModelFull) {
                    if (restaurantListModel.getName().toLowerCase().contains(filterPattern)) {

                        String s = restaurantListModel.getName();
                        Log.d(TAG, "performFiltering: " + s);

                        filteredList.add(restaurantListModel);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            restaurantListModel.clear();
            restaurantListModel.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView resto_item_image, resto_item_like;
        TextView resto_item_name, resto_item_address, resto_item_rating;
        CardView card_item;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item = itemView.findViewById(R.id.card_item);
            resto_item_image = itemView.findViewById(R.id.resto_item_image);
            resto_item_like = itemView.findViewById(R.id.resto_item_like);
            resto_item_name = itemView.findViewById(R.id.resto_item_name);
            resto_item_address = itemView.findViewById(R.id.resto_item_address);
            resto_item_rating = itemView.findViewById(R.id.resto_item_rating);
        }
    }
}
