package com.crrc.babymap.app.mvp;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import com.crrc.babymap.app.base.BaseFrameActivity;


/**
 * Created by carlos on 20/07/2016.
 */
public abstract class MvpActivity<Presenter extends BasePresenter> extends BaseFrameActivity<Fragment> implements BaseView {
  private Presenter presenter;

  protected abstract Presenter createPresenter();

  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    this.presenter = createPresenter();
    presenter.onCreate();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.onStart();
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

}
