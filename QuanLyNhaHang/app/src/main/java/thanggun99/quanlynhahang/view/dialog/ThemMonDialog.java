package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.MonManager;
import thanggun99.quanlynhahang.model.entity.Mon;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.util.Utils;

public class ThemMonDialog extends BaseDialog {
    public static final int SELECT_PHOTO = 1;
    private String action = "";

    private MonManager monManager;
    private ImageView ivHinhAnh;
    private EditText edtTenMon, edtDonViTinh, edtDonGia;
    private CheckBox ckbHienThi;
    private TextView btnChonHinh;
    private byte[] hinhAnhByte;

    private Spinner spnNhomMon;
    private ArrayList<NhomMon> nhomMonList;
    private ArrayAdapter<String> nhomMonAdapter;


    public ThemMonDialog(Context context, MonManager monManager) {
        super(context);
        this.monManager = monManager;
        setContentView(R.layout.dialog_them_mon);
        setCancelable(true);

        nhomMonList = monManager.getNhomMonList();

        nhomMonAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nhomMonList);

        nhomMonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnNhomMon = (Spinner) findViewById(R.id.spn_nhom_mon);
        spnNhomMon.setAdapter(nhomMonAdapter);

        ckbHienThi = (CheckBox) findViewById(R.id.ckb_hien_thi);
        ivHinhAnh = (ImageView) findViewById(R.id.iv_hinh_anh);
        edtTenMon = (EditText) findViewById(R.id.edt_ten_mon);
        edtDonViTinh = (EditText) findViewById(R.id.edt_don_vi_tinh);
        edtDonGia = (EditText) findViewById(R.id.edt_don_gia);
        btnChonHinh = (TextView) findViewById(R.id.btn_chon_hinh);

        btnChonHinh.setOnClickListener(this);

        btnCancle = (Button) findViewById(R.id.btn_cancel);
        btnOk = (Button) findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_ok) {
            if (checkForm()) {

                int hienThi = 0;
                if (ckbHienThi.isChecked()) {
                    hienThi = 1;
                }
                Mon mon = new Mon();
                mon.setHinhAnhString(Utils.getStringImage(hinhAnhByte));
                mon.setHienThi(hienThi);
                mon.setHinhAnh(hinhAnhByte);
                mon.setTenMon(edtTenMon.getText().toString().trim());
                mon.setDonViTinh(edtDonViTinh.getText().toString().trim());
                mon.setDonGia(Integer.parseInt(edtDonGia.getText().toString().trim()));
                mon.setMaLoai(nhomMonList.get(spnNhomMon.getSelectedItemPosition()).getMaLoai());
                mon.setPersonRating(1);
                mon.setRating((float) 3.5);

                if (action.equals("update")) {
                    monManager.updateMon(mon);
                } else {

                    monManager.addMon(mon);
                }
                dismiss();

            }
        }
        if (v.getId() == R.id.btn_chon_hinh) {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            monManager.getFragment().startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    }

    private boolean checkForm() {
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(edtDonGia.getText())) {
            cancel = true;
            focusView = edtDonGia;
            edtDonGia.setError(Utils.getStringByRes(R.string.nhap_so_luong));
        }
        if (TextUtils.isEmpty(edtDonViTinh.getText())) {
            cancel = true;
            focusView = edtDonViTinh;
            edtDonViTinh.setError(Utils.getStringByRes(R.string.nhap_don_vi_tinh));
        }
        if (TextUtils.isEmpty(edtTenMon.getText())) {
            cancel = true;
            focusView = edtTenMon;
            edtTenMon.setError(Utils.getStringByRes(R.string.nhap_ten_mon));
        }
        if (hinhAnhByte == null) {
            cancel = true;
            focusView = btnChonHinh;
            btnChonHinh.setError(Utils.getStringByRes(R.string.chon_hinh));
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    public void showImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        if (bitmap != null) {
            hinhAnhByte = Utils.getByteImage(bitmap);

            Glide.with(getContext())
                    .load(hinhAnhByte)
                    .into(ivHinhAnh);
        }

    }

    public void clear() {
        ivHinhAnh.setImageBitmap(null);
        hinhAnhByte = null;
        action = "";
        edtDonViTinh.setText(null);
        edtTenMon.setText(null);
        edtDonGia.setText(null);

        edtDonGia.setError(null);
        edtTenMon.setError(null);
        btnChonHinh.setError(null);
        edtDonViTinh.setError(null);

    }

    public void fillContent(Mon mon) {
        action = "update";
        hinhAnhByte = mon.getHinhAnh();
        Glide.with(getContext())
                .load(hinhAnhByte)
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(ivHinhAnh);
        edtTenMon.setText(mon.getTenMon());
        edtDonViTinh.setText(mon.getDonViTinh());
        edtDonGia.setText(mon.getDonGia() + "");

        for (NhomMon nhomMon : nhomMonList) {
            if (nhomMon.getMaLoai() == mon.getMaLoai()) {
                spnNhomMon.setSelection(nhomMonList.indexOf(nhomMon));
            }
        }

        if (mon.getHienThi() == 0) {
            ckbHienThi.setChecked(false);
        } else {
            ckbHienThi.setChecked(true);
        }
    }
}
