package com.thanggun99.khachhang.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.thanggun99.khachhang.LoginTask;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.adapter.TabsAdapter;
import com.thanggun99.khachhang.fragment.ThucDonFragment;
import com.thanggun99.khachhang.fragment.TinTucFragment;
import com.thanggun99.khachhang.service.MyFirebaseMessagingService;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginTask.OnLoginLogoutListener, PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private IntentFilter intentFilter;
    private ThucDonFragment thucDonFragment;
    private TinTucFragment tinTucFragment;
    private NavigationView navigationView;
    private PopupMenu popupMenu;
    private ImageButton btnArrowDown;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == MyFirebaseMessagingService.NOTIFI_ACTION) {
                Utils.showToast(intent.getStringExtra(MyFirebaseMessagingService.NOTIFI));
            } else if (intent.getAction() == MyFirebaseMessagingService.LOGOUT_ACTION) {
                thucDonFragment.otherPeopleLogin();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initComponets();
        setEvents();
    }

    private void initComponets() {
        popupMenu = new PopupMenu(this, btnArrowDown);
        popupMenu.getMenuInflater().inflate(R.menu.drop_menu, popupMenu.getMenu());
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(thucDonFragment = new ThucDonFragment());
        fragments.add(tinTucFragment = new TinTucFragment());
        thucDonFragment.setOnLoginLogoutListener(this);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragments);
        intentFilter = new IntentFilter();

    }

    private void setEvents() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawerLayout.setDrawerLockMode(View.FOCUS_LEFT);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
        popupMenu.setOnMenuItemClickListener(this);
        btnArrowDown.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        intentFilter.addAction(MyFirebaseMessagingService.LOGOUT_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.NOTIFI_ACTION);
    }

    private void findViews() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        btnArrowDown = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.btn_arrow_down);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pg);
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_arrow_down:
                popupMenu.show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoginSuccess() {
    }

    @Override
    public void onLogout() {
        Utils.showLog("logout");
    }

    @Override
    public void onLoginError() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_logout:
                thucDonFragment.logout();
                viewPager.setCurrentItem(0);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.btn_change_password:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_gop_y:
                Utils.showLog("gop y");
                return true;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }
}
