package com.thanggun99.khachhang.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thanggun99.khachhang.App;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.NhomMon;
import com.thanggun99.khachhang.presenter.KhachHangPresenter;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class NhomMonAdapter extends RecyclerView.Adapter<NhomMonAdapter.ViewHolder> {
    private ArrayList<NhomMon> nhomMonList;
    private KhachHangPresenter khachHangPresenter;
    private ProgressDialog progressDialog;
    private NhomMon nhomMonSelected;

    public NhomMonAdapter(Context context, KhachHangPresenter khachHangPresenter) {
        this.khachHangPresenter = khachHangPresenter;
        this.nhomMonList = khachHangPresenter.getDatabase().getNhomMonList();

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setCancelable(false);


        NhomMon nhomMon = nhomMonList.get(0);
        nhomMon.setSelected(true);
        nhomMonSelected = nhomMon;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhom_mon, parent, false));
    }

    @Override
    @SuppressLint("NewApi")
    public void onBindViewHolder(ViewHolder holder, int position) {
        NhomMon nhomMon = nhomMonList.get(position);

        holder.tvTenNhom.setText(nhomMon.getTenLoai());
        holder.tvTenNhom.setBackgroundColor(nhomMon.getMauSac());

        Drawable drawable = App.getContext().getDrawable(R.drawable.bg_nhom_mon_item);
        drawable.setColorFilter(nhomMon.getMauSac(), PorterDuff.Mode.MULTIPLY);
        holder.tvTenNhom.setBackground(drawable);

        if (nhomMon.getSelected()) {
            holder.tvTenNhom.setSelected(true);
        } else {
            holder.tvTenNhom.setSelected(false);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (nhomMonList == null) return 0;
        return nhomMonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNhom;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenNhom = (TextView) itemView.findViewById(R.id.tv_ten_nhom_mon);

            tvTenNhom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final NhomMon nhomMon = nhomMonList.get(getAdapterPosition());
                    if (nhomMon.getSelected()) {

                        return;
                    }

                    nhomMonSelected.setSelected(false);
                    notifyItemChanged(nhomMonList.indexOf(nhomMonSelected));

                    nhomMon.setSelected(true);
                    notifyItemChanged(getAdapterPosition());

                    nhomMonSelected = nhomMon;

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();
                            khachHangPresenter.onClickNhomMon(nhomMon);

                        }
                    }, 500);
                }
            });

        }
    }

}
