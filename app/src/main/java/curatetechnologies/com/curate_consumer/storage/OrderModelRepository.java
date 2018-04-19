package curatetechnologies.com.curate_consumer.storage;

import android.content.Context;

import com.google.android.gms.tasks.Task;

import curatetechnologies.com.curate_consumer.domain.model.OrderModel;

/**
 * Created by mremondi on 3/26/18.
 */

public interface OrderModelRepository {

    Task<Void> sendOrderToFirebase(OrderModel orderModel);

    boolean postOrder(String jwt, OrderModel orderModel, Context appContext);

    OrderModel getLastOrder(Context appContext);

    void clearLastOrder(Context context);
}
