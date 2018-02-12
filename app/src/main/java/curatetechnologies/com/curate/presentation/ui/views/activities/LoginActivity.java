package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.network.converters.oauth.FacebookUserConverter;
import curatetechnologies.com.curate.network.converters.oauth.GoogleUserConverter;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final Integer RC_SIGN_IN = 0;

    CallbackManager callbackManager = CallbackManager.Factory.create();
    GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.btn_login_facebook)
    LoginButton btnFacebookLogin;
    @BindView(R.id.btn_login_google)
    SignInButton btnGoogleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        setUpFacebookLoginCallback();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: I don't think we need this. We can check our locally save db. Keeping for now
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(GoogleUserConverter.apply(account));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        // FACEBOOK
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // GOOGLE
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    }

    private void updateUI(UserModel user){

    }

    // -- BEGIN: FACEBOOK LOGIN METHODS
    private void setUpFacebookLoginCallback(){
        btnFacebookLogin.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                updateUI(FacebookUserConverter.apply(object));
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "An Error occurred " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // -- END: FACEBOOK LOGIN METHODS

    // -- BEGIN: GOOGLE LOGIN METHODS
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(GoogleUserConverter.apply(account));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            updateUI(null);
        }
    }
    // -- END: GOOGLE LOGIN METHODS
}
