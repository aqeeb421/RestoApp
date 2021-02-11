package com.resto.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealsModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("deal_title")
    @Expose
    private Object dealTitle;
    @SerializedName("deal_description")
    @Expose
    private Object dealDescription;
    @SerializedName("deal_banner")
    @Expose
    private Object dealBanner;
    @SerializedName("no_of_meals")
    @Expose
    private Integer noOfMeals;
    @SerializedName("value_of_meal")
    @Expose
    private String valueOfMeal;
    @SerializedName("min_days")
    @Expose
    private Integer minDays;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("restraunt_id")
    @Expose
    private Integer restrauntId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(Object dealTitle) {
        this.dealTitle = dealTitle;
    }

    public Object getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(Object dealDescription) {
        this.dealDescription = dealDescription;
    }

    public Object getDealBanner() {
        return dealBanner;
    }

    public void setDealBanner(Object dealBanner) {
        this.dealBanner = dealBanner;
    }

    public Integer getNoOfMeals() {
        return noOfMeals;
    }

    public void setNoOfMeals(Integer noOfMeals) {
        this.noOfMeals = noOfMeals;
    }

    public String getValueOfMeal() {
        return valueOfMeal;
    }

    public void setValueOfMeal(String valueOfMeal) {
        this.valueOfMeal = valueOfMeal;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRestrauntId() {
        return restrauntId;
    }

    public void setRestrauntId(Integer restrauntId) {
        this.restrauntId = restrauntId;
    }

}
