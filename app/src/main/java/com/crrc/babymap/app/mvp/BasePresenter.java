package com.crrc.babymap.app.mvp;

import rx.Observable;

/**
 * Created by carlos on 20/07/2016.
 */
public abstract class BasePresenter<View extends BaseView> {
  public final View view;

  public BasePresenter(View view) {
    this.view = view;
  }

  public void onCreate() {
  }

  public void onStart() {
  }

  public void onResume() {
  }

  public void onPause() {
  }

  public void onStop() {
  }

  public void onDestroy() {
  }

  protected void showToast(String message) {
    view.showToast(message);
  }

  protected void showErrorToast(Throwable e) {
    view.showErrorToast(e);
  }

  protected void showAlert(String title, String message) {
    view.showAlert(title, message);
  }

  protected <T> Observable.Transformer<T, T> bindToLifecycle() {
    return view.bindToLifecycle();
  }
}
