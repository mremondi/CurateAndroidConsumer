package curatetechnologies.com.curate_consumer.modules.main;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetLastOrderInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetLastOrderInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserByIDInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserByIDInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 3/22/18.
 */

public class MainActivityPresenter extends AbstractPresenter implements MainActivityContract,
        GetUserByIDInteractor.Callback,
        GetLastOrderInteractor.Callback {

    private MainActivityContract.View mView;
    private UserModelRepository mUserRepository;
    private OrderModelRepository mOrderRepository;

    public MainActivityPresenter(Executor executor,
                                 MainThread mainThread,
                                 MainActivityContract.View view,
                                 UserModelRepository userModelRepository,
                                 OrderModelRepository orderModelRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userModelRepository;
        mOrderRepository = orderModelRepository;
    }

    @Override
    public void getUserById(Integer userId) {
        GetUserByIDInteractor menuInteractor = new GetUserByIDInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                userId
        );
        menuInteractor.execute();
    }

    @Override
    public void getLastOrder(Context appContext) {
        GetLastOrderInteractor getLastOrderInteractor = new GetLastOrderInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mOrderRepository,
                appContext
        );
        getLastOrderInteractor.execute();
    }

    @Override
    public void onUserRetrieved(UserModel userModel) {
        mView.updateUser(userModel);
    }

    @Override
    public void onRetrievalFailed(String error) {
        // todo
    }

    @Override
    public void onOrderModelRetrieved(OrderModel orderModel) {
        mView.rateLastOrder(orderModel);
    }
}
