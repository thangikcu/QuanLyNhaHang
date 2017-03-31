package thanggun99.quanlynhahang.view.fragment.manager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.MonManagerAdapter;
import thanggun99.quanlynhahang.model.MonManager;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ThemMonDialog;
import thanggun99.quanlynhahang.view.dialog.ThemTinTucDialog;
import thanggun99.quanlynhahang.view.fragment.BaseFragment;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class MonManagerFragment extends BaseFragment implements MonManager.MonManagerView, View.OnClickListener {
    private Button btnThemMoi;
    private SearchView edtTimKiemMon;
    private RecyclerView monRecyclerView;
    private MonManagerAdapter monManagerAdapter;
    private MainPresenter mainPresenter;
    private MonManager monManager;
    private ThemMonDialog themMonDialog;

    private Spinner spnNhomMon;
    private ArrayList<NhomMon> nhomMonList;
    private ArrayAdapter<String> nhomMonAdapter;
    private Animation animationZoom;


    public MonManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_mon_manager);
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void findViews(View view) {
        spnNhomMon = (Spinner) view.findViewById(R.id.spn_nhom_mon);
        edtTimKiemMon = (SearchView) view.findViewById(R.id.edt_tim_kiem_mon);
        monRecyclerView = (RecyclerView) view.findViewById(R.id.list_thuc_don);
        btnThemMoi = (Button) view.findViewById(R.id.btn_them_thuc_don);
    }

    @Override
    public void initComponents() {
        animationZoom = AnimationUtils.loadAnimation(getContext(), R.anim.zoom);

        monManager = new MonManager(mainPresenter);

        monManagerAdapter = new MonManagerAdapter(getContext(), monManager);

        themMonDialog = new ThemMonDialog(getContext(), monManager);

        nhomMonList = monManager.getNhomMonList();

        nhomMonAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nhomMonList);
    }

    @Override
    public void setEvents() {
        nhomMonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNhomMon.setAdapter(nhomMonAdapter);

        spnNhomMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monRecyclerView.startAnimation(animationZoom);

                monManagerAdapter.changeData(
                        monManager.getMonByMaLoai(nhomMonList.get(position).getMaLoai()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtTimKiemMon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                edtTimKiemMon.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyWord) {
                monManagerAdapter.changeData(monManager.findMon(keyWord));
                return true;
            }
        });

        edtTimKiemMon.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    edtTimKiemMon.onActionViewCollapsed();
                    monManagerAdapter.showAllData();
                }
            }
        });

        btnThemMoi.setOnClickListener(this);

        monRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        monRecyclerView.setAdapter(monManagerAdapter);

        monManager.setThemMonDialog(themMonDialog);
        monManager.setMonManagerView(this);
        monManager.setFragment(this);
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

            themMonDialog.showImage(Utils.compressImage(imagePath));

            cursor.close();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_them_thuc_don) {
            themMonDialog.clear();
            themMonDialog.show();
        }
    }

    @Override
    public void onFinishAddMonSuccess() {
        monRecyclerView.scrollToPosition(0);
        monManagerAdapter.showAllData();
    }

    @Override
    public void onAddMonFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.them_mon_that_bai));
    }

    @Override
    public void onDeleteMonFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.xoa_mon_that_bai));
    }

    @Override
    public void onFinishDeleteMonSuccess() {
        monManagerAdapter.removeMon();
    }

    @Override
    public void onFinishUpdateMon() {
        monManagerAdapter.notifyItemChanged(monManager.getCurrentMon());
    }

    @Override
    public void onUpdateMonFail() {
        Utils.notifiOnDialog(Utils.getStringByRes(R.string.update_mon_that_bai));
    }

}
