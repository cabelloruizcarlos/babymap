package com.crrc.babymap.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crrc.babymap.R;

import com.crrc.babymap.interfaces.ILogin;
import com.crrc.babymap.retrofit.Validation;
import com.crrc.babymap.model.Constant;
import com.crrc.babymap.model.UserProfile;

import retrofit.RetrofitError;

/**
 * Created by Carlos on 03/04/2015.
 */
public class LoginActivity extends Activity {

    private Button mSubmitBtn;
    private EditText mUserView, mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this.mSubmitBtn = (Button) findViewById(R.id.login_button);
        this.mUserView = ((EditText) findViewById(R.id.login_username));
        this.mPasswordView = ((EditText) findViewById(R.id.login_pass));

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

        this.mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Steps to do the login. From Yubl
                // 1. validate the login credentials locally
                // 2. ask the service if the login is valid
                // 3. if login is valid, persist the login
                // 4. if login is not valid, inform the user

                // Step 1
                UserProfile.getMainUserProfile().setUser(LoginActivity.this.mUserView.getText().toString());
                String username = UserProfile.getMainUserProfile().getUser();
                String password = LoginActivity.this.mPasswordView.getText().toString();

                boolean valid = false;
                Validation validation = null;

                try {
                    validation = validateCredentials(username, password);
                    valid = validation.isValid;
                } catch (RetrofitError ro) {
                    // TODO(from Yubl): feed back to user
                    valid = false;
                }

                if (valid) {
                    // Move on to the next activity.
                    // Before move forward the phone number is cached and the user finished the log in process

                    UserProfile.getMainUserProfile().setLogged(true);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();

                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_from_left);

                } else {
                    // TODO(benp) review how to display the fail message
                    String message = "Login failed";
                    if (validation != null) {
                        message += ": " + validation.errorMessage;
                    }

                    Context c = LoginActivity.this;
                    Toast toast = Toast.makeText(c, message, Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });
    }

    private Validation validateCredentials(String username, String password) throws RetrofitError {
        ILogin.ValidateCredentials credentials = new ILogin.ValidateCredentials();
        credentials.username = username;
        credentials.password = password;

        // loginService.getJsonService() thats returns the RestAdapter...
        /*return loginService.getJsonService().validateLogin(credentials);*/
        return new Validation(true,"");
    }
}
