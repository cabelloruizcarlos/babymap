package com.crrc.babymap.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.crrc.babymap.R;

import com.crrc.babymap.app.interfaces.LoginView;
import com.crrc.babymap.app.mvp.MvpActivity;
import com.crrc.babymap.app.presenters.LoginPresenter;
import com.crrc.babymap.app.model.Constant;
import com.crrc.babymap.app.model.UserProfile;

public class LoginActivity extends MvpActivity<LoginPresenter> implements View.OnClickListener, LoginView {

    private Button mSubmitBtn;
    private EditText mUserView, mPasswordView;

    private LoginPresenter mPresenter;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this.mSubmitBtn = (Button) findViewById(R.id.login_button);
        this.mUserView = ((EditText) findViewById(R.id.login_username));
        this.mPasswordView = ((EditText) findViewById(R.id.login_pass));

        this.mPresenter = new LoginPresenter(this);
        this.mSubmitBtn.setOnClickListener(this);
    }

    @Override
    protected Fragment getFirstFragment() { return null; }

    @Override
    public void onClick(View v) {
        String login = LoginActivity.this.mUserView.getText().toString();
        String pass = LoginActivity.this.mPasswordView.getText().toString();
        mPresenter.logUser(login, pass);
    }

    @Override
    public void loadUser() {

        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        String cachedUser = UserProfile.getMainUserProfile().getUser();
        if (cachedUser != Constant.DEFAULT_USER) {
            this.mUserView.setText(cachedUser);
            this.mPasswordView.requestFocus();
            imm.showSoftInput(this.mPasswordView, InputMethodManager.SHOW_IMPLICIT);
        } else {
            this.mUserView.requestFocus();
            imm.showSoftInput(this.mUserView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void navigateToMapsActivity() {

        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();

        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_left);

    }

    public Context getApplicationContext() {
        return getApplicationContext();
    }
}
