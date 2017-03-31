package thanggun99.quanlynhahang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.YeuCau;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class YeuCauAdapter extends RecyclerView.Adapter<YeuCauAdapter.ViewHolder> {

    private ArrayList<YeuCau> yeuCauList;
    private Context context;
    private PhucVuPresenter phucVuPresenter;

    public YeuCauAdapter(Context context, PhucVuPresenter phucVuPresenter) {
        this.context = context;

        this.phucVuPresenter = phucVuPresenter;
        yeuCauList = phucVuPresenter.getDatabase().getYeuCauList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_cau, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YeuCau yeuCau = yeuCauList.get(position);

        if (yeuCau.getType() == YeuCau.HOA_DON_MOI) {
            holder.tvType.setText(Utils.getStringByRes(R.string.hoa_don_moi));
        } else if (yeuCau.getType() == YeuCau.THEM_MON) {
            holder.tvType.setText(Utils.getStringByRes(R.string.yeu_cau_them_mon));
        } else if (yeuCau.getType() == YeuCau.TINH_TIEN_HOA_DON) {
            holder.tvType.setText(Utils.getStringByRes(R.string.yeu_cau_tinh_tien));
        } else {

            holder.tvType.setVisibility(View.GONE);
        }
        holder.tvTenKhachHang.setText(yeuCau.getKhachHang().getHoTen());
        holder.tvThoiGian.setText(yeuCau.getThoiGian());

    }

    @Override
    public int getItemCount() {
        if (yeuCauList != null) {
            return yeuCauList.size();
        }
        return 0;
    }

    public void notifyItemChanged(YeuCau yeuCau) {
        notifyItemChanged(yeuCauList.indexOf(yeuCau));
    }

    public void notifyItemRemoved(YeuCau yeuCau) {
        notifyItemRemoved(yeuCauList.indexOf(yeuCau));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenKhachHang;
        TextView tvThoiGian;
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);

            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvTenKhachHang = (TextView) itemView.findViewById(R.id.tv_ten_khach_hang);
            tvThoiGian = (TextView) itemView.findViewById(R.id.tv_thoi_gian);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    phucVuPresenter.onClickYeuCau(yeuCauList.get(getAdapterPosition()));
                    break;
            }

        }
    }

}
