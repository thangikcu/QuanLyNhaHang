package com.thanggun99.khachhang.view.fragment;


import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.adapter.TinTucAdapter;
import com.thanggun99.khachhang.model.entity.TinTuc;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

@SuppressLint("ValidFragment")
public class TinTucFragment extends BaseFragment implements KhachHangPresenter.TinTucView, TinTucAdapter.OnClickTinTucListener {
    private RecyclerView tinTucRecyclerView;
    private TinTucAdapter tinTucAdapter;
    private LinearLayout tinTucLayout;
    private ImageButton btnBack;
    private TextView tvTieuDe, tvNoiDung;
    private ImageView ivHinhAnh;
    private KhachHangPresenter khachHangPresenter;

    public TinTucFragment(KhachHangPresenter khachHangPresenter) {
        super(R.layout.fragment_tin_tuc);
        this.khachHangPresenter = khachHangPresenter;
    }

    @Override
    public void findViews(View view) {
        tinTucLayout = (LinearLayout) view.findViewById(R.id.layout_tin_tuc);
        btnBack = (ImageButton) tinTucLayout.findViewById(R.id.btn_back);
        tvTieuDe = (TextView) tinTucLayout.findViewById(R.id.tv_tieu_de);
        tvNoiDung = (TextView) tinTucLayout.findViewById(R.id.tv_noi_dung);
        ivHinhAnh = (ImageView) tinTucLayout.findViewById(R.id.iv_hinh_anh);

        tinTucRecyclerView = (RecyclerView) view.findViewById(R.id.list_tin_tuc);
    }

    @Override
    public void initComponents() {
        tinTucAdapter = new TinTucAdapter(getContext());
        tinTucAdapter.setOnClickTinTucListener(this);
    }

    @Override
    public void setEvents() {
        tinTucRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tinTucLayout.setVisibility(View.GONE);
        tinTucRecyclerView.setVisibility(View.VISIBLE);

        tvNoiDung.setMovementMethod(new ScrollingMovementMethod());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinTucLayout.setVisibility(View.GONE);
                tinTucRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        khachHangPresenter.setTinTucView(this);

    }

    @Override
    public void showTinTucList() {
        tinTucAdapter.setDatas(khachHangPresenter.getTinTucList());
        tinTucRecyclerView.setAdapter(tinTucAdapter);
    }

    @Override
    public void showErrorLoadTinTuc() {
        Utils.notifi(Utils.getStringByRes(R.string.tai_danh_sach_tin_tuc_that_bai));
    }

    @Override
    public void onClickTinTuc(TinTuc tinTuc) {
        tvNoiDung.scrollTo(0, 0);
        tvTieuDe.setText(tinTuc.getTieuDe());
        tvNoiDung.setText(tinTuc.getNoiDung());
        Glide.with(getContext())
                .load(tinTuc.getHinhAnh())
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(ivHinhAnh);

        tinTucLayout.setVisibility(View.VISIBLE);
        tinTucRecyclerView.setVisibility(View.GONE);

    }
}
