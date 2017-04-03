package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;

/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class DatBanChuaSetBanAdapter extends RecyclerView.Adapter<DatBanChuaSetBanAdapter.ViewHolder> {

    private ArrayList<DatBan> datBanChuaSetBanList;
    private PhucVuPresenter phucVuPresenter;
    private int currentPosition;

    public DatBanChuaSetBanAdapter(PhucVuPresenter phucVuPresenter) {
        this.phucVuPresenter = phucVuPresenter;
        this.datBanChuaSetBanList = phucVuPresenter.getDatabase().getDatBanChuaSetBanList();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dat_ban_chua_set_ban, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DatBan datBan = datBanChuaSetBanList.get(position);


        holder.tvTenKhachHang.setText(datBan.getTenKhachHang());

        holder.tvGioDen.setText(datBan.getGioDen());
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getItemCount() {
        if (datBanChuaSetBanList != null) {
            return datBanChuaSetBanList.size();
        }
        return 0;
    }

    public void changeData(ArrayList<DatBan> datBanList) {
        this.datBanChuaSetBanList = datBanList;
        notifyDataSetChanged();
    }

    public void showAllData() {
        this.datBanChuaSetBanList = phucVuPresenter.getDatabase().getDatBanChuaSetBanList();
        notifyDataSetChanged();
    }

    public void notifyItemRemoved(DatBan datBan) {
        if (datBan != null) {

            notifyItemRemoved(datBanChuaSetBanList.indexOf(datBan));
        } else {

            notifyItemRemoved(currentPosition);
        }
    }

    public void notifyItemChanged(DatBan datBan) {
        if (datBan != null) {
            notifyItemChanged(datBanChuaSetBanList.indexOf(datBan));
        } else {

            notifyItemChanged(currentPosition);
        }
    }

    public int getPositonOf(DatBan datBan) {
        return datBanChuaSetBanList.indexOf(datBan);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenKhachHang;
        TextView tvGioDen;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTenKhachHang = (TextView) itemView.findViewById(R.id.tv_ten_khach_hang);

            tvGioDen = (TextView) itemView.findViewById(R.id.tv_gio_den);

            itemView.findViewById(R.id.btn_update_dat_ban).setOnClickListener(this);
            itemView.findViewById(R.id.btn_delete_dat_ban).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_delete_dat_ban:
                    phucVuPresenter.onClickDeleteDatBanChuaSetBan(datBanChuaSetBanList.get(currentPosition));
                    break;
                case R.id.btn_update_dat_ban:
                    phucVuPresenter.onClickUpdateDatBanChuaSetBan(datBanChuaSetBanList.get(currentPosition));
                    break;
                default:
                    phucVuPresenter.onClickDatBanChuaSetBanItem(datBanChuaSetBanList.get(currentPosition));
                    break;
            }
        }
    }
}
