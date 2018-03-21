package curatetechnologies.com.curate.manager;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.ui.views.CartButtonWrapper;

/**
 * Created by mremondi on 2/26/18.
 */

public class CartManager {

    private static CartManager instance;

    private ArrayList<ItemModel> orderItems = new ArrayList<>();
    private Integer restaurantId;
    private String restaurantName;
    private String restaurantLogoUrl;
    private Double mealTaxRate;

    private CartManager() {
    }

    // synchronized just in case any background threads are trying to access at the same time
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public Integer getRestaurantId(){
        return this.restaurantId;
    }

    public void addItemToCart(ItemModel item){
        if (restaurantId == null){
            restaurantId = item.getRestaurantId();
            this.orderItems.add(item);
            this.restaurantName = item.getRestaurantName();
        } else if (!restaurantId.equals(item.getRestaurantId())){
            clearCart();
            this.orderItems.add(item);
            this.restaurantId = item.getRestaurantId();
            this.restaurantName = item.getRestaurantName();
        } else{
            this.orderItems.add(item);
        }
        CartButtonWrapper.getInstance().updateCartButtonCount(orderItems.size());
    }

    public void removeItemFromCart(ItemModel item){
        this.orderItems.remove(item);
    }

    public void removeItemAtIndex(Integer index){
        this.orderItems.remove(index);
    }

    public ArrayList<ItemModel> getOrderItems(){
        return orderItems;
    }

    public int getOrderItemCount() { return orderItems.size(); }

    public ArrayList<Integer> getOrderItemIds(){
        ArrayList<Integer> itemIds = new ArrayList<>();
        for (ItemModel item : orderItems){
            itemIds.add(item.getId());
        }
        return itemIds;
    }


    public void clearCart(){
        this.orderItems.clear();
        this.restaurantId = null;
        this.mealTaxRate = null;
        this.restaurantName = null;
        this.restaurantLogoUrl = null;
    }

    public void setMealTax(Double mealTax){
        this.mealTaxRate = mealTax;
    }

    public Double getOrderTax(){
        Double price = getSubTotal();
        return (double) Math.round((price * this.mealTaxRate) * 100) / 100;
    }

    public Double getSubTotal(){
        Double subtotal = 0.0;
        for (ItemModel item: orderItems){
            subtotal += item.getNumericPrice();
        }
        return (double) Math.round(subtotal * 100) / 100;
    }

    public Double getOrderTotal(){
        return (double) Math.round((getSubTotal() +  getOrderTax()) * 100) / 100;
    }

    public Integer getOrderTotalForStripe(){
        return (int) Math.round(getOrderTotal() * 100);
    }

    public void cacheOrderForRating(){
        // TODO:
    }

    public void clearOrderCache(){
        // TODO:
    }

    public List<ItemModel> getOrderFromCache(){
        // TODO:
        return null;
    }

}