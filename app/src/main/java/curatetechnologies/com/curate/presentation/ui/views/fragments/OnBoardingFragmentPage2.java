package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
   OnBoardingWorkflowActivity activity;

    Unbinder unbinder;

    Map<CardView, TagTypeModel> cardToPreferenceMap = new HashMap<>();

    @BindView(R.id.espresso_card)
    CardView espressoCard;
    @BindView(R.id.brewed_card)
    CardView brewedCard;
    @BindView(R.id.tea_card)
    CardView teaCard;

    @OnClick(R.id.fragment_onboarding_page2_next_button) void nextClick(){
        activity.mPager.setCurrentItem(3);
    }

    @OnClick({R.id.espresso_card, R.id.brewed_card, R.id.tea_card}) void cardClick(CardView card){
        TagTypeModel preference = cardToPreferenceMap.get(card);
        if (!activity.containsPreference(preference)) {
            card.setCardBackgroundColor(getResources().getColor(R.color.selectedGreen));
            activity.addPreference(preference);
        } else {
            card.setCardBackgroundColor(getResources().getColor(R.color.white));
            activity.removePreference(preference);
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OnBoardingWorkflowActivity) context;
    }

    public void initializeCardPreferenceMap(){
        // TODO: UN HARDCODE THIS STUFF
        cardToPreferenceMap.put(espressoCard, new TagTypeModel(8, "Espresso", ""));
        cardToPreferenceMap.put(brewedCard, new TagTypeModel(9, "Brewed Coffee", ""));
        cardToPreferenceMap.put(teaCard, new TagTypeModel(10, "Tea", ""));
    }
}