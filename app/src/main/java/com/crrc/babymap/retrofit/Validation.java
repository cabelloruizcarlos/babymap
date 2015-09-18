package com.crrc.babymap.retrofit;

/**
 * Created by Carlos on 07/04/15.
 */
public class Validation {

  public boolean isValid;
  public String errorMessage;

  public Validation(boolean isValid, String errorMessage) {
    this.isValid = isValid;
    this.errorMessage = errorMessage;
  }
}
