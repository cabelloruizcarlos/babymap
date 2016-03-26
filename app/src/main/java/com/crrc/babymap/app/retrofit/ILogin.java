package com.crrc.babymap.app.retrofit;

import com.crrc.babymap.app.retrofit.Validation;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Carlos on 07/04/15.
 */
public interface ILogin {

  @GET("/babymap/public/api/marker")
  void markers(Callback<JSONObject[]> markersList);

  @POST("/user/validate/credentials")
  Validation validateLogin(@Body ValidateCredentials credentials);

  @POST("/user/forgot_password")
  Validation forgotPassword(@Body ForgotPassword details);

  @POST("/user/password")
  Validation setPassword(@Body SetPassword setPassword);

  public class ValidateCredentials {
    public String username;
    public String password;
  }

  public class ForgotPassword {
    public String iso31661Alpha2CountryCode;

    /***
     * The phone number that gets sent through to the server should not send the +, it should only
     * send the country code concatenated with the number. For example:
     *
     * valid: 447875224731
     * invalid: +44702340234
     * valid: 4472349234
     * invalid: 72349234
     */
    public String username;
  }

  public class SetPassword {
    public String username;
    public String newPassword;
  }
}
