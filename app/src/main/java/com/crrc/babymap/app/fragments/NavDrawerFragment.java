package com.crrc.babymap.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crrc.babymap.R;

/**
 * Created by Carlos on 12/04/2015.
 */
public class NavDrawerFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    private View mContainerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        return layout;
    }

    public void setUp(DrawerLayout pDrawerLayout, Toolbar pToolbar, int pFragmentResId) {

        this.mContainerView = getActivity().findViewById(pFragmentResId);
        this.mDrawerLayout = pDrawerLayout;
        this.mToolbar = pToolbar;
        this.mDrawerToggle = new ActionBarDrawerToggle(getActivity(), this.mDrawerLayout, pToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (slideOffset < 0.6)
                    mToolbar.setAlpha(1 - slideOffset);
            }
        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);

/*        mDrawerLayout.openDrawer(mContainerView);*/
        /*In order to show the hamburger icon*/
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

}
