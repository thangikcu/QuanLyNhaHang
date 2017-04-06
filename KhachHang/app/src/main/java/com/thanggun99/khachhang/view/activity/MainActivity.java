package com.thanggun99.khachhang.view.activity;

import android.app.ProgressDialog;
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
import com.thanggun99.khachhang.view.fragment.FeedbackFragment;
import com.thanggun99.khachhang.view.fragment.HomeFragment;
import com.thanggun99.khachhang.view.fragment.LoginFragment;
import com.thanggun99.khachhang.view.fragment.MyProfileFragment;
import com.thanggun99.khachhang.view.fragment.SettingFragment;
import com.thanggun99.khachhang.view.fragment.ThongTinPhucVuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements KhachHangPresenter.MainView, PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle toggle;
    private LinearLayout lnLogin;
    private TextView tvUsername, tvFullname, tvDangNhap;
    private IntentFilter intentFilter;
    private PopupMenu popupMenu;
    private ImageButton btnArrowDown;
    private KhachHangPresenter khachHangPresenter;
    private HomeFragment homeFragment;
    private FeedbackFragment feedbackFragment;
    private SettingFragment settingFragment;
    private AboutFragment aboutFragment;
    private ThongTinPhucVuFragment thongTinPhucVuFragment;
    private MyProfileFragment myProfileFragment;
    private LoginFragment loginFragment;
    private Fragment fragmentIsShow;
    private ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case MyFirebaseMessagingService.NOTIFI_ACTION:
                    break;
                case MyFirebaseMessagingService.LOGOUT_ACTION:

                    if (khachHangPresenter.checkLogin()) {

                        khachHangPresenter.onOtherLogin();
                        khachHangPresenter.logout();
                    }
                    break;
                case MyFirebaseMessagingService.UPDATE_DAT_BAN_ACTION:
                    if (khachHangPresenter != null) {
                        KhachHang khachHangUpdate = (KhachHang) intent.getSerializableExtra(MyFirebaseMessagingService.KHACH_HANG);

                        khachHangPresenter.updateThongTinKhachHangService(khachHangUpdate);

                        tvFullname.setText("(" + khachHangUpdate.getTenKhachHang() + ")");

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.thong_tin_cua_ban_da_duoc_cap_nhat));
                    }
                    break;
                case MyFirebaseMessagingService.HUY_DAT_BAN_ACTION:
                    if (khachHangPresenter != null) {
                        khachHangPresenter.deleteDatBanService();

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.da_huy_dat_ban));
                    }
                    break;
                case MyFirebaseMessagingService.TAO_HOA_DON_MOI_ACTION:
                    if (khachHangPresenter != null) {
                        khachHangPresenter.taoHoaDonMoiService();

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.da_tao_hoa_don_moi));
                    }
                    break;
                case MyFirebaseMessagingService.GIAM_GIA_HOA_DON_ACTION:
                    if (khachHangPresenter != null) {
                        int giamGia = intent.getIntExtra(MyFirebaseMessagingService.GIAM_GIA, 0);

                        khachHangPresenter.giamGiaHoaDonMoiService(giamGia);

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.ban_duoc_giam_gia) + " " + giamGia + "%");
                    }
                    break;
                case MyFirebaseMessagingService.ORDER_MON_ACTION:
                    if (khachHangPresenter != null) {

                        khachHangPresenter.orderMonService();

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.da_them_mon));
                    }
                    break;
                case MyFirebaseMessagingService.TINH_TIEN_HOA_DON_ACTION:
                    if (khachHangPresenter != null) {

                        khachHangPresenter.tinhTienHoaDonService();

                        Utils.showNotify(Utils.getStringByRes(R.string.thong_bao),
                                Utils.getStringByRes(R.string.da_tinh_tien));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViews();
        initComponets();
        setEvents();
        khachHangPresenter.loginAuto();

    }

    private void initComponets() {
        khachHangPresenter = new KhachHangPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        //initFragment
        fragmentIsShow = new Fragment();
        loginFragment = new LoginFragment(khachHangPresenter);
        homeFragment = new HomeFragment(khachHangPresenter);

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
        navView.setNavigationItemSelectedListener(this);
        tvDangNhap.setOnClickListener(this);

        showNavigationOnUnLogin();

        intentFilter.addAction(MyFirebaseMessagingService.LOGOUT_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.NOTIFI_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.UPDATE_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.HUY_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.TAO_HOA_DON_MOI_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.GIAM_GIA_HOA_DON_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.ORDER_MON_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.TINH_TIEN_HOA_DON_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                intentFilter);

    }

    private void findViews() {
        lnLogin = ButterKnife.findById(navView.getHeaderView(0), R.id.ln_login);
        tvFullname = ButterKnife.findById(navView.getHeaderView(0), R.id.tv_full_name);
        tvUsername = ButterKnife.findById(navView.getHeaderView(0), R.id.tv_username);
        tvDangNhap = ButterKnife.findById(navView.getHeaderView(0), R.id.tv_login);
        btnArrowDown = ButterKnife.findById(navView.getHeaderView(0), R.id.btn_arrow_down);

    }

    @Override
    public void showLoginFragment() {
        fillFrame(loginFragment, 0);
    }

    @Override
    public void showHomeFragment() {
        fillFrame(homeFragment, R.id.btn_home);
    }

    @Override
    public void showThongTinPhucVuFragment() {
        if (thongTinPhucVuFragment == null) {

            thongTinPhucVuFragment = new ThongTinPhucVuFragment(khachHangPresenter);
        }

        fillFrame(thongTinPhucVuFragment, R.id.btn_thong_tin_phuc_vu);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void fillFrame(final Fragment fragment, int id) {
        if (fragment.isVisible()) return;

        if (id != 0) {
            MenuItem menuItem = navView.getMenu().findItem(id);
            menuItem.setChecked(true);
            String title = menuItem.getTitle().toString();
            toolbar.setTitle(title);
        } else if (id == 0) {
            toolbar.setTitle(Utils.getStringByRes(R.string.dang_nhap));
        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow.isVisible()) transaction.hide(fragmentIsShow);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.frame, fragment);
        }
        transaction.commitAllowingStateLoss();
        fragmentIsShow = fragment;

    }

    public void showNavigationOnUnLogin() {
        lnLogin.setVisibility(View.GONE);
        tvDangNhap.setVisibility(View.VISIBLE);
        navView.getMenu().getItem(1).setVisible(false);
        navView.getMenu().getItem(2).setVisible(false);
    }

    public void showNavigationOnLogin() {
        lnLogin.setVisibility(View.VISIBLE);
        tvDangNhap.setVisibility(View.GONE);
        navView.getMenu().getItem(1).setVisible(true);
        navView.getMenu().getItem(2).setVisible(true);
    }

    @Override
    public void showNotify(String message) {
        Utils.notifi(message);
    }

    @Override
    public void showConnectFailDialog() {
        Utils.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        if (progressDialog != null) {
            progressDialog.cancel();
        }
        khachHangPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_arrow_down:
                popupMenu.show();
                break;
            case R.id.tv_login:
                drawerLayout.closeDrawer(GravityCompat.START);
                fillFrame(loginFragment, 0);
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
            if (!(fragmentIsShow instanceof HomeFragment)) {
                fillFrame(homeFragment, R.id.btn_home);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void showViewOnlogin(KhachHang khachHang) {

        if (fragmentIsShow instanceof LoginFragment) {
            fillFrame(homeFragment, R.id.btn_home);
        }

        tvUsername.setText(khachHang.getTenDangNhap());
        tvFullname.setText("(" + khachHang.getTenKhachHang() + ")");
        showNavigationOnLogin();
    }

    @Override
    public void showViewOnUnlogin() {
        tvUsername.setText("");
        tvFullname.setText("");
        drawerLayout.closeDrawer(GravityCompat.START);
        popupMenu.dismiss();
        fillFrame(loginFragment, 0);
        showNavigationOnUnLogin();
    }

    @Override
    public void setNullFragments() {
        thongTinPhucVuFragment = null;
        myProfileFragment = null;
    }

    @Override
    public void showOtherLogin() {
        Utils.notifi(Utils.getStringByRes(R.string.other_people_login));
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
                if (feedbackFragment == null) {
                    feedbackFragment = new FeedbackFragment(khachHangPresenter);
                }
                fillFrame(feedbackFragment, R.id.btn_gop_y);
                break;
            case R.id.btn_settings:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
                fillFrame(settingFragment, R.id.btn_settings);
                break;
            case R.id.btn_about:
                if (aboutFragment == null) {
                    aboutFragment = new AboutFragment();
                }
                fillFrame(aboutFragment, R.id.btn_about);
                break;
            case R.id.btn_thong_tin_phuc_vu:
                if (thongTinPhucVuFragment == null) {
                    thongTinPhucVuFragment = new ThongTinPhucVuFragment(khachHangPresenter);
                }
                fillFrame(thongTinPhucVuFragment, R.id.btn_thong_tin_phuc_vu);
                break;
            case R.id.btn_my_profile:
                if (myProfileFragment == null) {
                    myProfileFragment = new MyProfileFragment(khachHangPresenter);
                }
                fillFrame(myProfileFragment, R.id.btn_my_profile);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
