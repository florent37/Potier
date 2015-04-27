package com.github.florent37.xebia;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.florent37.xebia.fragment.BookListFragment;
import com.github.florent37.xebia.parallax.ParallaxHeaderActivity;
import com.github.florent37.xebia.parallax.ParallaxHelper;


public class MainActivity extends ActionBarActivity implements ParallaxHeaderActivity{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private ParallaxHelper parallaxHelper;

    private View parallaxView;
    private View logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        parallaxView = findViewById(R.id.headerBackground);
        logo = findViewById(R.id.logo);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, 0, 0);
        drawer.setDrawerListener(drawerToggle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, BookListFragment.newInstance())
                .commit();

        prepareParallaxScroll();
    }

    private void prepareParallaxScroll() {
        logo.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                logo.getViewTreeObserver().removeOnPreDrawListener(this);
                parallaxHelper = new ParallaxHelper(toolbar,parallaxView,logo);
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onParallaxScroll(int yOffset) {
        if(parallaxView != null && parallaxHelper != null){
            parallaxHelper.onScroll(yOffset);
        }
    }
}
