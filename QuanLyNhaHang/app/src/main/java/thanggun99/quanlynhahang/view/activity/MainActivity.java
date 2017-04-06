package thanggun99.quanlynhahang.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.CommondActionForView;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.service.ConnectChangeBroadcastReceiver;
import thanggun99.quanlynhahang.service.MyBroadcastReceiver;
import thanggun99.quanlynhahang.service.MyFirebaseMessagingService;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ChangePasswordDialog;
import thanggun99.quanlynhahang.view.dialog.ErrorDialog;
import thanggun99.quanlynhahang.view.dialog.KhachHangYeuCauDialog;
import thanggun99.quanlynhahang.view.dialog.NotifiDialog;
import thanggun99.quanlynhahang.view.fragment.DatBanFragment;
import thanggun99.quanlynhahang.view.fragment.ManagerFragment;
import thanggun99.quanlynhahang.view.fragment.PhucVuFragment;
import thanggun99.quanlynhahang.view.fragment.SettingFragment;
import thanggun99.quanlynhahang.view.fragment.ThongKeFragment;

public class MainActivity extends AppCompatActivity implements CommondActionForView, View.OnClickListener, MainPresenter.MainView {
    private static boolean isShowFloatButton = false;
    private WindowManager wm;
    private WindowManager.LayoutParams params;
    private LinearLayout layoutFloat;
    private TextView tvNumber;

    private Database database;

    private Admin admin;
    private TextView tvAdmin, tvHoTen;
    private ImageButton btnDropDown;
    private PopupMenu popupMenu;

    private MainPresenter mainPresenter;
    private PhucVuPresenter phucVuPresenter;

    private Button btnPhucVu, btnManage, btnThongKe, btnDatBan, btnSetting, btnSelected;

    private PhucVuFragment phucVuFragment;
    private SettingFragment settingFragment;
    private DatBanFragment datBanFragment;
    private ManagerFragment managerFragment;
    private ThongKeFragment thongKeFragment;
    private Fragment fragmentIsShow;

    private ProgressDialog progressDialog;
    private ErrorDialog errorDialog;
    private NotifiDialog notifiDialog;
    private KhachHangYeuCauDialog khachHangYeuCauDialog;

    private MyBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = (Admin) getIntent().getSerializableExtra(Admin.ADMIN);

        findViews(null);
        initComponents();
        setEvents();
        mainPresenter.getDatas();

    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
        cancelDialog();
        super.onDestroy();
    }

    private void cancelDialog() {

        if (notifiDialog != null) {
            notifiDialog.cancel();
        }
        if (errorDialog != null) {
            errorDialog.cancel();
        }
        if (khachHangYeuCauDialog != null) {
            khachHangYeuCauDialog.cancel();
        }
    }

    @Override
    public void findViews(View view) {
        btnDropDown = (ImageButton) findViewById(R.id.btn_drop_down);
        tvAdmin = (TextView) findViewById(R.id.tv_ten_admin);
        tvHoTen = (TextView) findViewById(R.id.tv_ho_ten);
        btnPhucVu = (Button) findViewById(R.id.btn_sell);
        btnManage = (Button) findViewById(R.id.btn_manager);
        btnThongKe = (Button) findViewById(R.id.btn_thong_ke);
        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
        btnSetting = (Button) findViewById(R.id.btn_setting);

        btnSelected = btnPhucVu;
    }

    @Override
    public void initComponents() {

        wm = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        layoutFloat = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.btn_float, null);
        tvNumber = (TextView) layoutFloat.findViewById(R.id.tv_num);

        database = new Database();
        database.setAdmin(admin);
        mainPresenter = new MainPresenter(this, database);
        phucVuPresenter = new PhucVuPresenter(this, database);

        myBroadcastReceiver = new MyBroadcastReceiver(phucVuPresenter, mainPresenter);

        notifiDialog = new NotifiDialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        popupMenu = new PopupMenu(this, btnDropDown);
        popupMenu.getMenuInflater().inflate(R.menu.account_menu, popupMenu.getMenu());

        errorDialog = new ErrorDialog(this, mainPresenter);
        khachHangYeuCauDialog = new KhachHangYeuCauDialog(this, phucVuPresenter);

        phucVuFragment = new PhucVuFragment(phucVuPresenter);
        intentFilter = new IntentFilter();

    }

    @Override
    public void setEvents() {
        setAdmin();

        btnDropDown.setOnClickListener(this);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_change_pass:
                        mainPresenter.onClickChangePassword();
                        return true;
                    case R.id.btn_logout:
                        mainPresenter.logout();
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnPhucVu.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnThongKe.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        intentFilter.addAction(ConnectChangeBroadcastReceiver.CONNECT_AVAILABLE);
        intentFilter.addAction(ConnectChangeBroadcastReceiver.CONNECT_FAIL);
        intentFilter.addAction(MyFirebaseMessagingService.DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.HUY_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.UPDATE_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.LOGOUT_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_VAO_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_HANG_REGISTER_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_HANG_YEU_CAU_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, intentFilter);

    }

    private void setAdmin() {
        tvAdmin.setText(admin.getTenDangNhap());
        tvHoTen.setText("(" + admin.getHoTen() + ")");
        if (admin.getType() == 2) {
            btnManage.setEnabled(false);
            btnThongKe.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sell:
                if (phucVuFragment == null) phucVuFragment = new PhucVuFragment(phucVuPresenter);
                fillFrame(phucVuFragment, btnPhucVu);
                break;
            case R.id.btn_manager:
                if (managerFragment == null) managerFragment = new ManagerFragment(mainPresenter);
                fillFrame(managerFragment, btnManage);
                break;
            case R.id.btn_thong_ke:
                if (thongKeFragment == null) {

                    thongKeFragment = new ThongKeFragment(database);
                } else {
                    thongKeFragment.showInfo();
                }
                fillFrame(thongKeFragment, btnThongKe);
                break;
            case R.id.btn_dat_ban:
                if (datBanFragment == null) datBanFragment = new DatBanFragment(phucVuPresenter);
                fillFrame(datBanFragment, btnDatBan);
                break;
            case R.id.btn_setting:
                if (settingFragment == null) settingFragment = new SettingFragment();
                fillFrame(settingFragment, btnSetting);
                break;
            case R.id.btn_drop_down:
                popupMenu.show();
                break;
            default:
                break;
        }
    }


    private void createFloatView() {

        ImageButton btnFloat = (ImageButton) layoutFloat.findViewById(R.id.btn_float);

        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        btnFloat.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;

                        wm.updateViewLayout(layoutFloat, params);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                khachHangYeuCauDialog.show();
            }
        });

        wm.addView(layoutFloat, params);
    }

    @Override
    public void showFloatButton() {
        if (!isShowFloatButton) {
            isShowFloatButton = true;
            createFloatView();
        }
    }

    @Override
    public void updateFloatButton(int size) {
        if (size == 0) {
            removeFloatButton();
        } else {
            showFloatButton();

            tvNumber.setText(size + "");
            wm.updateViewLayout(layoutFloat, params);
        }
    }

    @Override
    public void removeFloatButton() {
        if (isShowFloatButton) {
            isShowFloatButton = false;
            wm.removeView(layoutFloat);
        }
    }

    @Override
    public void clearFrame() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments.size() > 0) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (Fragment fragment : fragments) {
                Utils.showLog("xoa " + fragment.getClass().getName());
                transaction.remove(fragment);
            }
            transaction.commit();
        }
        cancelDialog();
        btnSelected.setSelected(false);
        fragmentIsShow = null;
        phucVuFragment = null;
        datBanFragment = null;
        managerFragment = null;
        thongKeFragment = null;
    }

    @Override
    public void setYeuCauList() {
        khachHangYeuCauDialog = new KhachHangYeuCauDialog(this, phucVuPresenter);
    }

    @Override
    public void showChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(this, mainPresenter);
        changePasswordDialog.show();
    }

    @Override
    public void hideKhachHangYeuCauDialog() {
        khachHangYeuCauDialog.dismiss();
    }

    @Override
    public void showLogin() {
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void showPhucVu() {
        if (phucVuFragment == null) phucVuFragment = new PhucVuFragment(phucVuPresenter);
        fillFrame(phucVuFragment, btnPhucVu);
    }

    private void fillFrame(final Fragment fragment, Button button) {
        if (button.isSelected()) return;

        btnSelected.setSelected(false);
        button.setSelected(true);
        btnSelected = button;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow != null && fragmentIsShow.isVisible()) {
            transaction.hide(fragmentIsShow);
            fragmentIsShow.onPause();
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.frame, fragment);
        }
        transaction.commitAllowingStateLoss();
        fragmentIsShow = fragment;
        fragmentIsShow.onResume();

    }

    @Override
    public void showGetDatasFailDialog() {
        errorDialog.cancel();
        errorDialog.show();
    }

    @Override
    public void showContent() {
        setAdmin();
        if (phucVuFragment == null) phucVuFragment = new PhucVuFragment(phucVuPresenter);
        fillFrame(phucVuFragment, btnPhucVu);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {

        progressDialog.hide();
    }

    @Override
    public void showNotifyDialog(String message) {
        notifiDialog.notifi(message);
    }

    @Override
    public void showOtherLoginDialog() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.other_people_login));
    }
}
