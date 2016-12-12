package thanggun99.quanlynhahang.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.view.fragment.HomeFragment;
import thanggun99.quanlynhahang.view.fragment.PhucVuFragment;
import thanggun99.quanlynhahang.view.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnHome, btnSell, btnManage, btnStatistic, btnRepository, btnSetting, btnSelected;
    private PhucVuFragment sellFragment;
    private SettingFragment settingFragment;
    private HomeFragment homeFragment;
    private Fragment fragmentIsShow;


    public MainActivity() {
        homeFragment = new HomeFragment();
        fragmentIsShow = new Fragment();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setEvents();
        fillFrame(homeFragment, btnHome);
    }

    private void setEvents() {
        btnHome.setOnClickListener(this);
        btnHome.setSelected(true);
        btnSell.setOnClickListener(this);
        btnManage.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
        btnRepository.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    private void initViews() {
        btnHome = (Button) findViewById(R.id.btn_home);
        btnSell = (Button) findViewById(R.id.btn_sell);
        btnManage = (Button) findViewById(R.id.btn_manager);
        btnStatistic = (Button) findViewById(R.id.btn_statistic);
        btnRepository = (Button) findViewById(R.id.btn_repository);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        btnSelected = btnHome;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                fillFrame(homeFragment, btnHome);
                break;
            case R.id.btn_sell:
                if (sellFragment == null) sellFragment = new PhucVuFragment();
                fillFrame(sellFragment, btnSell);
                break;
            case R.id.btn_manager:
                break;
            case R.id.btn_statistic:
                break;
            case R.id.btn_repository:
                break;
            case R.id.btn_setting:
                if (settingFragment == null) settingFragment = new SettingFragment();
                fillFrame(settingFragment, btnSetting);
                break;
            default:
                break;
        }
    }

    private void fillFrame(Fragment fragment, Button button) {
        if (fragment.isVisible()) return;

        btnSelected.setSelected(false);
        button.setSelected(true);
        btnSelected = button;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragmentIsShow.isVisible()) transaction.hide(fragmentIsShow);

        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction
                    .add(R.id.frame, fragment);
        }
        transaction.commit();
        fragmentIsShow = fragment;
    }

}
