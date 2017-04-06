package thanggun99.quanlynhahang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.view.fragment.manager.BanManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.KhachHangManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.LoaiMonManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.MonManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.NhanVienManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.TinTucManagerFragment;

@SuppressLint("ValidFragment")
public class ManagerFragment extends BaseFragment implements View.OnClickListener {
    private Button btnSelected, btnQlTinTuc, btnQlBan, btnQlMon, btnQlLoaiMon, btnQlNhanVien, btnQlKhachHang;

    private Fragment fragmentIsShow;
    private TinTucManagerFragment tinTucManagerFragment;
    private MonManagerFragment monManagerFragment;
    private BanManagerFragment banManagerFragment;
    private NhanVienManagerFragment nhanVienManagerFragment;
    private LoaiMonManagerFragment loaiMonManagerFragment;
    private KhachHangManagerFragment khachHangManagerFragment;

    private MainPresenter mainPresenter;

    public ManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_manager);
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void findViews(View view) {

        btnQlBan = (Button) view.findViewById(R.id.btn_ql_ban);
        btnQlLoaiMon = (Button) view.findViewById(R.id.btn_ql_loai_mon);
        btnQlNhanVien = (Button) view.findViewById(R.id.btn_ql_nhan_vien);
        btnQlMon = (Button) view.findViewById(R.id.btn_ql_thuc_don);
        btnQlTinTuc = (Button) view.findViewById(R.id.btn_ql_tin_tuc);
        btnQlKhachHang = (Button) view.findViewById(R.id.btn_ql_khach_hang);

        btnSelected = new Button(getContext());
    }

    @Override
    public void initComponents() {
        tinTucManagerFragment = new TinTucManagerFragment(mainPresenter);

    }

    @Override
    public void setEvents() {
        btnQlMon.setOnClickListener(this);
        btnQlTinTuc.setOnClickListener(this);
        btnQlNhanVien.setOnClickListener(this);
        btnQlLoaiMon.setOnClickListener(this);
        btnQlBan.setOnClickListener(this);
        btnQlKhachHang.setOnClickListener(this);

        fillFrame(tinTucManagerFragment, btnQlTinTuc);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ql_ban:
                if (banManagerFragment == null)
                    banManagerFragment = new BanManagerFragment(mainPresenter);

                fillFrame(banManagerFragment, btnQlBan);
                break;
            case R.id.btn_ql_loai_mon:
                if (loaiMonManagerFragment == null)
                    loaiMonManagerFragment = new LoaiMonManagerFragment(mainPresenter);

                fillFrame(loaiMonManagerFragment, btnQlLoaiMon);
                break;
            case R.id.btn_ql_nhan_vien:
                if (nhanVienManagerFragment == null)
                    nhanVienManagerFragment = new NhanVienManagerFragment(mainPresenter);

                fillFrame(nhanVienManagerFragment, btnQlNhanVien);
                break;
            case R.id.btn_ql_thuc_don:
                if (monManagerFragment == null)
                    monManagerFragment = new MonManagerFragment(mainPresenter);

                fillFrame(monManagerFragment, btnQlMon);
                break;
            case R.id.btn_ql_tin_tuc:
                if (tinTucManagerFragment == null) {

                    tinTucManagerFragment = new TinTucManagerFragment(mainPresenter);
                } else {
                    tinTucManagerFragment.getData();
                }

                fillFrame(tinTucManagerFragment, btnQlTinTuc);
                break;
            case R.id.btn_ql_khach_hang:
                if (khachHangManagerFragment == null)
                    khachHangManagerFragment = new KhachHangManagerFragment(mainPresenter);

                fillFrame(khachHangManagerFragment, btnQlKhachHang);
                break;
            default:
                break;
        }
    }

    private void fillFrame(final Fragment fragment, Button button) {
        if (button.isSelected()) return;

        btnSelected.setSelected(false);
        button.setSelected(true);
        btnSelected = button;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow != null && fragmentIsShow.isVisible()) {

            transaction.hide(fragmentIsShow);
            fragmentIsShow.onPause();
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.manager_frame, fragment);
        }
        transaction.commitAllowingStateLoss();
        fragmentIsShow = fragment;
    }

}
