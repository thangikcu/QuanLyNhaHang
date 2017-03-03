package com.thanggun99.khachhang.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.KhachHang;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.service.MyFirebaseMessagingService;
import com.thanggun99.khachhang.util.Utils;
import com.thanggun99.khachhang.view.dialog.ChangePasswordDialog;
import com.thanggun99.khachhang.view.fragment.AboutFragment;
import com.thanggun99.khachhang.view.fragment.DatBanFragment;
import com.thanggun99.khachhang.view.fragment.FeedbackFragment;
import com.thanggun99.khachhang.view.fragment.HomeFragment;
import com.thanggun99.khachhang.view.fragment.MyProfileFragment;
import com.thanggun99.khachhang.view.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements KhachHangPresenter.MainView, PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private LinearLayout lnLogin;
    private TextView tvUsername, tvFullname;
    private IntentFilter intentFilter;
    private NavigationView navigationView;
    private PopupMenu popupMenu;
    private ImageButton btnArrowDown;
    private KhachHangPresenter khachHangPresenter;
    private boolean isLogin = false;
    private HomeFragment homeFragment;
    private FeedbackFragment feedbackFragment;
    private SettingFragment settingFragment;
    private AboutFragment aboutFragment;
    private DatBanFragment datBanFragment;
    private MyProfileFragment myProfileFragment;
    private Fragment fragmentIsShow;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == MyFirebaseMessagingService.NOTIFI_ACTION) {

                Utils.showToast(intent.getStringExtra(MyFirebaseMessagingService.NOTIFI));

            } else if (intent.getAction() == MyFirebaseMessagingService.LOGOUT_ACTION) {
                if (isLogin) {
                    khachHangPresenter.onOtherLogin();
                    khachHangPresenter.logout();
                }
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
        khachHangPresenter = new KhachHangPresenter(this);
        //initFragment
        fragmentIsShow = new Fragment();
        homeFragment = new HomeFragment(khachHangPresenter);
        feedbackFragment = new FeedbackFragment(khachHangPresenter);
        settingFragment = new SettingFragment();
        aboutFragment = new AboutFragment();
        datBanFragment = new DatBanFragment();
        myProfileFragment = new MyProfileFragment();

        popupMenu = new PopupMenu(this, btnArrowDown);
        popupMenu.getMenuInflater().inflate(R.menu.drop_menu, popupMenu.getMenu());
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        intentFilter = new IntentFilter();

    }

    private void setEvents() {
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        popupMenu.setOnMenuItemClickListener(this);
        btnArrowDown.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        showNavigationOnUnLogin();
        intentFilter.addAction(MyFirebaseMessagingService.LOGOUT_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.NOTIFI_ACTION);
        fillFrame(homeFragment, R.id.btn_home);

    }

    private void findViews() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        lnLogin = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ln_login);
        tvFullname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_full_name);
        tvUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        btnArrowDown = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.btn_arrow_down);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void fillFrame(Fragment fragment, int id) {
        if (fragment.isVisible()) return;

        MenuItem menuItem = navigationView.getMenu().findItem(id);
        menuItem.setChecked(true);
        String title = menuItem.getTitle().toString();
        toolbar.setTitle(title);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow.isVisible()) transaction.hide(fragmentIsShow);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.frame, fragment);
        }
        transaction.commit();
        fragmentIsShow = fragment;
    }

    public void showNavigationOnUnLogin() {
        lnLogin.setVisibility(View.GONE);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
    }

    public void showNavigationOnLogin() {
        lnLogin.setVisibility(View.VISIBLE);
        navigationView.getMenu().getItem(1).setVisible(true);
        navigationView.getMenu().getItem(2).setVisible(true);
    }


    @Override
    public void showDialogConnectFail() {
        Utils.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                intentFilter);
        super.onResume();
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
    protected void onDestroy() {
        khachHangPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!(fragmentIsShow instanceof HomeFragment)) {
                fillFrame(homeFragment, R.id.btn_home);
            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void showViewOnlogin(KhachHang khachHang) {
        isLogin = true;
        tvUsername.setText(khachHang.getTenDangNhap());
        tvFullname.setText("(" + khachHang.getTenKhachHang() + ")");
        showNavigationOnLogin();
    }

    @Override
    public void showViewOnUnlogin() {
        isLogin = false;
        tvUsername.setText("");
        tvFullname.setText("");
        drawerLayout.closeDrawer(GravityCompat.START);
        popupMenu.dismiss();
        fillFrame(homeFragment, R.id.btn_home);
        showNavigationOnUnLogin();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_logout:
                khachHangPresenter.logout();
                return true;
            case R.id.btn_change_password:
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(this, khachHangPresenter);
                changePasswordDialog.show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_home:
                fillFrame(homeFragment, R.id.btn_home);
                break;
            case R.id.btn_gop_y:
                fillFrame(feedbackFragment, R.id.btn_gop_y);
                break;
            case R.id.btn_settings:
                fillFrame(settingFragment, R.id.btn_settings);
                break;
            case R.id.btn_about:
                fillFrame(aboutFragment, R.id.btn_about);
                break;
            case R.id.btn_dat_ban:
                fillFrame(datBanFragment, R.id.btn_dat_ban);
                break;
            case R.id.btn_my_profile:
                fillFrame(myProfileFragment, R.id.btn_my_profile);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
