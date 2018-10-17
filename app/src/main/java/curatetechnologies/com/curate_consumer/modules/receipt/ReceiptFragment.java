package curatetechnologies.com.curate_consumer.modules.receipt;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.manager.CartManager;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.CartItemsAdapter;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.SwipeController;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.SwipeControllerActions;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

/**
 * Created by mremondi on 4/20/18.
 */

public class ReceiptFragment extends Fragment implements ReceiptContract.View {
    Unbinder unbinder;

    private ReceiptContract mReceiptPresenter;

    @BindView(R.id.fragment_receipt_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.fragment_receipt_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.fragment_receipt_restaurant_address)
    TextView tvRestaurantAddress;
    @BindView(R.id.fragment_receipt_recycler_view)
    RecyclerView orderItemRecyclerView;
    @BindView(R.id.fragment_receipt_subtotal)
    TextView tvSubtotal;
    @BindView(R.id.fragment_receipt_tax)
    TextView tvTax;
    @BindView(R.id.fragment_receipt_total)
    TextView tvTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_receipt, container, false);
        unbinder = ButterKnife.bind(this, v);

        CartManager.getInstance().setUser(UserRepository.getInstance(getContext()).getCurrentUser());

        mReceiptPresenter = new ReceiptPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository()
        );
        mReceiptPresenter.getRestaurantById(CartManager.getInstance().getRestaurantId(),
                getLocation(), getRadius());

        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void displayRestaurant(RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantAddress.setText(restaurant.getStreetAddress());

        CartManager.getInstance().setMealTax(restaurant.getMealTaxRate());

        setUpRecyclerView();

        updateUI();
    }

    // -- BEGIN BASEVIEW CONTRACT METHODS


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        android.support.v7.app.AlertDialog.Builder builder;
        builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("Order Cannot Be Processed")
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setUpRecyclerView(){
        final CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(CartManager.getInstance().getOrderItems());
        orderItemRecyclerView.setAdapter(cartItemsAdapter);

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                cartItemsAdapter.orderItems.remove(position);
                CartManager.getInstance().removeItemAtIndex(position);
                cartItemsAdapter.notifyItemRemoved(position);
                cartItemsAdapter.notifyItemRangeChanged(position, cartItemsAdapter.getItemCount());
                updateUI();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(orderItemRecyclerView);

        orderItemRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void updateUI(){
        tvSubtotal.setText("$" + String.format("%.2f", CartManager.getInstance().getSubTotal()));
        tvTax.setText("$" + String.format("%.2f",CartManager.getInstance().getOrderTax()));
        tvTotal.setText("$" + String.format("%.2f",CartManager.getInstance().getOrderTotal()));

        CartManager.getInstance().clearCart();
    }

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private Float getRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }

}