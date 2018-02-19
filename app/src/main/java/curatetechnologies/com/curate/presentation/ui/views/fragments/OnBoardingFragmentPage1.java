package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.presentation.presenters.OnBoardingFragment1Contract;
import curatetechnologies.com.curate.presentation.presenters.OnBoardingFragment1Presenter;
import curatetechnologies.com.curate.presentation.ui.views.activities.OnBoardingWorkflowActivity;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

/**
 * Created by mremondi on 2/16/18.
 */

public class OnBoardingFragmentPage1 extends Fragment implements OnBoardingFragment1Contract.View {
    OnBoardingWorkflowActivity activity;
    Unbinder unbinder;
    private OnBoardingFragment1Contract mOnBoardUserPresenter;

    @BindView(R.id.fragment_onboarding_page1_username)
    EditText username;
    @BindView(R.id.fragment_onboarding_page1_first_name)
    EditText firstName;
    @BindView(R.id.fragment_onboarding_page1_last_name)
    EditText lastName;

    @OnClick(R.id.fragment_onboarding_page1_next_button) void nextClick(){
        mOnBoardUserPresenter.checkUsernameAvailable(username.getText().toString());
        activity.mPager.setCurrentItem(2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_onboarding_page1, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        mOnBoardUserPresenter = new OnBoardingFragment1Presenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(activity.getApplicationContext())
        );
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OnBoardingWorkflowActivity) context;
    }

    @Override
    public void usernameAvailable(Boolean available) {
        if (available){
            activity.user.setUsername(username.getText().toString());
            activity.user.setFirstName(firstName.getText().toString());
            activity.user.setLastName(firstName.getText().toString());
            activity.mPager.setCurrentItem(2);
        }
        else{
            username.getText().clear();
            username.setHintTextColor(getResources().getColor(R.color.colorAccent));
            username.setHint("Username taken. Please try another username");
        }
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
