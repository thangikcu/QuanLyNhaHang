package thanggun99.quanlynhahang.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.CommondActionForView;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ErrorDialog;
import thanggun99.quanlynhahang.view.dialog.NotifiDialog;
import thanggun99.quanlynhahang.view.fragment.DatBanFragment;
import thanggun99.quanlynhahang.view.fragment.HomeFragment;
import thanggun99.quanlynhahang.view.fragment.PhucVuFragment;
import thanggun99.quanlynhahang.view.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements CommondActionForView, View.OnClickListener, MainPresenter.MainView {
    private Database database;

    private MainPresenter mainPresenter;
    private PhucVuPresenter phucVuPresenter;

    private Button btnHome, btnPhucVu, btnManage, btnStatistic, btnDatBan, btnSetting, btnSelected;
    private PhucVuFragment phucVuFragment;
    private SettingFragment settingFragment;
    private HomeFragment homeFragment;
    private Fragment fragmentIsShow;
    private DatBanFragment datBanFragment;
    private ProgressDialog progressDialog;
    private ErrorDialog errorDialog;
    private NotifiDialog notifiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews(null);
        initComponents();
        setEvents();
        mainPresenter.getDatas();
    }

    @Override
    public void findViews(View view) {
        btnHome = (Button) findViewById(R.id.btn_home);
        btnPhucVu = (Button) findViewById(R.id.btn_sell);
        btnManage = (Button) findViewById(R.id.btn_manager);
        btnStatistic = (Button) findViewById(R.id.btn_statistic);
        btnDatBan = (Button) findViewById(R.id.btn_dat_ban);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        //btnSelected = btnHome;
        btnSelected = btnPhucVu;
    }

    @Override
    public void initComponents() {

        database = new Database();
        mainPresenter = new MainPresenter(this, database);
        phucVuPresenter = new PhucVuPresenter(this, database);

        notifiDialog = new NotifiDialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        errorDialog = new ErrorDialog(this, mainPresenter);

        phucVuFragment = new PhucVuFragment(phucVuPresenter);
        fragmentIsShow = new Fragment();

    }

    @Override
    public void setEvents() {
        btnHome.setOnClickListener(this);
        //btnHome.setSelected(true);
        btnPhucVu.setSelected(true);
        btnPhucVu.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
        btnDatBan.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                if (homeFragment == null) homeFragment = new HomeFragment();
                fillFrame(homeFragment, btnHome);
                break;
            case R.id.btn_sell:
                if (phucVuFragment == null) phucVuFragment = new PhucVuFragment(phucVuPresenter);
                fillFrame(phucVuFragment, btnPhucVu);
                break;
            case R.id.btn_manager:
                break;
            case R.id.btn_statistic:
                break;
            case R.id.btn_dat_ban:
                if (datBanFragment == null) datBanFragment = new DatBanFragment(phucVuPresenter);
                fillFrame(datBanFragment, btnDatBan);
                break;
            case R.id.btn_setting:
                if (settingFragment == null) settingFragment = new SettingFragment();
                fillFrame(settingFragment, btnSetting);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        fragmentIsShow.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (notifiDialog != null) {
            notifiDialog.cancel();
        }
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if (errorDialog != null) {
            errorDialog.cancel();
        }
        super.onDestroy();
    }

    private void fillFrame(Fragment fragment, Button button) {
        if (fragment.isVisible()) return;

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
        transaction.commit();
        fragmentIsShow = fragment;
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
