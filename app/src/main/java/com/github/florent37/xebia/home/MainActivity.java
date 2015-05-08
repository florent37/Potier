package com.github.florent37.xebia.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.florent37.xebia.pay.PayActivity;
import com.github.florent37.xebia.R;
import com.github.florent37.xebia.home.parallax.ParallaxHeaderActivity;
import com.github.florent37.xebia.home.parallax.ParallaxHelper;


public class MainActivity extends ActionBarActivity implements ParallaxHeaderActivity, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private ParallaxHelper parallaxHelper;

    private View fabPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, 0, 0);
        drawer.setDrawerListener(drawerToggle);

        drawer.findViewById(R.id.left_drawer).setOnClickListener(this); //avoid scroll behind drawer
        drawer.findViewById(R.id.drawer_books).setOnClickListener(this);
        drawer.findViewById(R.id.drawer_pay).setOnClickListener(this);
        drawer.findViewById(R.id.drawer_settings).setOnClickListener(this);

        fabPay = findViewById(R.id.fab_pay);
        fabPay.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, BookListFragment.newInstance())
                .commit();

        prepareParallaxScroll();
    }

    private void prepareParallaxScroll() {
        final View logo = findViewById(R.id.logo);
        logo.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                logo.getViewTreeObserver().removeOnPreDrawListener(this);
                parallaxHelper = new ParallaxHelper(toolbar,
                        findViewById(R.id.headerBackground),
                        logo);
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //dispatch the scroll to the parallaxHelper
    @Override
    public void onParallaxScroll(int yOffset) {
        if (parallaxHelper != null) {
            parallaxHelper.onScroll(yOffset);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_pay:
            case R.id.drawer_pay:
                drawer.closeDrawers();
                startPayActivity();
                break;
            case R.id.drawer_books:
                drawer.closeDrawers();
                break;
            case R.id.drawer_settings:
                break;
        }
    }

    private void startPayActivity() {
        //start activity with shared transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setExitTransition(null);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, null
        );

        Intent intent = new Intent(this, PayActivity.class);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
