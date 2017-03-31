package com.thanggun99.khachhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.MonOrder;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;


public class MonOrderAdapter extends RecyclerView.Adapter<MonOrderAdapter.ViewHolder> {
    private ArrayList<MonOrder> monOrderList;
    private int currentPosition;
    private Context context;
    private KhachHangPresenter khachHangPresenter;

    public MonOrderAdapter(Context context, KhachHangPresenter khachHangPresenter) {
        this.context = context;
        this.khachHangPresenter = khachHangPresenter;
        this.monOrderList = khachHangPresenter.getKhachHang().getCurrentHoaDon().getMonOrderList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MonOrder monOrder = monOrderList.get(position);

        holder.tvTenMon.setText(monOrder.getTenMon());
        holder.tvSoLuong.setText(monOrder.getSoLuong() + monOrder.getDonViTinh());
        holder.tvThanhTien.setText(Utils.formatMoney(monOrder.getTongTien()));
        holder.ratingBar.setRating(monOrder.getRating() / monOrder.getPersonRating());
        holder.tvRatingPoint.setText(monOrder.getRatingPoint());
        Glide.with(context)
                .load(monOrder.getHinhAnh())
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(holder.ivMon);

    }

    @Override
    public int getItemCount() {
        if (monOrderList == null) return 0;
        return monOrderList.size();
    }

    public void updateMonOrder(MonOrder currentMonOrder) {
        notifyItemChanged(monOrderList.indexOf(currentMonOrder));
    }

    public void deleteMonOrder() {
        notifyItemRemoved(currentPosition);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getSize() {
        if (monOrderList == null) return 0;
        return monOrderList.size();
    }

    public int getPositionOf(MonOrder monOrder) {
        return monOrderList.indexOf(monOrder);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenMon;
        TextView tvThanhTien;
        TextView tvSoLuong;
        ImageView ivMon;
        ImageButton btnDelete;
        RatingBar ratingBar;
        TextView tvRatingPoint;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvRatingPoint = (TextView) itemView.findViewById(R.id.tv_point_rating);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvThanhTien = (TextView) itemView.findViewById(R.id.tv_don_gia);
            tvSoLuong = (TextView) itemView.findViewById(R.id.tv_so_luong);
            ivMon = (ImageView) itemView.findViewById(R.id.iv_mon);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete_mon_order);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_message);

            tvTenMon.setMovementMethod(new ScrollingMovementMethod());
            tvTenMon.setOnClickListener(this);

            btnDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();

            switch (v.getId()) {
                case R.id.btn_delete_mon_order:
                    break;
                default:
                    khachHangPresenter.onClickMonOrder(monOrderList.get(currentPosition));
                    break;
            }
        }
    }
}
