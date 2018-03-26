package curatetechnologies.com.curate.presentation.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentCompletionProvider;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionData;

import java.util.ArrayList;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.CompleteChargeInteractor;
import curatetechnologies.com.curate.domain.interactor.CompleteChargeInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SendOrderToRestaurantInteractor;
import curatetechnologies.com.curate.domain.interactor.SendOrderToRestaurantInteractorImpl;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.storage.OrderModelRepository;
import curatetechnologies.com.curate.storage.RestaurantModelRepository;
import curatetechnologies.com.curate.storage.StripeModelRepository;
import curatetechnologies.com.curate.storage.StripeRepository;
import curatetechnologies.com.curate.storage.UserRepository;

/**
 * Created by mremondi on 2/26/18.
 */

public class CartPresenter extends AbstractPresenter implements CartContract,
        GetRestaurantByIdInteractor.Callback, CompleteChargeInteractor.Callback,
        SendOrderToRestaurantInteractor.Callback{

    private CartContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;
    private StripeModelRepository mStripeRepository;
    private OrderModelRepository mOrderRepository;

    public CartPresenter(Executor executor, MainThread mainThread,
                         View view, RestaurantModelRepository restaurantModelRepository,
                         StripeModelRepository stripeRepository,
                         OrderModelRepository orderModelRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantModelRepository;
        mStripeRepository = stripeRepository;
        mOrderRepository = orderModelRepository;
    }

    // -- BEGIN: CartContract methods
    @Override
    public void getRestaurantById(Integer restaurantId) {
        mView.showProgress();
        GetRestaurantByIdInteractor restaurantInteractor = new GetRestaurantByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mRestaurantRepository,
                restaurantId
        );
        restaurantInteractor.execute();
    }

    @Override
    public void completeCharge(PaymentSession paymentSession, final String email) {
        mView.showProgress();
        CompleteChargeInteractor completeChargeInteractor = new CompleteChargeInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mStripeRepository,
                paymentSession,
                email);
        completeChargeInteractor.execute();
    }

    @Override
    public void processOrder(OrderModel orderModel) {
        mView.showProgress();
        SendOrderToRestaurantInteractor sendOrderToRestaurantInteractor =
                new SendOrderToRestaurantInteractorImpl(
                        mExecutor,
                        mMainThread,
                        this,
                        mOrderRepository,
                        orderModel
                );
        sendOrderToRestaurantInteractor.execute();

//        PostOrderInteractor postOrderInteractor = new PostOrderInteractorImpl(
//
//        );
//        postOrderInteractor.execute();
    }

    // -- END: CartContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurant) {
        mView.hideProgress();
        mView.displayRestaurant(restaurant);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetItemByIdInteractor.Callback methods


    // -- BEGIN: CompleteChargeInteractor.Callback methods
    @Override
    public void onChargeSuccessful() {
        mView.hideProgress();
        mView.chargeCompleted(true);
    }

    @Override
    public void onChargeFailed(String error) {
        mView.hideProgress();
        onError(error);
    }

    // -- END: CompleteChargeInteractor.Callback methods

    // -- BEGIN: SendOrderToRestaurantInteractor.Callback methods

    @Override
    public void onOrderSent() {

    }

    // -- END: SendOrderToRestaurantInteractor.Callback methods

}