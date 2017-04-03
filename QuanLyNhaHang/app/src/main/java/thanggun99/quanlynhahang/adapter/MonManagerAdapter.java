package thanggun99.quanlynhahang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.MonManager;
import thanggun99.quanlynhahang.model.entity.Mon;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ConfirmDialog;
import thanggun99.quanlynhahang.view.dialog.ThemMonDialog;

/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class MonManagerAdapter extends RecyclerView.Adapter<MonManagerAdapter.ViewHolder> {

    private ArrayList<Mon> monList;
    private int currentPosition;
    private MonManager monManager;
    private Context context;

    public MonManagerAdapter(Context context, MonManager monManager) {
        this.context = context;
        this.monManager = monManager;
        monList = monManager.getMonList();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mon mon = monList.get(position);

        holder.tvTenMon.setText(mon.getTenMon());
        holder.tvDonGia.setText(Utils.formatMoney(mon.getDonGia()) + "/" + mon.getDonViTinh());
        holder.tvRatingPoint.setText(mon.getRatingPoint());
        holder.ratingBar.setRating(mon.getRating() / mon.getPersonRating());

        Glide.with(context)
                .load(mon.getHinhAnh())
                .placeholder(R.drawable.ic_food)
                .error(R.drawable.ic_food)
                .into(holder.ivHinhAnh);

    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getItemCount() {
        if (monList != null) {
            return monList.size();
        }
        return 0;
    }

    public void notifyItemRemoved(Mon mon) {
        if (mon != null) {

            notifyItemRemoved(monList.indexOf(mon));
        } else {

            notifyItemRemoved(currentPosition);
        }
    }

    public void notifyItemChanged(Mon mon) {
        if (mon != null) {
            notifyItemChanged(monList.indexOf(mon));
        } else {

            notifyItemChanged(currentPosition);
        }
    }


    public void setDatas(ArrayList<Mon> datas) {
        this.monList = datas;
    }

    public void showAllData() {
        this.monList = monManager.getMonList();
        notifyDataSetChanged();
    }

    public void changeData(ArrayList<Mon> monList) {
        this.monList = monList;
        notifyDataSetChanged();
    }

    public void removeMon() {
        notifyItemRemoved(currentPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDonGia;
        TextView tvTenMon;
        ImageView ivHinhAnh;
        TextView tvRatingPoint;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvRatingPoint = (TextView) itemView.findViewById(R.id.tv_point_rating);

            ivHinhAnh = (ImageView) itemView.findViewById(R.id.iv_mon);

            tvDonGia = (TextView) itemView.findViewById(R.id.tv_don_gia);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_ten_mon);

            itemView.findViewById(R.id.btn_update).setOnClickListener(this);
            itemView.findViewById(R.id.btn_delete).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_delete:
                    final ConfirmDialog confirmDialog = new ConfirmDialog(monManager.getFragment().getContext());
                    confirmDialog.setContent(Utils.getStringByRes(R.string.xac_nhan),
                            Utils.getStringByRes(R.string.xac_nhan_xoa_mon) + " " + monList.get(currentPosition).getTenMon() + " ?");

                    confirmDialog.setOnClickOkListener(new ConfirmDialog.OnClickOkListener() {
                        @Override
                        public void onClickOk() {
                            monManager.deleteMon(monList.get(currentPosition));
                            confirmDialog.dismiss();

                        }
                    });

                    break;
                case R.id.btn_update:
                    ThemMonDialog themMonDialog = monManager.getThemMonDialog();
                    monManager.setCurrentMon(monList.get(currentPosition));
                    themMonDialog.clear();
                    themMonDialog.fillContent(monList.get(currentPosition));
                    themMonDialog.show();
                    break;
                default:
                    break;
            }

        }
    }
}
