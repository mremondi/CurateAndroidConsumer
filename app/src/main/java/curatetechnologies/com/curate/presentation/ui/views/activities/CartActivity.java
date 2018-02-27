package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentCompletionProvider;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Customer;
import com.stripe.android.model.CustomerSource;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.presentation.presenters.CartContract;
import curatetechnologies.com.curate.presentation.presenters.CartPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.CartItemsAdapter;
import curatetechnologies.com.curate.storage.RestaurantRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

import static com.stripe.android.PayWithGoogleUtils.getPriceString;

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
        mPaymentSession.completePayment(new PaymentCompletionProvider() {
            @Override
            public void completePayment(@NonNull PaymentSessionData data, @NonNull PaymentResultListener listener) {
                // TODO:
            }
        });
    }
    @BindView(R.id.activity_cart_select_payment_button)
    Button selectPaymentButton;

    @OnClick(R.id.activity_cart_select_payment_button) void selectPaymentClick(){
        mPaymentSession.presentPaymentMethodSelection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        mCustomer = CustomerSession.getInstance().getCachedCustomer();

        mCartPresenter = new CartPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository()
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
                mResultTextView.setText(formatStringResults(mPaymentSession.getPaymentSessionData()));
                if (data.isPaymentReadyToCharge()){
                    Log.d("PAYMENT", "READY TO CHARGE");
                    mResultTextView.setVisibility(View.VISIBLE);
                    selectPaymentButton.setVisibility(View.GONE);
                    completePurchaseButton.setEnabled(true);
                } else {
                    mResultTextView.setVisibility(View.GONE);
                    selectPaymentButton.setVisibility(View.VISIBLE);
                    completePurchaseButton.setEnabled(false);
                    Log.d("PAYMENT", "NOT READY TO CHARGE");

                }
            }
        }, new PaymentSessionConfig.Builder()
                .setShippingInfoRequired(false)
                .build());
        if (paymentSessionInitialized) {
            selectPaymentButton.setEnabled(true);
            selectPaymentButton.setBackgroundColor(getResources().getColor(R.color.activeBlue));
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    private String formatStringResults(PaymentSessionData data) {
        Currency currency = Currency.getInstance("USD");
        StringBuilder stringBuilder = new StringBuilder();

        if (data.getSelectedPaymentMethodId() != null && mCustomer != null) {
            CustomerSource source = mCustomer.getSourceById(data.getSelectedPaymentMethodId());
            if (source != null) {
                Source cardSource = source.asSource();
                stringBuilder.append("Payment Info:\n");
                if (cardSource != null) {
                    SourceCardData scd = (SourceCardData) cardSource.getSourceTypeModel();
                    stringBuilder.append(scd.getBrand())
                            .append(" ending in ")
                            .append(scd.getLast4());
                } else {
                    stringBuilder.append('\n').append(source.toString()).append('\n');
                }
                String isOrNot = data.isPaymentReadyToCharge() ? " IS " : " IS NOT ";
                stringBuilder.append(isOrNot).append("ready to charge.\n\n");
            }
        }
        if (data.getShippingInformation() != null) {
            stringBuilder.append("Shipping Info: \n");
            stringBuilder.append(data.getShippingInformation());
            stringBuilder.append("\n\n");
        }
        if (data.getShippingMethod() != null) {
            stringBuilder.append("Shipping Method: \n");
            stringBuilder.append(data.getShippingMethod()).append('\n');
            if (data.getShippingTotal() > 0) {
                stringBuilder.append("Shipping total: ")
                        .append(getPriceString(data.getShippingTotal(), currency));
            }
        }

        return stringBuilder.toString();
    }
}
