package curatetechnologies.com.curate.presentation.ui.views.fragments;

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
import curatetechnologies.com.curate.presentation.ui.views.activities.OnBoardingWorkflowActivity;

/**
 * Created by mremondi on 2/16/18.
 */

public class OnBoardingFragmentPage1 extends Fragment {

    Unbinder unbinder;


    @BindView(R.id.fragment_onboarding_page1_username)
    EditText username;
    @BindView(R.id.fragment_onboarding_page1_first_name)
    EditText firstName;
    @BindView(R.id.fragment_onboarding_page1_last_name)
    EditText lastName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_onboarding_page1, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean validateUsername(){
        // TODO: Actually implement this method. Check the validity of the string and check that it's available on our backend
        return true;
    }
}
