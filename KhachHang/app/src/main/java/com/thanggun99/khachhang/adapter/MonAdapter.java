package com.thanggun99.khachhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.Mon;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;


/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.ViewHolder> {
    private ArrayList<Mon> monList;
    private KhachHangPresenter khachHangPresenter;
    private Context context;

    public MonAdapter(Context context, KhachHangPresenter khachHangPresenter) {
        this.context = context;
        this.khachHangPresenter = khachHangPresenter;
        this.monList = khachHangPresenter.getDatabase()
                .getListThucDonByMaLoai(khachHangPresenter.getDatabase().getNhomMonList().get(0).getMaLoai());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mon mon = monList.get(position);

        holder.tvTenMon.setText(mon.getTenMon());
        holder.tvTenMon.scrollTo(0, 0);
        holder.tvDonGia.setText(Utils.formatMoney(mon.getDonGia()) + "/" + mon.getDonViTinh());
        holder.ratingBar.setRating(mon.getRating() / mon.getPersonRating());
        holder.tvRatingPoint.setText(mon.getRatingPoint());
        Glide.with(context)
                .load(mon.getHinhAnh())
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(holder.ivMon);
    }

    @Override
    public int getItemCount() {
        if (monList == null) return 0;
        return monList.size();
    }

    public void changeData(ArrayList<Mon> data) {
        monList = data;
        notifyDataSetChanged();
    }

    public Mon getItem(int position) {
        return monList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenMon;
        TextView tvDonGia;
        ImageView ivMon;
        RatingBar ratingBar;
        TextView tvRatingPoint;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvRatingPoint = (TextView) itemView.findViewById(R.id.tv_point_rating);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvDonGia = (TextView) itemView.findViewById(R.id.tv_don_gia);
            ivMon = (ImageView) itemView.findViewById(R.id.iv_mon);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_message);

            tvTenMon.setMovementMethod(new ScrollingMovementMethod());
            tvTenMon.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            khachHangPresenter.onClickMon(getItem(getAdapterPosition()));
        }
    }
}
