package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class LoginOrSignUpActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_or_signup_bg)
    ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);

        ButterKnife.bind(this);
        Glide.with(this).load(getResources()
                .getDrawable(R.drawable.background_beverage_breakfast))
                .apply(bitmapTransform(new MultiTransformation(
                        new CenterCrop())))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        resource.setColorFilter(getResources().getColor(R.color.darkTransparent), PorterDuff.Mode.DARKEN);
                        backgroundImage.setBackground(resource);
                    }
                });
    }

    @OnClick(R.id.activity_login_or_signup_create_acount) void onCreateAccountClick(){
        //Intent i = new Intent(this, CreateAccountActivity.class);
        //startActivity(i);
    }

    @OnClick(R.id.activity_login_or_signup_login) void onLoginClick(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.activity_login_or_signup_skip) void onSignUpClick(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}