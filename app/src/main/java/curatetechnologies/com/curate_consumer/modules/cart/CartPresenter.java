package curatetechnologies.com.curate_consumer.modules.cart;

import android.content.Context;
import android.location.Location;

import com.stripe.android.PaymentSession;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CompleteChargeInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CompleteChargeInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetRestaurantByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.PostOrderInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.PostOrderInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SendOrderToRestaurantInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SendOrderToRestaurantInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;
import curatetechnologies.com.curate_consumer.storage.StripeModelRepository;

/**
 * Created by mremondi on 2/26/18.
 */

public class CartPresenter extends AbstractPresenter implements CartContract,
        GetRestaurantByIdInteractor.Callback, CompleteChargeInteractor.Callback,
        SendOrderToRestaurantInteractor.Callback, PostOrderInteractor.Callback,
        CreatePostInteractor.Callback{

    private CartContract.View mView;
    private RestaurantModelRepository mRestaurantRepository;
    private StripeModelRepository mStripeRepository;
    private OrderModelRepository mOrderRepository;
    private PostModelRepository mPostRepository;

    private Location mLocation;
    private Float mRadius;

    public CartPresenter(Executor executor, MainThread mainThread,
                         View view, RestaurantModelRepository restaurantModelRepository,
                         StripeModelRepository stripeRepository,
                         OrderModelRepository orderModelRepository,
                         PostModelRepository postModelRepository) {
        super(executor, mainThread);
        mView = view;
        mRestaurantRepository = restaurantModelRepository;
        mStripeRepository = stripeRepository;
        mOrderRepository = orderModelRepository;
        mPostRepository = postModelRepository;
    }

    // -- BEGIN: CartContract methods
    @Override
    public void getRestaurantById(Integer restaurantId, Location location, Float radiusMiles) {
        if (mView.isActive()) {
            mView.showProgress();
            GetRestaurantByIdInteractor restaurantInteractor = new GetRestaurantByIdInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mRestaurantRepository,
                    restaurantId,
                    location,
                    radiusMiles
            );
            restaurantInteractor.execute();
        }
    }

    @Override
    public void completeCharge(PaymentSession paymentSession, final String email) {
        if (mView.isActive()) {
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
    }

    @Override
    public void processOrder(String jwt, OrderModel orderModel, Context appContext) {
        if (mView.isActive()) {
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

            PostModel postModel = new PostModel(0, PostModel.ORDER_POST,
                    orderModel.getRestaurantId(), orderModel.getOrderItems().get(0).getId(),
                    "", null, 0, 0,
                    "", "", orderModel.getUser().getId(),
                    orderModel.getUser().getUsername(), orderModel.getUser().getProfilePictureURL(),
                    "", "", 0.0, null);

            CreatePostInteractor createPostInteractor = new CreatePostInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mPostRepository,
                    jwt,
                    postModel
            );
            createPostInteractor.execute();
        }
    }

    // -- END: CartContract methods

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurant) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayRestaurant(restaurant);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        mView.showError(error);
    }
    // -- END: GetItemByIdInteractor.Callback methods


    // -- BEGIN: CompleteChargeInteractor.Callback methods
    @Override
    public void onChargeSuccessful() {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.chargeCompleted(true);
        }
    }

    @Override
    public void onChargeFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }

    // -- END: CompleteChargeInteractor.Callback methods

    // -- BEGIN: SendOrderToRestaurantInteractor.Callback methods
    @Override
    public void onOrderSent() {
        // TODO:
        if (mView.isActive()) {
            mView.hideProgress();
            mView.orderProcessed();
        }
    }
    // -- END: SendOrderToRestaurantInteractor.Callback methods


    // -- BEGIN: PostOrderInteractor.Callback methods
    @Override
    public void onOrderPosted() {
        // TODO:
    }

    @Override
    public void onOrderPostFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: PostOrderInteractor.Callback methods


    // -- BEGIN: CreatePostInteractor.Callback methods
    @Override
    public void onCreatePostSuccess() {

    }

    @Override
    public void onCreatePostFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: CreatePostInteractor.Callback methods
}