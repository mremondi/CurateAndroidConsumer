package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.presentation.ui.views.activities.LoginActivity;
import curatetechnologies.com.curate.storage.UserRepository;

/**
 * Created by mremondi on 2/28/18.
 */

public class SettingsFragment extends Fragment {

    Unbinder unbinder;

    @OnClick(R.id.fragment_settings_sign_out_row) void onSettingsClick(){
        signOutUser();
    }

    @OnClick(R.id.fragment_settings_location_settings_row) void onAcknowledgementsClick(){
        Fragment locationFragment = new LocationFragment();

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(locationFragment, "LOCATION")
                .addToBackStack("LOCATION")
                .replace(R.id.content_frame, locationFragment)
                .commit();
    }

    @OnClick(R.id.fragment_settings_contact_us_row) void onAboutUsClick(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:contact@curatemeals.com");
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        unbinder = ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void signOutUser(){
        UserRepository.getInstance(getContext()).signOutUser();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
