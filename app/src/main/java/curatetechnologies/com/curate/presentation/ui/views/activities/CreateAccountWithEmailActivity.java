package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.presentation.presenters.CreateAccountWithEmailContract;
import curatetechnologies.com.curate.presentation.presenters.CreateAccountWithEmailPresenter;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class CreateAccountWithEmailActivity extends AppCompatActivity implements CreateAccountWithEmailPresenter.View{

    private CreateAccountWithEmailContract mConnectWithEmailPresenter;

    @BindView(R.id.create_account_with_email_et_email)
    EditText etEmail;
    @BindView(R.id.create_account_with_email_et_password)
    EditText etPassword;
    @BindView(R.id.create_account_with_email_btn_login)
    Button btnLogin;
    @OnTextChanged(R.id.create_account_with_email_et_password) void onPasswordChanged(){
        if(etPassword.getText().length() > 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.activeBlue));
            btnLogin.setClickable(true);
        } else if (etPassword.getText().length() < 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.inactiveBlue));
            btnLogin.setClickable(false);
        }
    }
    @OnClick(R.id.create_account_with_email_btn_login) void loginClick(){
        mConnectWithEmailPresenter.createAccountEmailPassword(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_with_email);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.login_action_bar);

        mConnectWithEmailPresenter = new CreateAccountWithEmailPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                UserRepository.getInstance(getApplicationContext())
        );
    }

    // -- BEGIN LOGIN CONTRACT METHODS
    @Override
    public void updateUI() {
        Intent i = new Intent(this, OnBoardingWorkflowActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void saveUser(UserModel user) {
        Log.d("SAVE USER LOGIN", user.getCurateToken());
        mConnectWithEmailPresenter.saveUser(user);
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

    }
}
