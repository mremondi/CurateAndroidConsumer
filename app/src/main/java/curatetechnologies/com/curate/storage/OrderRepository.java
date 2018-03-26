package curatetechnologies.com.curate.storage;

import com.google.android.gms.tasks.Task;

import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.network.firebase.FirebaseClient;
import curatetechnologies.com.curate.network.converters.curate.OrderConverter;
import curatetechnologies.com.curate.network.firebase.model.FirebaseOrder;

/**
 * Created by mremondi on 3/26/18.
 */

public class OrderRepository implements OrderModelRepository {

    @Override
    public Task<Void> sendOrderToFirebase(OrderModel orderModel) {

        FirebaseOrder firebaseOrder = OrderConverter.convertOrderModelToFirebaseOrder(orderModel);

        return FirebaseClient.pushOrderToFirebase(firebaseOrder);
    }
}
