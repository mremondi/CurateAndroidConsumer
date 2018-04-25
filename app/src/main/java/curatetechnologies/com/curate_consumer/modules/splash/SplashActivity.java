package curatetechnologies.com.curate_consumer.modules.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import curatetechnologies.com.curate_consumer.modules.login_or_signup.LoginOrSignUpActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by mremondi on 2/12/18.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        Intent intent = new Intent(this, LoginOrSignUpActivity.class);
        startActivity(intent);
        finish();
    }
}