package thanggun99.quanlynhahang.view.fragment.manager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.TinTucAdapter;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.TinTucManager;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ThemTinTucDialog;
import thanggun99.quanlynhahang.view.fragment.BaseFragment;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class TinTucManagerFragment extends BaseFragment implements TinTucManager.TinTucManagerView, View.OnClickListener {

    private Database database;
    private TinTucManager tinTucManager;
    private MainPresenter mainPresenter;

    private Button btnThemMoi;
    private RecyclerView tinTucRecyclerView;
    private TinTucAdapter tinTucAdapter;
    private SearchView edtTimKiemTinTuc;
    private ThemTinTucDialog themTinTucDialog;

    public TinTucManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_tin_tuc_manager);
        this.database = mainPresenter.getDatabase();
        this.mainPresenter = mainPresenter;
    }

    public void getData() {
        tinTucManager.getDatas();
    }

    @Override
    public void findViews(View view) {
        edtTimKiemTinTuc = (SearchView) view.findViewById(R.id.edt_tim_kiem_tin_tuc);
        tinTucRecyclerView = (RecyclerView) view.findViewById(R.id.list_tin_tuc);
        btnThemMoi = (Button) view.findViewById(R.id.btn_them_tin_tuc);
    }

    @Override
    public void initComponents() {
        tinTucManager = new TinTucManager(mainPresenter);

        tinTucAdapter = new TinTucAdapter(getContext(), tinTucManager);

        themTinTucDialog = new ThemTinTucDialog(getContext(), tinTucManager);

    }

    @Override
    public void setEvents() {
        tinTucRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        edtTimKiemTinTuc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                edtTimKiemTinTuc.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyWord) {
                tinTucAdapter.changeData(tinTucManager.findDatBan(keyWord));
                return true;
            }
        });

        edtTimKiemTinTuc.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtTimKiemTinTuc.onActionViewCollapsed();
                    tinTucAdapter.showAllData();
                }
            }
        });

        btnThemMoi.setOnClickListener(this);

        tinTucManager.setThemTinTucDialog(themTinTucDialog);
        tinTucManager.setTinTucManagerView(this);
        tinTucManager.setFragment(this);

        tinTucManager.getDatas();
    }

    @Override
    public void onFinishGetDatas() {
        tinTucAdapter.setDatas(database.getTinTucList());
        tinTucRecyclerView.setAdapter(tinTucAdapter);
    }

    @Override
    public void onGetDatasFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.tai_danh_sach_tin_tuc_that_bai));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ThemTinTucDialog.SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            themTinTucDialog.showImage(Utils.compressImage(imagePath));

            cursor.close();
        }

    }

    @Override
    public void onFinishUpdateTinTuc() {
        tinTucAdapter.notifyItemChanged(tinTucManager.getCurrentTinTuc());
    }

    @Override
    public void onUpdateTinTucFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.update_tin_tuc_that_bai));
    }

    @Override
    public void onDeleteTinTucFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.xoa_tin_tuc_that_bai));
    }

    @Override
    public void onFinishDeleteTinTucSuccess() {
        tinTucAdapter.removeTinTuc();
    }

    @Override
    public void onFinishAddTinTucSuccess() {
        tinTucRecyclerView.scrollToPosition(0);
        tinTucAdapter.notifyItemInserted(0);
    }

    @Override
    public void onAddTinTucFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.them_tin_tuc_that_bai));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_them_tin_tuc) {
            themTinTucDialog.clear();
            themTinTucDialog.show();
        }
    }
}
