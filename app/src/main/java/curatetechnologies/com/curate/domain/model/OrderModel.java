package curatetechnologies.com.curate.domain.model;

import java.util.Date;
import java.util.List;

/**
 * Created by mremondi on 3/26/18.
 */

public class OrderModel {

    private Integer id;
    private String deviceId;
    private UserModel user;
    private String instructions;
    private List<ItemModel> orderItems;
    private double totalOrderPrice;
    private Integer restaurantId;
    private String restaurantName;
    // TODO: private Date time;


    public OrderModel(Integer id, String deviceId, UserModel user, String instructions, List<ItemModel> orderItems,
                      double totalOrderPrice, Integer restaurantId, String restaurantName) {
        this.id = id;
        this.deviceId = deviceId;
        this.user = user;
        this.instructions = instructions;
        this.orderItems = orderItems;
        this.totalOrderPrice = totalOrderPrice;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<ItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}