package curatetechnologies.com.curate_consumer.presentation.ui.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mremondi on 2/12/18.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginOrSignUpActivity.class);
        startActivity(intent);
        finish();
    }
}