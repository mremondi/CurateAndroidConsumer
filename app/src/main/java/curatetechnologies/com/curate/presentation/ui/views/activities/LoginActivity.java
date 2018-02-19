package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.converters.oauth.FacebookUserConverter;
import curatetechnologies.com.curate.network.converters.oauth.GoogleUserConverter;
import curatetechnologies.com.curate.presentation.presenters.LoginContract;
import curatetechnologies.com.curate.presentation.presenters.LoginPresenter;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final Integer RC_SIGN_IN = 0;

    private LoginContract mLoginPresenter;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.btn_login_facebook)
    LoginButton btnFacebookLogin;

    @OnClick(R.id.btn_login_facebook_custom) void btnFacebookCustomClick() {
        btnFacebookLogin.performClick();
    }

    @OnClick(R.id.btn_login_google_custom) void btnGoogleLoginCustom() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @OnClick(R.id.btn_login_email) void btnLoginEmailClick(){
        // go to Register with email password screen
        Intent i = new Intent(this, LoginWithEmailActivity.class);
        startActivity(i);
        finish();
    }
    @OnClick(R.id.btn_login_skip) void btnLoginSkipClick(){
        Intent i = new Intent(this, OnBoardingWorkflowActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );

        SharedPreferences pref = getSharedPreferences("CURATE", MODE_PRIVATE);
        pref.edit().clear().apply();

        ButterKnife.bind(this);
        setUpFacebookLoginCallback();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: I don't think we need this. We can check our locally save db. Keeping for now
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(GoogleUserConverter.apply(account));
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
    public void updateUI(){
        Intent i = new Intent(this, OnBoardingWorkflowActivity.class);
        startActivity(i);
        finish();
    }

    // -- BEGIN: FACEBOOK LOGIN METHODS
    private void setUpFacebookLoginCallback(){
        btnFacebookLogin.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                handleFacebookLoginSuccess(loginResult);
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "An Error occurred " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookLoginSuccess(final LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", object.toString());
                        UserModel user = FacebookUserConverter.apply(object, loginResult.getAccessToken().getToken());
                        mLoginPresenter.saveUser(user);
                        Log.d("USER", user.getEmail());
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
            mLoginPresenter.saveUser(GoogleUserConverter.apply(account));
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
