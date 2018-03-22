package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetUserByIDInteractor;
import curatetechnologies.com.curate.domain.interactor.GetUserByIDInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 3/22/18.
 */

public class MainActivityPresenter extends AbstractPresenter implements MainActivityContract, GetUserByIDInteractor.Callback {

    private MainActivityContract.View mView;
    private UserModelRepository mUserRepository;

    public MainActivityPresenter(Executor executor,
                                 MainThread mainThread,
                                 MainActivityContract.View view,
                                 UserModelRepository userModelRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userModelRepository;
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
    public void onUserRetrieved(UserModel userModel) {
        mView.updateUser(userModel);
    }

    @Override
    public void onRetrievalFailed(String error) {
        // todo
    }
}
