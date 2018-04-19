package curatetechnologies.com.curate_consumer.presentation.ui.views.activities;

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
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.network.converters.oauth.FacebookUserConverter;
import curatetechnologies.com.curate_consumer.network.converters.oauth.GoogleUserConverter;
import curatetechnologies.com.curate_consumer.presentation.presenters.CreateAccountContract;
import curatetechnologies.com.curate_consumer.presentation.presenters.CreateAccountPresenter;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountContract.View{
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final Integer RC_SIGN_IN = 0;

    private CreateAccountContract mCreateAccountPresenter;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.activity_create_account_bg)
    ImageView backgroundImage;
    @BindView(R.id.btn_create_account_facebook)
    LoginButton btnFacebookCreateAccount;

    @OnClick(R.id.btn_create_account_facebook_custom) void btnFacebookCustomClick() {
        btnFacebookCreateAccount.performClick();
    }

    @OnClick(R.id.btn_create_account_google_custom) void btnGoogleLoginCustom() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @OnClick(R.id.btn_create_account_email) void btnLoginEmailClick(){
        // go to Register with email password screen
        Intent i = new Intent(this, CreateAccountWithEmailActivity.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mCreateAccountPresenter = new CreateAccountPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );

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

        setUpFacebookLoginCallback();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("855249522296-uaffr57rie99f5q7esqq5cdkfp83r1mm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: I don't think we need this. We can check our locally save db. Keeping for now
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        segueToOnboarding(GoogleUserConverter.apply(account));
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
    public void segueToOnboarding(){
        Intent i = new Intent(this, OnBoardingWorkflowActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void segueToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // -- BEGIN: FACEBOOK LOGIN METHODS
    private void setUpFacebookLoginCallback(){
        btnFacebookCreateAccount.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        btnFacebookCreateAccount.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                handleFacebookLoginSuccess(loginResult);
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(CreateAccountActivity.this, "An Error occurred " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookLoginSuccess(final LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        UserModel user = FacebookUserConverter.apply(object, loginResult.getAccessToken().getToken());


                        mCreateAccountPresenter.signUpWithFacebook(user);
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

            mCreateAccountPresenter.signUpWithGoogle(userModel);
        } catch (ApiException e) {
            Log.d("Error Google Account", e.getLocalizedMessage());
        }
    }
    // -- END: GOOGLE LOGIN METHODS


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
