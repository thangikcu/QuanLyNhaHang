package com.thanggun99.khachhang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thanggun99.khachhang.R;
import com.thanggun99.khachhang.model.entity.DatBan;
import com.thanggun99.khachhang.model.entity.TinTuc;
import com.thanggun99.khachhang.util.Utils;

import java.util.ArrayList;


/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class TinTucAdapter extends RecyclerView.Adapter<TinTucAdapter.ViewHolder> {

    private ProgressDialog progressDialog;
    private OnClickTinTucListener onClickTinTucListener;
    private ArrayList<TinTuc> tinTucList;
    private Context context;

    public TinTucAdapter(Context context) {

        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(Utils.getStringByRes(R.string.loading));
        progressDialog.setCancelable(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tin_tuc, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TinTuc tinTuc = tinTucList.get(position);

        holder.tvTitle.setText(tinTuc.getTieuDe());
        holder.tvNgayDang.setText(tinTuc.getNgayDang());
        holder.tvNoiDung.setText(tinTuc.getNoiDung());
        Glide.with(context)
                .load(tinTuc.getHinhAnh())
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(holder.ivHinhAnh);

    }

    @Override
    public int getItemCount() {
        if (tinTucList != null) {
            return tinTucList.size();
        }
        return 0;
    }

    public void notifyItemRemoved(DatBan datBan) {
        if (datBan != null) {

            notifyItemRemoved(tinTucList.indexOf(datBan));
        }
    }

    public void notifyItemChanged(TinTuc tinTuc) {
        if (tinTuc != null) {
            notifyItemChanged(tinTucList.indexOf(tinTuc));
        }
    }

    public void setDatas(ArrayList<TinTuc> datas) {
        this.tinTucList = datas;
    }

    public void changeData(ArrayList<TinTuc> tinTucList) {
        this.tinTucList = tinTucList;
        notifyDataSetChanged();
    }

    public void setOnClickTinTucListener(OnClickTinTucListener onClickTinTucListener) {
        this.onClickTinTucListener = onClickTinTucListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvNgayDang;
        TextView tvNoiDung;
        ImageView ivHinhAnh;

        public ViewHolder(View itemView) {
            super(itemView);

            ivHinhAnh = (ImageView) itemView.findViewById(R.id.iv_tin_tuc);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTitle.setMovementMethod(new ScrollingMovementMethod());
            tvNgayDang = (TextView) itemView.findViewById(R.id.tv_ngay_dang);
            tvNoiDung = (TextView) itemView.findViewById(R.id.tv_noi_dung);

            tvTitle.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    onClickTinTucListener.onClickTinTuc(tinTucList.get(getAdapterPosition()));
                }
            }, 500);
        }
    }

    public interface OnClickTinTucListener {
        void onClickTinTuc(TinTuc tinTuc);

    }

}
