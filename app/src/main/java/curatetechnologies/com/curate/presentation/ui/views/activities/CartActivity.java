package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentCompletionProvider;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.Stripe;
import com.stripe.android.StripeNetworkUtils;
import com.stripe.android.model.Customer;
import com.stripe.android.model.CustomerSource;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.network.stripe.StripeChargeProvider;
import curatetechnologies.com.curate.presentation.presenters.CartContract;
import curatetechnologies.com.curate.presentation.presenters.CartPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.CartItemsAdapter;
import curatetechnologies.com.curate.storage.OrderRepository;
import curatetechnologies.com.curate.storage.RestaurantRepository;
import curatetechnologies.com.curate.storage.StripeRepository;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private CartPresenter mCartPresenter;

    private PaymentSession mPaymentSession;
    private Customer mCustomer;

    @BindView(R.id.activity_cart_payment_session_data)
    TextView mResultTextView;
    @BindView(R.id.activity_cart_restaurant_logo)
    ImageView ivRestaurantLogo;
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
    @OnClick(R.id.activity_cart_complete_button) void completePurchase(){
        // TODO:
        UserModel user = UserRepository.getInstance(getApplicationContext()).getCurrentUser();
        String email = user.getEmail();
        mCartPresenter.completeCharge(mPaymentSession, email);
    }

    @OnClick(R.id.activity_cart_payment_row) void selectPaymentClick(){
        mPaymentSession.presentPaymentMethodSelection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        mCustomer = CustomerSession.getInstance().getCachedCustomer();
        CartManager.getInstance().setUser(UserRepository.getInstance(getApplicationContext()).getCurrentUser());

        mCartPresenter = new CartPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository(),
                new StripeRepository(),
                new OrderRepository()
        );
        mCartPresenter.getRestaurantById(CartManager.getInstance().getRestaurantId());

        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPaymentSession.handlePaymentData(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPaymentSession.onDestroy();
    }

    private void initializePaymentSession(){
        mPaymentSession = new PaymentSession(this);
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
                Log.d("PAYMENT RESULT", data.getPaymentResult());
                if (data.getPaymentResult().equals("success")){
                    // Payment has been completed!!
                    Log.d("PAYMENT RESULT SUCCESS", data.getPaymentResult());
                }
                if (data.isPaymentReadyToCharge()){
                    showCardDetails(data);
                    completePurchaseButton.setBackgroundColor(getResources().getColor(R.color.activeBlue));
                    completePurchaseButton.setEnabled(true);
                } else {
                    mResultTextView.setVisibility(View.GONE);
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

    @Override
    public void displayRestaurant(RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);
        CartManager.getInstance().setMealTax(restaurant.getMealTaxRate());
        orderItemRecyclerView.setAdapter(new CartItemsAdapter(CartManager.getInstance().getOrderItems()));
        tvSubtotal.setText("$" + CartManager.getInstance().getSubTotal().toString());
        tvTax.setText("$" + CartManager.getInstance().getOrderTax().toString());
        tvTotal.setText("$" + CartManager.getInstance().getOrderTotal().toString());

        initializePaymentSession();

    }

    @Override
    public void chargeCompleted(boolean success) {
        Log.d("CHARGE COMPLETE", "NOW FIREBASE");
        // TODO: send to firebase
        mCartPresenter.processOrder(CartManager.getInstance().createOrderModel());
    }

    @Override
    public void orderProcessed() {
        // TODO: indicate the order has been completed and move back to main app
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        android.app.AlertDialog.Builder builder;
        builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Order Cannot Be Processed")
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
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

        mResultTextView.setVisibility(View.VISIBLE);
        mResultTextView.setText(stringBuilder.toString());
    }
}
