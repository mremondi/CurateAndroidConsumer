package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.presentation.ui.views.activities.OnBoardingWorkflowActivity;

/**
 * Created by mremondi on 2/16/18.
 */

public class OnBoardingFragmentPage0 extends Fragment {
    OnBoardingWorkflowActivity activity;
    Unbinder unbinder;

    @OnClick(R.id.fragment_onboarding_page0_next_button) void nextClick(){
        activity.mPager.setCurrentItem(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_onboarding_page0, container, false);

        unbinder = ButterKnife.bind(this, rootView);
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
}