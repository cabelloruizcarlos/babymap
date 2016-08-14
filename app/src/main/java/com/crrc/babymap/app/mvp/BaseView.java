package com.crrc.babymap.app.mvp;

import rx.Observable;

/**
 * Created by carlos on 20/07/2016.
 */
public interface BaseView  {
    void showToast(String message);

    void showAlert(String title, String message);

    void showErrorToast(Throwable e);

    <T> Observable.Transformer<T, T> bindToLifecycle();

    void launchBrowser(String url);
}
