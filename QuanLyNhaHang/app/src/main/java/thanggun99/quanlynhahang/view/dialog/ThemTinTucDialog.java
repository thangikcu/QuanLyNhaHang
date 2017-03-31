package thanggun99.quanlynhahang.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.TinTucManager;
import thanggun99.quanlynhahang.model.entity.TinTuc;
import thanggun99.quanlynhahang.util.Utils;

public class ThemTinTucDialog extends BaseDialog {
    public static final int SELECT_PHOTO = 1;
    private String action = "";
    private String date;

    private TinTucManager tinTucManager;
    private ImageView ivHinhAnh;
    private EditText edtTieuDe, edtNoiDung, edtNgayDang;
    private CheckBox ckbHienThi;
    private TextView btnChonHinh;
    private byte[] hinhAnhByte;


    public ThemTinTucDialog(Context context, TinTucManager tinTucManager) {
        super(context);
        this.tinTucManager = tinTucManager;
        setContentView(R.layout.dialog_them_tin_tuc);
        setCancelable(true);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        date = day + "/" + month + "/" + year;

        ckbHienThi = (CheckBox) findViewById(R.id.ckb_hien_thi);
        ivHinhAnh = (ImageView) findViewById(R.id.iv_hinh_anh);
        edtTieuDe = (EditText) findViewById(R.id.edt_tieu_de);
        edtTieuDe.setMovementMethod(new ScrollingMovementMethod());
        edtNoiDung = (EditText) findViewById(R.id.edt_noi_dung);
        edtNgayDang = (EditText) findViewById(R.id.edt_ngay_dang);
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
                TinTuc tinTuc = new TinTuc();
                tinTuc.setHinhAnhString(Utils.getStringImage(hinhAnhByte));
                tinTuc.setNgayDang(edtNgayDang.getText().toString().trim());
                tinTuc.setHienThi(hienThi);
                tinTuc.setHinhAnh(hinhAnhByte);
                tinTuc.setTieuDe(edtTieuDe.getText().toString().trim());
                tinTuc.setNoiDung(edtNoiDung.getText().toString().trim());

                if (action.equals("update")) {
                    tinTucManager.updateTinTuc(tinTuc);
                } else {

                    tinTucManager.addTinTuc(tinTuc);
                }
                dismiss();

            }
        }
        if (v.getId() == R.id.btn_chon_hinh) {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            tinTucManager.getFragment().startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    }

    private boolean checkForm() {
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(edtNgayDang.getText())) {
            cancel = true;
            focusView = edtNgayDang;
            edtNgayDang.setError(Utils.getStringByRes(R.string.nhap_ngay_dang));
        }
        if (TextUtils.isEmpty(edtNoiDung.getText())) {
            cancel = true;
            focusView = edtNoiDung;
            edtNoiDung.setError(Utils.getStringByRes(R.string.nhap_noi_dung));
        }
        if (TextUtils.isEmpty(edtTieuDe.getText())) {
            cancel = true;
            focusView = edtTieuDe;
            edtTieuDe.setError(Utils.getStringByRes(R.string.nhap_tieu_de));
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
        edtNoiDung.setText(null);
        edtTieuDe.setText(null);
        edtNgayDang.setText(date);

        edtNoiDung.setError(null);
        edtTieuDe.setError(null);
        edtNgayDang.setError(null);
        btnChonHinh.setError(null);

    }

    public void fillContent(TinTuc tinTuc) {
        action = "update";
        hinhAnhByte = tinTuc.getHinhAnh();
        Glide.with(getContext())
                .load(hinhAnhByte)
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(ivHinhAnh);
        edtTieuDe.setText(tinTuc.getTieuDe());
        edtNoiDung.setText(tinTuc.getNoiDung());
        edtNgayDang.setText(tinTuc.getNgayDang());

        if (tinTuc.getHienThi() == 0) {
            ckbHienThi.setChecked(false);
        } else {
            ckbHienThi.setChecked(true);
        }
    }
}
