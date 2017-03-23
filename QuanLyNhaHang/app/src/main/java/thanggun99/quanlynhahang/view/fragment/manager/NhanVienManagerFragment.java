package thanggun99.quanlynhahang.view.fragment.manager;


import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.TinTucAdapter;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.TinTucManager;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.fragment.BaseFragment;

@SuppressLint("ValidFragment")
public class NhanVienManagerFragment extends BaseFragment implements TinTucManager.TinTucView {
    private Button btnThemMoi;
    private RecyclerView tinTucrecyclerView;
    private TinTucAdapter tinTucAdapter;
    private Database database;
    private MainPresenter mainPresenter;
    private TinTucManager tinTucManager;


    public NhanVienManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_nhan_vien_manager);
        this.database = mainPresenter.getDatabase();
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void findViews(View view) {
        tinTucrecyclerView = (RecyclerView) view.findViewById(R.id.list_nhan_vien);
        btnThemMoi = (Button) view.findViewById(R.id.btn_them_nhan_vien);
    }

    @Override
    public void initComponents() {
        tinTucManager = new TinTucManager(database);
        tinTucManager.setTinTucView(this);

        tinTucAdapter = new TinTucAdapter();

    }

    @Override
    public void setEvents() {
        tinTucrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStartTask() {
        mainPresenter.onStartTask();
    }

    @Override
    public void onFinishGetDatas() {
        tinTucAdapter.setDatas(database.getTinTucList());
        tinTucrecyclerView.setAdapter(tinTucAdapter);
    }

    @Override
    public void onGetDatasFail() {
        Utils.notifi(Utils.getStringByRes(R.string.tai_danh_sach_tin_tuc_that_bai));
    }

    @Override
    public void onFinishTask() {
        mainPresenter.onFinishTask();
    }
}
