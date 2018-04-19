package curatetechnologies.com.curate_consumer.modules.cart;

import android.content.Context;

import com.stripe.android.PaymentSession;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CompleteChargeInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CompleteChargeInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.PostOrderInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.PostOrderInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SendOrderToRestaurantInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SendOrderToRestaurantInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;
import curatetechnologies.com.curate_consumer.storage.StripeModelRepository;

/**
 * Created by mremondi on 2/26/18.
 */

public class CartPresenter extends AbstractPresenter implements CartContract,
        GetRestaurantByIdInteractor.Callback, CompleteChargeInteractor.Callback,
        SendOrderToRestaurantInteractor.Callback, PostOrderInteractor.Callback{

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
    public void processOrder(String jwt, OrderModel orderModel, Context appContext) {
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

        PostOrderInteractor postOrderInteractor = new PostOrderInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mOrderRepository,
                jwt,
                orderModel,
                appContext
        );
        postOrderInteractor.execute();
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
        // TODO:
        mView.hideProgress();
        mView.orderProcessed();
    }
    // -- END: SendOrderToRestaurantInteractor.Callback methods

    // -- BEGIN: PostOrderInteractor.Callback methods

    @Override
    public void onOrderPosted() {
        // TODO:
    }

    @Override
    public void onOrderPostFailed(String error) {
        mView.hideProgress();
        onError(error);
    }

    // -- END: PostOrderInteractor.Callback methods
}