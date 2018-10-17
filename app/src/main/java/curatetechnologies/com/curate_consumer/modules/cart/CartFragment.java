package curatetechnologies.com.curate_consumer.modules.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.model.Customer;
import com.stripe.android.model.CustomerSource;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.manager.CartManager;
import curatetechnologies.com.curate_consumer.modules.receipt.ReceiptFragment;
import curatetechnologies.com.curate_consumer.modules.search.SearchFragment;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.CartItemsAdapter;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.SwipeController;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.SwipeControllerActions;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.OrderRepository;
import curatetechnologies.com.curate_consumer.storage.PostRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantRepository;
import curatetechnologies.com.curate_consumer.storage.StripeRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

/**
 * Created by mremondi on 4/20/18.
 */

public class CartFragment extends Fragment implements CartContract.View {
    Unbinder unbinder;

    private CartPresenter mCartPresenter;

    private PaymentSession mPaymentSession;
    private Customer mCustomer;

    @BindView(R.id.fragment_cart_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.activity_cart_payment_session_data)
    TextView mResultTextView;
    @BindView(R.id.activity_cart_payment_label)
    TextView mPaymentLabel;
    @BindView(R.id.activity_cart_payment_please_add_card)
    TextView mAddCardLabel;
    @BindView(R.id.activity_cart_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.activity_cart_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.activity_cart_restaurant_address)
    TextView tvRestaurantAddress;
    @BindView(R.id.activity_cart_recycler_view)
    RecyclerView orderItemRecyclerView;
    @BindView(R.id.activity_cart_subtotal)
    TextView tvSubtotal;
    @BindView(R.id.activity_cart_tax)
    TextView tvTax;
    @BindView(R.id.activity_cart_total)
    TextView tvTotal;

    @BindView(R.id.activity_cart_complete_button)
    Button completePurchaseButton;


    @OnClick(R.id.activity_cart_clear_cart_button) void clearCart(){
        CartManager.getInstance().clearCart();

        Fragment searchFragment = new SearchFragment();
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_frame, searchFragment)
                .commit();

    }

    @OnClick(R.id.activity_cart_complete_button) void completePurchase(){
        UserModel user = UserRepository.getInstance(getContext()).getCurrentUser();
        String email = user.getEmail();
        mCartPresenter.completeCharge(mPaymentSession, email);
    }

    @OnClick(R.id.activity_cart_payment_row) void selectPaymentClick(){
        mPaymentSession.presentPaymentMethodSelection();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, v);

        mCustomer = CustomerSession.getInstance().getCachedCustomer();
        CartManager.getInstance().setUser(UserRepository.getInstance(getContext()).getCurrentUser());

        mCartPresenter = new CartPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository(),
                new StripeRepository(),
                new OrderRepository(),
                new PostRepository()
        );
        mCartPresenter.getRestaurantById(CartManager.getInstance().getRestaurantId(),
                getLocation(), getRadius());

        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }



    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPaymentSession.handlePaymentData(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPaymentSession.onDestroy();
    }

    private void initializePaymentSession(){
        mPaymentSession = new PaymentSession(getActivity());
        mPaymentSession.setCartTotal(CartManager.getInstance().getOrderTotalForStripe());
        boolean paymentSessionInitialized = mPaymentSession.init(new PaymentSession.PaymentSessionListener() {
            @Override
            public void onCommunicatingStateChanged(boolean isCommunicating) {
                if (isCommunicating) {
                    // mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    // mProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onError(int errorCode, @Nullable String errorMessage) {
                //mErrorDialogHandler.showError(errorMessage);
                Log.d("ERROR", errorMessage);
            }

            @Override
            public void onPaymentSessionDataChanged(@NonNull PaymentSessionData data) {
                if (data.getPaymentResult().equals("success")){
                    // Payment has been completed!!
                    Log.d("PAYMENT RESULT SUCCESS", data.getPaymentResult());


                    // TODO: NOTIFY THAT THE ORDER HAS BEEN PLACED
                }
                if (data.isPaymentReadyToCharge()){
                    Log.d("HERE", "PAYMENT READY TO CHARGE");
                    showCardDetails(data);
                    completePurchaseButton.setBackgroundColor(getResources().getColor(R.color.activeBlue));
                    completePurchaseButton.setEnabled(true);
                } else {
                    mResultTextView.setVisibility(View.GONE);
                    mPaymentLabel.setVisibility(View.GONE);
                    mAddCardLabel.setVisibility(View.VISIBLE);
                    completePurchaseButton.setEnabled(false);
                    Log.d("PAYMENT", "NOT READY TO CHARGE");

                }
            }
        }, new PaymentSessionConfig.Builder()
                .setShippingInfoRequired(false)
                .setShippingMethodsRequired(false)
                .build());
        if (paymentSessionInitialized) {
            Log.d("HERE", "paymentSessionInitialized");
        }
    }

    public boolean isActive(){
        return isAdded();
    }

    @Override
    public void displayRestaurant(RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantAddress.setText(restaurant.getStreetAddress());

        CartManager.getInstance().setMealTax(restaurant.getMealTaxRate());

        setUpRecyclerView();

        updateUI();

        initializePaymentSession();

    }

    @Override
    public void chargeCompleted(boolean success) {
        mCartPresenter.processOrder(UserRepository
                        .getInstance(getContext())
                        .getCurrentUser()
                        .getCurateToken(),
                CartManager.getInstance().createOrderModel(),
                getContext());
    }

    @Override
    public void orderProcessed() {
        final Context context = getContext();

        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("Order Successful");
        alertDialog.setMessage("You will receive a notification when your order status changes. Enjoy!");
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Fragment receiptFragment = new ReceiptFragment();
                        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction()
                                .replace(R.id.content_frame, receiptFragment)
                                .commit();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

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

    private void showCardDetails(PaymentSessionData data) {
        StringBuilder stringBuilder = new StringBuilder();
        if (data.getSelectedPaymentMethodId() != null && mCustomer != null) {
            CustomerSource source = mCustomer.getSourceById(data.getSelectedPaymentMethodId());
            if (source != null) {
                Source cardSource = source.asSource();
                if (cardSource != null) {
                    SourceCardData scd = (SourceCardData) cardSource.getSourceTypeModel();
                    stringBuilder.append(scd.getBrand())
                            .append(" ")
                            .append(scd.getLast4());
                }
            }
        }
        mAddCardLabel.setVisibility(View.GONE);
        mResultTextView.setVisibility(View.VISIBLE);
        mPaymentLabel.setVisibility(View.VISIBLE);
        mResultTextView.setText(stringBuilder.toString());
    }

    private void updateUI(){
        tvSubtotal.setText("$" + String.format("%.2f", CartManager.getInstance().getSubTotal()));
        tvTax.setText("$" + String.format("%.2f",CartManager.getInstance().getOrderTax()));
        tvTotal.setText("$" + String.format("%.2f",CartManager.getInstance().getOrderTotal()));
    }

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private Float getRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }
}
