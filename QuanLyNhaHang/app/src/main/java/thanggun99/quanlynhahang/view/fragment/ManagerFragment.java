package thanggun99.quanlynhahang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.view.fragment.manager.BanManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.LoaiMonManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.NhanVienManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.ThucDonManagerFragment;
import thanggun99.quanlynhahang.view.fragment.manager.TinTucManagerFragment;

@SuppressLint("ValidFragment")
public class ManagerFragment extends BaseFragment implements View.OnClickListener {
    private Button btnSelected, btnNews, btnBan, btnThucDon, btnLoaiMon, btnNhanVien;
    private FrameLayout frameLayout;

    private Fragment fragmentIsShow;
    private TinTucManagerFragment tinTucManagerFragment;
    private ThucDonManagerFragment thucDonManagerFragment;
    private BanManagerFragment banManagerFragment;
    private NhanVienManagerFragment nhanVienManagerFragment;
    private LoaiMonManagerFragment loaiMonManagerFragment;

    private MainPresenter mainPresenter;
    private Database database;

    public ManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_manager);
        this.mainPresenter = mainPresenter;
        this.database = mainPresenter.getDatabase();
    }

    @Override
    public void findViews(View view) {
        frameLayout = (FrameLayout) view.findViewById(R.id.frame);

        btnSelected = new Button(getContext());
        btnBan = (Button) view.findViewById(R.id.btn_ql_ban);
        btnLoaiMon = (Button) view.findViewById(R.id.btn_ql_loai_mon);
        btnNhanVien = (Button) view.findViewById(R.id.btn_ql_nhan_vien);
        btnThucDon = (Button) view.findViewById(R.id.btn_ql_thuc_don);
        btnNews = (Button) view.findViewById(R.id.btn_ql_tin_tuc);
    }

    @Override
    public void initComponents() {
        fragmentIsShow = new Fragment();
        tinTucManagerFragment = new TinTucManagerFragment(mainPresenter);
        fillFrame(tinTucManagerFragment, btnNews);

    }

    @Override
    public void setEvents() {
        btnThucDon.setOnClickListener(this);
        btnNews.setOnClickListener(this);
        btnNhanVien.setOnClickListener(this);
        btnLoaiMon.setOnClickListener(this);
        btnBan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ql_ban:
                if (banManagerFragment == null)
                    banManagerFragment = new BanManagerFragment(mainPresenter);

                fillFrame(banManagerFragment, btnBan);
                break;
            case R.id.btn_ql_loai_mon:
                if (loaiMonManagerFragment == null)
                    loaiMonManagerFragment = new LoaiMonManagerFragment(mainPresenter);

                fillFrame(loaiMonManagerFragment, btnLoaiMon);
                break;
            case R.id.btn_ql_nhan_vien:
                if (nhanVienManagerFragment == null)
                    nhanVienManagerFragment = new NhanVienManagerFragment(mainPresenter);

                fillFrame(nhanVienManagerFragment, btnNhanVien);
                break;
            case R.id.btn_ql_thuc_don:
                if (thucDonManagerFragment == null)
                    thucDonManagerFragment = new ThucDonManagerFragment(mainPresenter);

                fillFrame(thucDonManagerFragment, btnThucDon);
                break;
            case R.id.btn_ql_tin_tuc:
                if (tinTucManagerFragment == null)
                    tinTucManagerFragment = new TinTucManagerFragment(mainPresenter);

                fillFrame(tinTucManagerFragment, btnNews);
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

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        if (fragmentIsShow.isVisible()) {

            transaction.hide(fragmentIsShow);
            fragmentIsShow.onPause();
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.manager_frame, fragment);
        }
        transaction.commit();
        fragmentIsShow = fragment;
    }
}
