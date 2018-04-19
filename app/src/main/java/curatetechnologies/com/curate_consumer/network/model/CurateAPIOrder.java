package curatetechnologies.com.curate_consumer.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

/**
 * Created by mremondi on 3/26/18.
 */

public class CurateAPIOrder {

    @SerializedName("Order_ID")
    @Expose
    private Integer orderID;

    @SerializedName("Order_OrderItemsInformation")
    @Expose
    private JSONArray orderItemsInformation;

    @SerializedName("Order_Time")
    @Expose
    private String orderTime;

    @SerializedName("Order_TotalPrice")
    @Expose
    private Double orderTotalPrice;

    @SerializedName("Order_MealTax")
    @Expose
    private Double orderMealTax;

    @SerializedName("Order_CurateFees")
    @Expose
    private Double orderCurateFees;

    @SerializedName("User_ID")
    @Expose
    private Integer userID;

    @SerializedName("Restaurant_ID")
    @Expose
    private Integer restaurantID;

    public CurateAPIOrder(Integer orderID, JSONArray orderItemsInformation, String orderTime,
                          Double orderTotalPrice, Double orderMealTax, Double orderCurateFees,
                          Integer userID, Integer restaurantID) {
        this.orderID = orderID;
        this.orderItemsInformation = orderItemsInformation;
        this.orderTime = orderTime;
        this.orderTotalPrice = orderTotalPrice;
        this.orderMealTax = orderMealTax;
        this.orderCurateFees = orderCurateFees;
        this.userID = userID;
        this.restaurantID = restaurantID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public JSONArray getOrderItemsInformation() {
        return orderItemsInformation;
    }

    public void setOrderItemsInformation(JSONArray orderItemsInformation) {
        this.orderItemsInformation = orderItemsInformation;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Double getOrderMealTax() {
        return orderMealTax;
    }

    public void setOrderMealTax(Double orderMealTax) {
        this.orderMealTax = orderMealTax;
    }

    public Double getOrderCurateFees() {
        return orderCurateFees;
    }

    public void setOrderCurateFees(Double orderCurateFees) {
        this.orderCurateFees = orderCurateFees;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }
}
