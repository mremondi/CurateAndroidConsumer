package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.presentation.ui.views.activities.OnBoardingWorkflowActivity;

/**
 * Created by mremondi on 2/17/18.
 */

public class OnBoardingFragmentPage3 extends Fragment {

    Unbinder unbinder;

    Map<CardView, TagTypeModel> cardToPreferenceMap = new HashMap<>();

    @OnClick({R.id.iced_card, R.id.hot_card, R.id.decaf_card, R.id.caffeinated_card}) void cardClick(CardView card){
        TagTypeModel preference = cardToPreferenceMap.get(card);
        if (!((OnBoardingWorkflowActivity)getActivity()).containsPreference(preference)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.selectedGreen));
            ((OnBoardingWorkflowActivity) getActivity()).addPreference(preference);
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.white));
            ((OnBoardingWorkflowActivity) getActivity()).removePreference(preference);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_page3, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        initializeCardPreferenceMap();
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initializeCardPreferenceMap(){
        // TODO: UN HARDCODE THIS STUFF

    }
}