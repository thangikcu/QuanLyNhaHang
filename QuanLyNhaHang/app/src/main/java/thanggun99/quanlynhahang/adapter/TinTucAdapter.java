package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.DatBan;
import thanggun99.quanlynhahang.model.entity.TinTuc;

/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class TinTucAdapter extends RecyclerView.Adapter<TinTucAdapter.ViewHolder> {


    private ArrayList<TinTuc> tinTucList;
    private int currentPosition;

    public TinTucAdapter() {

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
        holder.ivHinhAnh.setImageBitmap(tinTuc.getHinhAnh());

    }

    public int getCurrentPosition() {
        return currentPosition;
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
        } else {

            notifyItemRemoved(currentPosition);
        }
    }

    public void notifyItemChanged(DatBan datBan) {
        if (datBan != null) {
            notifyItemChanged(tinTucList.indexOf(datBan));
        } else {

            notifyItemChanged(currentPosition);
        }
    }

    public int getPositonOf(DatBan datBan) {
        return tinTucList.indexOf(datBan);
    }

    public void setDatas(ArrayList<TinTuc> datas) {
        this.tinTucList = datas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvNgayDang;
        ImageButton btnDelete;
        ImageButton btnUpdate;
        ImageView ivHinhAnh;

        public ViewHolder(View itemView) {
            super(itemView);

            ivHinhAnh = (ImageView) itemView.findViewById(R.id.iv_tin_tuc);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTitle.setMovementMethod(new ScrollingMovementMethod());
            tvNgayDang = (TextView) itemView.findViewById(R.id.tv_ngay_dang);

            tvTitle.setOnClickListener(this);
            itemView.setOnClickListener(this);

            itemView.findViewById(R.id.btn_update_tin_tuc).setOnClickListener(this);
            itemView.findViewById(R.id.btn_delete_tin_tuc).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_delete_tin_tuc:

                    break;
                case R.id.btn_update_tin_tuc:

                    break;
                default:
                    break;
            }

        }
    }
}
