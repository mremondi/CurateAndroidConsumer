package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.presenters.OnBoardUserContract;
import curatetechnologies.com.curate.presentation.presenters.OnBoardUserPresenter;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class OnBoardingWorkflowActivity extends AppCompatActivity implements OnBoardUserPresenter.View{

    private OnBoardUserContract mOnBoardUserPresenter;

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_workflow);

        mOnBoardUserPresenter = new OnBoardUserPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );
        mOnBoardUserPresenter.getCurrentUser();



        // TODO: Actually set up the UI for the workflow
    }

    @Override
    public void beginOnBoarding(UserModel user) {
        Log.d("ON BOARDING USER", user.getEmail());
    }

    @Override
    public void segueToMainApp() {

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
}
