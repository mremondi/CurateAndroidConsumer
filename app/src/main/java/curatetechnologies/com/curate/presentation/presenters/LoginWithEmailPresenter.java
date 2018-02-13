package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
 */

public class LoginWithEmailPresenter extends AbstractPresenter implements LoginWithEmailContract {

    private LoginWithEmailContract.View mView;
    private UserModelRepository mUserRepository;

    public LoginWithEmailPresenter(Executor executor, MainThread mainThread,
                                   LoginWithEmailContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void loginUserEmailPassword(String email, String password) {

    }
}
