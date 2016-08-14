package com.crrc.babymap.app.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.crrc.babymap.R;
import com.crrc.babymap.app.util.ToolbarUtil;

import butterknife.BindView;

/**
 * Created by carlos on 19/07/2016.
 */
public abstract class BaseToolbarActivity<FirstFragment extends Fragment>
        extends BaseFrameActivity<FirstFragment> {

    @BindView(R.id.toolbar) protected Toolbar toolbar;

    @Override
    protected int getLayout() {
//        return R.layout.activity_toolbar_frame;
    return 0;
    }

    public void setupToolbar(String headerObject, String title) {
//        ParseUi.setupToolbarStyle(this, headerObject, title);
        ToolbarUtil.setupToolbarView(this, toolbar);
    }
}
