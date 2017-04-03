package thanggun99.quanlynhahang.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.CommondActionForView;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.entity.Admin;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.KhachHang;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
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


    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case MyFirebaseMessagingService.DAT_BAN_ACTION:
                    if (phucVuPresenter != null) {
                        DatBan datBan = (DatBan) intent.getSerializableExtra(MyFirebaseMessagingService.DAT_BAN);

                        phucVuPresenter.datBanService(datBan);

                        Utils.showNotify(Utils.getStringByRes(R.string.khach_hang_dat_ban),
                                intent.getStringExtra(MyFirebaseMessagingService.TEN_KHACH_HANG));
                    }
                    break;
                case MyFirebaseMessagingService.HUY_DAT_BAN_ACTION:
                    if (phucVuPresenter != null) {
                        phucVuPresenter.huyDatBanService(intent.getIntExtra(MyFirebaseMessagingService.MA_DAT_BAN, 0));

                        Utils.showNotify(Utils.getStringByRes(R.string.khach_hang_huy_dat_ban),
                                intent.getStringExtra(MyFirebaseMessagingService.TEN_KHACH_HANG));
                    }
                    break;
                case MyFirebaseMessagingService.UPDATE_DAT_BAN_ACTION:
                    if (phucVuPresenter != null) {
                        DatBan datBanUpdate = (DatBan) intent.getSerializableExtra(MyFirebaseMessagingService.DAT_BAN);

                        phucVuPresenter.updateDatBanService(datBanUpdate);

                        Utils.showNotify(Utils.getStringByRes(R.string.update_dat_ban),
                                intent.getStringExtra(MyFirebaseMessagingService.TEN_KHACH_HANG));
                    }
                    break;
                case MyFirebaseMessagingService.KHACH_VAO_BAN_ACTION:
                    if (phucVuPresenter != null) {
                        DatBan datBanVaoBan = (DatBan) intent.getSerializableExtra(MyFirebaseMessagingService.DAT_BAN);

                        phucVuPresenter.khachVaoBanService(datBanVaoBan);

                        Utils.showNotify(datBanVaoBan.getBan().getTenBan(), Utils.getStringByRes(R.string.khach_vao_ban));
                    }
                    break;
                case MyFirebaseMessagingService.KHACH_HANG_REGISTER_ACTION:
                    if (database != null) {
                        KhachHang khachHang = (KhachHang) intent.getSerializableExtra(MyFirebaseMessagingService.KHACH_HANG);
                        Utils.showLog(khachHang.getMaKhachHang() + khachHang.getHoTen() + khachHang.getDiaChi()
                                + khachHang.getSoDienThoai() + khachHang.getTenDangNhap()
                                + khachHang.getMatKhau() + khachHang.getMaToken());
                        database.addKhachHang(khachHang);

                    }
                    break;
                case MyFirebaseMessagingService.KHACH_HANG_YEU_CAU_ACTION:
                    if (phucVuPresenter != null) {
                        YeuCau yeuCau = (YeuCau) intent.getSerializableExtra(MyFirebaseMessagingService.YEU_CAU);

                        phucVuPresenter.khachHangYeuCauService(yeuCau);

                        Utils.showNotify(Utils.getStringByRes(R.string.khach_hang_yeu_cau),
                                intent.getStringExtra(MyFirebaseMessagingService.TEN_KHACH_HANG));
                    }

                    break;
                case MyFirebaseMessagingService.LOGOUT_ACTION:
                    Utils.notifiOnDialog(Utils.getStringByRes(R.string.other_people_login));
                    mainPresenter.logout();

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

        admin = (Admin) getIntent().getSerializableExtra(Admin.ADMIN);

        findViews(null);
        initComponents();
        setEvents();
        mainPresenter.getDatas();

    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        if (notifiDialog != null) {
            notifiDialog.cancel();
        }
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if (errorDialog != null) {
            errorDialog.cancel();
        }
        if (khachHangYeuCauDialog != null) {
            khachHangYeuCauDialog.cancel();
        }
        super.onDestroy();
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
        fragmentIsShow = new Fragment();
        intentFilter = new IntentFilter();

    }

    @Override
    public void setEvents() {
        tvAdmin.setText(admin.getTenDangNhap());
        tvHoTen.setText("(" + admin.getHoTen() + ")");
        if (admin.getType() == 2) {
            btnManage.setEnabled(false);
            btnThongKe.setEnabled(false);
        }
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

        intentFilter.addAction(MyFirebaseMessagingService.DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.HUY_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.UPDATE_DAT_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.LOGOUT_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_VAO_BAN_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_HANG_REGISTER_ACTION);
        intentFilter.addAction(MyFirebaseMessagingService.KHACH_HANG_YEU_CAU_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

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
        if (isShowFloatButton) {
            if (size == 0) {
                removeFloatButton();
            } else {

                tvNumber.setText(size + "");
                wm.updateViewLayout(layoutFloat, params);
            }
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
    public void setYeuCauList() {
        khachHangYeuCauDialog.setYeuCauList();
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

        fillFrame(phucVuFragment, btnPhucVu);
    }

    private void fillFrame(final Fragment fragment, Button button) {
        if (button.isSelected()) return;

        btnSelected.setSelected(false);
        button.setSelected(true);
        btnSelected = button;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow.isVisible()) {

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
    public void showConnectFailDialog() {
        notifiDialog.notifi(Utils.getStringByRes(R.string.kiem_tra_ket_noi_mang));
    }
}
