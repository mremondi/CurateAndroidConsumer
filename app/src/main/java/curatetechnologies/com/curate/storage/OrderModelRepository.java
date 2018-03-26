package curatetechnologies.com.curate.storage;

import com.google.android.gms.tasks.Task;

import curatetechnologies.com.curate.domain.model.OrderModel;

/**
 * Created by mremondi on 3/26/18.
 */

public interface OrderModelRepository {

    Task<Void> sendOrderToFirebase(OrderModel orderModel);
}
