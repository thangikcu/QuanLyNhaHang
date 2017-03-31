package thanggun99.quanlynhahang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.MonOrder;
import thanggun99.quanlynhahang.util.Utils;


/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class MonKhachHangYeuCauAdapter extends RecyclerView.Adapter<MonKhachHangYeuCauAdapter.ViewHolder> {
    private ArrayList<MonOrder> monOrderList;
    private Context context;

    public MonKhachHangYeuCauAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_khach_hang_yeu_cau, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MonOrder monOrder = monOrderList.get(position);

        holder.tvTenMon.setText(monOrder.getTenMon());
        holder.tvSoLuong.setText(monOrder.getSoLuong() + monOrder.getDonViTinh());
        holder.tvThanhTien.setText(Utils.formatMoney(monOrder.getTongTien()));
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

    public void changeData(ArrayList<MonOrder> data) {
        monOrderList = data;
        notifyDataSetChanged();
    }

    public MonOrder getItem(int position) {
        return monOrderList.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenMon;
        TextView tvSoLuong;
        ImageView ivMon;
        ImageButton btnDelete;
        TextView tvThanhTien;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvThanhTien = (TextView) itemView.findViewById(R.id.tv_don_gia);
            tvSoLuong = (TextView) itemView.findViewById(R.id.tv_so_luong);
            ivMon = (ImageView) itemView.findViewById(R.id.iv_mon);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete_mon_order);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_ten_mon);

            tvTenMon.setMovementMethod(new ScrollingMovementMethod());
            tvTenMon.setOnClickListener(this);

            btnDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_delete_mon_order:
                    break;
                default:
                    break;
            }
        }
    }
}
