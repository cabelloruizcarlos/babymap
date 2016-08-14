package com.crrc.babymap.app.presenters;

import android.widget.Toast;

import com.crrc.babymap.app.interfaces.LoginView;
import com.crrc.babymap.app.model.UserProfile;
import com.crrc.babymap.app.mvp.BasePresenter;
import com.crrc.babymap.app.retrofit.ILogin;
import com.crrc.babymap.app.retrofit.Validation;

import retrofit.RetrofitError;

/**
 * Created by Carlos2 on 26/03/2016.
 */
public class LoginPresenter extends BasePresenter<LoginView>{

    LoginView mView;

    public LoginPresenter(LoginView view){
        super(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mView.loadUser();
    }

    public void logUser(String user, String pass) {
        // Steps to do the login. From Yubl
        // 1. validate the login credentials locally
        // 2. ask the service if the login is valid
        // 3. if login is valid, persist the login
        // 4. if login is not valid, inform the user

        // Step 1
        UserProfile.getMainUserProfile().setUser(user);
        String username = UserProfile.getMainUserProfile().getUser();
        String password = pass;

        boolean valid = false;
        Validation validation = null;

        try {
            validation = validateCredentials(username, password);
            valid = validation.isValid;
        } catch (RetrofitError ro) {
            valid = false;
        }

        if (valid) {
            // Move on to the next activity.
            // Before move forward the phone number is cached and the user finished the log in process

            UserProfile.getMainUserProfile().setLogged(true);
            mView.navigateToMapsActivity();
        } else {
            String message = "Login failed";
            if (validation != null) {
                message += ": " + validation.errorMessage;
            }

            Toast toast = Toast.makeText(mView.getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
        }
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
