package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
 * Created by mremondi on 2/16/18.
 */

public class OnBoardingFragmentPage2 extends Fragment {

    Unbinder unbinder;

    Map<CardView, TagTypeModel> cardToPreferenceMap = new HashMap<>();

    @BindView(R.id.espresso_card)
    CardView espressoCard;
    @BindView(R.id.brewed_card)
    CardView brewedCard;
    @BindView(R.id.tea_card)
    CardView teaCard;

    @OnClick({R.id.espresso_card, R.id.brewed_card, R.id.tea_card}) void espressoClick(CardView card){
        TagTypeModel preference = cardToPreferenceMap.get(card);
        if (!((OnBoardingWorkflowActivity)getActivity()).containsPreference(preference)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.selectedGreen));
            ((OnBoardingWorkflowActivity) getActivity()).addPreference(preference);
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.white));
            ((OnBoardingWorkflowActivity) getActivity()).removePreference(preference);
        }
    }

    @OnClick(R.id.fragment_onboarding_page2_next_button) void nextPage(){
        ((OnBoardingWorkflowActivity)getActivity()).mPager.setCurrentItem(3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_page2, container, false);

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
        cardToPreferenceMap.put(espressoCard, new TagTypeModel(9, "Espresso", ""));
        cardToPreferenceMap.put(espressoCard, new TagTypeModel(10, "Brewed Coffee", ""));
        cardToPreferenceMap.put(espressoCard, new TagTypeModel(11, "Tea", ""));
    }
}