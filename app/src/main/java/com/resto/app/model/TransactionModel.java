package com.resto.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("users_id")
    @Expose
    private UsersId usersId;
    @SerializedName("restraunt_id")
    @Expose
    private RestaurantListModel restrauntId;
    @SerializedName("trans_id")
    @Expose
    private String transId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("trans_amt")
    @Expose
    private String transAmt;
    @SerializedName("trans_date")
    @Expose
    private String transDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsersId getUsersId() {
        return usersId;
    }

    public void setUsersId(UsersId usersId) {
        this.usersId = usersId;
    }

    public RestaurantListModel getRestrauntId() {
        return restrauntId;
    }

    public void setRestrauntId(RestaurantListModel restrauntId) {
        this.restrauntId = restrauntId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
}
