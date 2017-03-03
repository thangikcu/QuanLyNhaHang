package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.OnItemclickListener;
import thanggun99.quanlynhahang.model.entity.ThucDon;
import thanggun99.quanlynhahang.util.Utils;


/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class ThucDonAdapter extends RecyclerView.Adapter<ThucDonAdapter.ViewHolder> {
    private ArrayList<ThucDon> thucDons;
    private OnItemclickListener onItemclickListener;

    public ThucDonAdapter(ArrayList<ThucDon> thucDons) {
        this.thucDons = thucDons;
    }

    public void setOnItemclickListener(OnItemclickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThucDon thucDon = thucDons.get(position);

        holder.tvTenMon.setText(thucDon.getTenMon());
        holder.tvDonGia.setText(Utils.formatMoney(thucDon.getDonGia()) + "/" + thucDon.getDonViTinh());
        holder.ivThucDon.setImageBitmap(thucDon.getHinhAnh());

    }

    @Override
    public int getItemCount() {
        if (thucDons == null) return 0;
        return thucDons.size();
    }

    public void changeData(ArrayList<ThucDon> data) {
        thucDons = data;
        notifyDataSetChanged();
    }

    public ThucDon getItem(int position) {
        return thucDons.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenMon;
        TextView tvDonGia;
        ImageView ivThucDon;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_ten_mon);
            tvDonGia = (TextView) itemView.findViewById(R.id.tv_don_gia);
            ivThucDon = (ImageView) itemView.findViewById(R.id.iv_thuc_don);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclickListener.onItemClick(itemView, getAdapterPosition());
                }
            });

        }
    }
}
