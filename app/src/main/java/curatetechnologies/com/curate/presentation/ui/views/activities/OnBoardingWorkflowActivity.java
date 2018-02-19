package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.presenters.OnBoardUserContract;
import curatetechnologies.com.curate.presentation.presenters.OnBoardUserPresenter;
import curatetechnologies.com.curate.presentation.ui.views.fragments.OnBoardingFragmentPage0;
import curatetechnologies.com.curate.presentation.ui.views.fragments.OnBoardingFragmentPage1;
import curatetechnologies.com.curate.presentation.ui.views.fragments.OnBoardingFragmentPage2;
import curatetechnologies.com.curate.presentation.ui.views.fragments.OnBoardingFragmentPage3;
import curatetechnologies.com.curate.presentation.ui.views.fragments.OnBoardingFragmentPage4;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class OnBoardingWorkflowActivity extends FragmentActivity implements OnBoardUserPresenter.View{

    private static final int NUM_PAGES = 5;

    private ArrayList<TagTypeModel> preferences = new ArrayList<>();

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    public PagerAdapter mPagerAdapter;

    private OnBoardUserContract mOnBoardUserPresenter;

    public UserModel user;

    @BindView(R.id.pager)
    public ViewPager mPager;
    @BindView(R.id.view_pager_indicator)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_workflow);
        ButterKnife.bind(this);

        mOnBoardUserPresenter = new OnBoardUserPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );
        mOnBoardUserPresenter.getCurrentUser();

        mPagerAdapter = new OnBoardingPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager, true);
        // disable tab clicks
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
    }

    public void addPreference(TagTypeModel preference){
        this.preferences.add(preference);
    }

    public boolean containsPreference(TagTypeModel preference){
        return this.preferences.contains(preference);
    }

    public void removePreference(TagTypeModel preference){
        this.preferences.remove(preference);
    }


    @Override
    public void beginOnBoarding(UserModel user) {
        Log.d("USER MODEL", user.getEmail());
        this.user = user;
    }

    public void completeOnBoarding(){
        mOnBoardUserPresenter.saveUser(user);
        mOnBoardUserPresenter.saveUserPreferences(preferences);
    }

    @Override
    public void segueToMainApp() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
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


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class OnBoardingPagerAdapter extends FragmentStatePagerAdapter {
        public OnBoardingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new OnBoardingFragmentPage0();
                case 1:
                    return new OnBoardingFragmentPage1();
                case 2:
                    return new OnBoardingFragmentPage2();
                case 3:
                    return new OnBoardingFragmentPage3();
                case 4:
                    return new OnBoardingFragmentPage4();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
