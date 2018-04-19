package curatetechnologies.com.curate_consumer.presentation.ui.views.fragments;

import android.content.Context;
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
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.activities.OnBoardingWorkflowActivity;

/**
 * Created by mremondi on 2/17/18.
 */

public class OnBoardingFragmentPage3 extends Fragment {
    OnBoardingWorkflowActivity activity;
    Unbinder unbinder;

    Map<CardView, TagTypeModel> cardToPreferenceMap = new HashMap<>();

    @BindView(R.id.iced_card)
    CardView icedCard;
    @BindView(R.id.hot_card)
    CardView hotCard;
    @BindView(R.id.decaf_card)
    CardView decafCard;
    @BindView(R.id.caffeinated_card)
    CardView caffeinatedCard;

    @OnClick(R.id.fragment_onboarding_page3_next_button) void nextClick(){
        activity.mPager.setCurrentItem(4);

    }

    @OnClick({R.id.iced_card, R.id.hot_card, R.id.decaf_card, R.id.caffeinated_card}) void cardClick(CardView card){
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_page3, container, false);

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
        cardToPreferenceMap.put(icedCard, new TagTypeModel(2, "Cold Drink", ""));
        cardToPreferenceMap.put(hotCard, new TagTypeModel(1, "Hot Drink", ""));
        cardToPreferenceMap.put(caffeinatedCard, new TagTypeModel(3, "Caffeinated", ""));
        cardToPreferenceMap.put(decafCard, new TagTypeModel(4, "Decaf", ""));
    }
}