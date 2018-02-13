package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
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
import curatetechnologies.com.curate.presentation.presenters.LoginWithEmailContract;
import curatetechnologies.com.curate.presentation.presenters.LoginWithEmailPresenter;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class LoginWithEmailActivity extends AppCompatActivity implements LoginWithEmailPresenter.View{

    private LoginWithEmailContract mConnectWithEmailPresenter;

    @BindView(R.id.connect_with_email_et_email)
    EditText etEmail;
    @BindView(R.id.connect_with_email_et_password)
    EditText etPassword;
    @BindView(R.id.connect_with_email_btn_login)
    Button btnLogin;
    @OnTextChanged(R.id.connect_with_email_et_password) void onPasswordChanged(){
        if(etPassword.getText().length() > 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.buttonActiveBlue));
            btnLogin.setClickable(true);
        } else if (etPassword.getText().length() < 6){
            btnLogin.setBackgroundColor(getResources().getColor(R.color.buttonInactiveBlue));
            btnLogin.setClickable(false);
        }
    }
    @OnClick(R.id.connect_with_email_btn_login) void loginClick(){
        mConnectWithEmailPresenter.loginUserEmailPassword("", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_email);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.login_action_bar);

        mConnectWithEmailPresenter = new LoginWithEmailPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new UserRepository()
        );
    }

    public void updateUI(UserModel user){

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
