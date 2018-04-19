package curatetechnologies.com.curate_consumer.modules.about_us;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;

/**
 * Created by mremondi on 2/28/18.
 */

public class AboutUsFragment extends Fragment {
    Unbinder unbinder;

    @OnClick(R.id.fragment_about_us_terms_button) void onTermsClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.curatemeals.com/termsofservice"));
        startActivity(browserIntent);
    }
    @OnClick(R.id.fragment_about_us_privacy_button) void onPrivacyClick(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.curatemeals.com/privacypolicy"));
        startActivity(browserIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this, v);

        return v;
    }

}
