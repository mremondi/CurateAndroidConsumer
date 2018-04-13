package curatetechnologies.com.curate.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonNull;

import curatetechnologies.com.curate.config.Constants;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.firebase.FirebaseClient;
import curatetechnologies.com.curate.network.converters.curate.OrderConverter;
import curatetechnologies.com.curate.network.firebase.model.FirebaseOrder;
import curatetechnologies.com.curate.network.model.CurateAPIOrder;
import curatetechnologies.com.curate.network.services.OrderService;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mremondi on 3/26/18.
 */

public class OrderRepository implements OrderModelRepository {

    @Override
    public Task<Void> sendOrderToFirebase(OrderModel orderModel) {

        FirebaseOrder firebaseOrder = OrderConverter.convertOrderModelToFirebaseOrder(orderModel);

        return FirebaseClient.pushOrderToFirebase(firebaseOrder);
    }

    @Override
    public boolean postOrder(String jwt, OrderModel orderModel, Context appContext) {

        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderModel);
        prefsEditor.putString(Constants.LAST_ORDER_SHARED_PREFERENCE_KEY, json);
        prefsEditor.apply();

        OrderService orderService = CurateClient.getService(OrderService.class);
        try {
            String bearerToken = "Bearer " + jwt;
            Log.d("BEARER TOKEN ", bearerToken);
            CurateAPIOrder curateAPIOrder = OrderConverter.convertOrderModelToCurateModel(orderModel);
            Call<JsonNull> postOrder = orderService.postOrder(bearerToken, curateAPIOrder);
            Response<JsonNull> response = postOrder.execute();
        } catch (Exception e) {
            Log.d("network post order", "failure " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public OrderModel getLastOrder(Context appContext) {

        SharedPreferences  prefs = appContext.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(Constants.LAST_ORDER_SHARED_PREFERENCE_KEY, "");
        return gson.fromJson(json, OrderModel.class);
    }

    @Override
    public void clearLastOrder(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.CURATE_SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.remove(Constants.LAST_ORDER_SHARED_PREFERENCE_KEY);
        prefsEditor.apply();
    }
}
