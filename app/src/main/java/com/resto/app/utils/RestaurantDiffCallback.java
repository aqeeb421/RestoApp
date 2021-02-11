package com.resto.app.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.resto.app.model.RestaurantListModel;

import java.util.List;

public class RestaurantDiffCallback extends DiffUtil.Callback {

    private final List<RestaurantListModel> mOldRestaurantList;
    private final List<RestaurantListModel> mNewRestaurantList;

    public RestaurantDiffCallback(List<RestaurantListModel> mOldRestaurantList, List<RestaurantListModel> mNewRestaurantList) {
        this.mOldRestaurantList = mOldRestaurantList;
        this.mNewRestaurantList = mNewRestaurantList;
    }


    @Override
    public int getOldListSize() {
        return mOldRestaurantList != null ? mOldRestaurantList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mOldRestaurantList != null ? mNewRestaurantList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldRestaurantList.get(oldItemPosition).getId().equals(mNewRestaurantList.get(
                newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final RestaurantListModel oldRestaurant = mOldRestaurantList.get(oldItemPosition);
        final RestaurantListModel newRestaurant = mNewRestaurantList.get(newItemPosition);

        return oldRestaurant.getName().equals(newRestaurant.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
       /* RestaurantListModel mOld = mOldRestaurantList.get(oldItemPosition);
        RestaurantListModel mNew = mNewRestaurantList.get(newItemPosition);

        Bundle bundle = new Bundle();
        if (!mNew.getName().equals(mOld.getName())) {
            bundle.putString("name", mNew.getName());
        }*/

        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
