package thanggun99.quanlynhahang.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.OnItemclickListener;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.util.Utils;


/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class ThucDonOrderAdapter extends RecyclerView.Adapter<ThucDonOrderAdapter.ViewHolder> {
    private ArrayList<ThucDonOrder> thucDonOrders;
    private OnItemclickListener onItemclickListener;

    public ThucDonOrderAdapter() {
    }

    public void setOnItemclickListener(OnItemclickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thuc_don_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThucDonOrder thucDonOrder = thucDonOrders.get(position);

        holder.tvTenMon.setText(thucDonOrder.getTenMon());
        holder.tvSoLuong.setText(thucDonOrder.getSoLuong() + thucDonOrder.getDonViTinh());
        holder.tvThanhTien.setText(Utils.formatMoney(thucDonOrder.getTongTien()));
        holder.ivThucDon.setImageBitmap(thucDonOrder.getHinhAnh());
    }

    @Override
    public int getItemCount() {
        if (thucDonOrders == null) return 0;
        return thucDonOrders.size();
    }

    public void changeData(ArrayList<ThucDonOrder> data) {
        thucDonOrders = data;
        notifyDataSetChanged();
    }

    public ThucDonOrder getItem(int position) {
        return thucDonOrders.get(position);
    }

    public int getSize() {
        if (thucDonOrders == null) return 0;
        return thucDonOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenMon;
        TextView tvThanhTien;
        TextView tvSoLuong;
        ImageView ivThucDon;
        ImageButton btnDelete;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_ten_mon);
            tvThanhTien = (TextView) itemView.findViewById(R.id.tv_don_gia);
            tvSoLuong = (TextView) itemView.findViewById(R.id.tv_so_luong);
            ivThucDon = (ImageView) itemView.findViewById(R.id.iv_thuc_don);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete_mon_order);
            btnDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_delete_mon_order:
                    onItemclickListener.onItemClick(btnDelete, getAdapterPosition());
                    break;
                default:
                    onItemclickListener.onItemClick(itemView, getAdapterPosition());
                    break;
            }
        }
    }
}
