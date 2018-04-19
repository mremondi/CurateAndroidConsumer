package curatetechnologies.com.curate_consumer.modules.login_with_email;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.modules.main.MainActivity;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

public class LoginWithEmailActivity extends AppCompatActivity implements LoginWithEmailContract.View{

    private LoginWithEmailContract mLoginWithEmailPresenter;

    @BindView(R.id.login_with_email_et_email)
    EditText etEmail;
    @BindView(R.id.login_with_email_et_password)
    EditText etPassword;
    @BindView(R.id.login_with_email_btn_login)
    Button btnLogin;
    @OnTextChanged(R.id.login_with_email_et_password) void onPasswordChanged(){
        if(etPassword.getText().length() > 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.activeBlue));
            btnLogin.setClickable(true);
        } else if (etPassword.getText().length() < 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.inactiveBlue));
            btnLogin.setClickable(false);
        }
    }
    @OnClick(R.id.login_with_email_btn_login) void loginClick(){
        mLoginWithEmailPresenter.loginUserWithEmail(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.login_action_bar);

        mLoginWithEmailPresenter = new LoginWithEmailPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );
    }

    // -- BEGIN LOGIN CONTRACT METHODS
    @Override
    public void updateUI() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void saveUser(UserModel user) {
        Log.d("SAVE USER LOGIN", user.getCurateToken());
        mLoginWithEmailPresenter.saveUser(user);
    }
    // -- END LOGIN CONTRACT METHODS

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_LONG).show();
    }
}
