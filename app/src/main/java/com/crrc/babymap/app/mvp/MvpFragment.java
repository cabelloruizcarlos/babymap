package com.crrc.babymap.app.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.crrc.babymap.app.base.BaseFragment;

/**
 * Created by carlos on 20/07/2016.
 */
public abstract class MvpFragment<Presenter extends BasePresenter> extends BaseFragment implements BaseView {
  private Presenter presenter;

  protected abstract Presenter createPresenter();

  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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
  public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
//        Util.freeMemory();
    Log.d("GC call", "MvpFragment");
  }

}
