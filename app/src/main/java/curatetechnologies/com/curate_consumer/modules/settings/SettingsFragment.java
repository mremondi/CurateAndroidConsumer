package curatetechnologies.com.curate_consumer.modules.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.modules.location_preferences.LocationFragment;
import curatetechnologies.com.curate_consumer.modules.login_or_signup.LoginOrSignUpActivity;
import curatetechnologies.com.curate_consumer.storage.UserRepository;

/**
 * Created by mremondi on 2/28/18.
 */

public class SettingsFragment extends Fragment {

    Unbinder unbinder;

    @OnClick(R.id.fragment_settings_sign_out_row) void onSettingsClick(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Sign Out");
        alertDialog.setMessage("Are you sure you would like to sign out?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Sign Out",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        signOutUser();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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
        Intent i = new Intent(getContext(), LoginOrSignUpActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
