package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;

/**
 * Created by mremondi on 2/28/18.
 */

public class MoreFragment extends Fragment {
    Unbinder unbinder;

    @OnClick(R.id.fragment_more_settings_row) void onSettingsClick(){
        Fragment settingsFragment = new SettingsFragment();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(settingsFragment, "SETTINGS")
                .addToBackStack("SETTINGS")
                .replace(R.id.content_frame, settingsFragment)
                .commit();

    }

    @OnClick(R.id.fragment_more_acknowledgements_row) void onAcknowledgementsClick(){
        startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));
    }

    @OnClick(R.id.fragment_more_about_us_row) void onAboutUsClick(){
        Fragment aboutUsFragment = new AboutUsFragment();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(aboutUsFragment, "ABOUTUS")
                .addToBackStack("ABOUTUS")
                .replace(R.id.content_frame, aboutUsFragment)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}