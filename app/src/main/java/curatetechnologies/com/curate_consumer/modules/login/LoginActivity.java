package curatetechnologies.com.curate_consumer.modules.login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate_consumer.BuildConfig;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.network.converters.oauth.FacebookUserConverter;
import curatetechnologies.com.curate_consumer.network.converters.oauth.GoogleUserConverter;
import curatetechnologies.com.curate_consumer.modules.login_with_email.LoginWithEmailActivity;
import curatetechnologies.com.curate_consumer.modules.main.MainActivity;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static boolean isActive = false;

    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final Integer RC_SIGN_IN = 0;

    private LoginContract mLoginPresenter;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.activity_login_bg)
    ImageView backgroundImage;

    @BindView(R.id.btn_login_facebook)
    LoginButton btnFacebookLogin;

    @OnClick(R.id.btn_login_facebook_custom)
    void btnFacebookCustomClick() {
        btnFacebookLogin.performClick();
    }

    @OnClick(R.id.btn_login_google_custom)
    void btnGoogleLoginCustom() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.btn_login_email)
    void btnLoginEmailClick() {
        // go to Register with email password screen
        Intent i = new Intent(this, LoginWithEmailActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );
        mLoginPresenter.getCurrentUser();

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

        setUpFacebookLoginCallback();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_OAUTH_SERVER_CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
        // FACEBOOK
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // GOOGLE
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    }

    @Override
    public void segueToMainApp() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void segueToOnboarding() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // -- BEGIN: FACEBOOK LOGIN METHODS
    private void setUpFacebookLoginCallback() {
        btnFacebookLogin.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                handleFacebookLoginSuccess(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "An Error occurred " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookLoginSuccess(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", object.toString());
                        UserModel user = FacebookUserConverter.apply(object, loginResult.getAccessToken().getToken());

                        mLoginPresenter.signUpWithFacebook(user);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, first_name, last_name, email, gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
    // -- END: FACEBOOK LOGIN METHODS

    // -- BEGIN: GOOGLE LOGIN METHODS
    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            UserModel userModel = GoogleUserConverter.apply(account);
            mLoginPresenter.signUpWithGoogle(userModel);
        } catch (ApiException e) {
            Log.d("Error Google Account", e.getLocalizedMessage());
        }
    }
    // -- END: GOOGLE LOGIN METHODS


    @Override
    public boolean isActive() {
        return isActive;
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
}
