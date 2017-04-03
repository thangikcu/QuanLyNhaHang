package thanggun99.quanlynhahang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.TinTucManager;
import thanggun99.quanlynhahang.model.entity.TinTuc;
import thanggun99.quanlynhahang.util.Utils;
import thanggun99.quanlynhahang.view.dialog.ConfirmDialog;
import thanggun99.quanlynhahang.view.dialog.ThemTinTucDialog;

/**
 * Created by Thanggun99 on 11/03/2017.
 */

public class TinTucAdapter extends RecyclerView.Adapter<TinTucAdapter.ViewHolder> {


    private ArrayList<TinTuc> tinTucList;
    private int currentPosition;
    private TinTucManager tinTucManager;
    private Context context;

    public TinTucAdapter(Context context, TinTucManager tinTucManager) {
        this.context = context;
        this.tinTucManager = tinTucManager;

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

        Glide.with(context)
                .load(tinTuc.getHinhAnh())
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(holder.ivHinhAnh);

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

    public void notifyItemChanged(TinTuc tinTuc) {
        if (tinTuc != null) {
            notifyItemChanged(tinTucList.indexOf(tinTuc));
        } else {

            notifyItemChanged(currentPosition);
        }
    }

    public void setDatas(ArrayList<TinTuc> datas) {
        this.tinTucList = datas;
    }

    public void showAllData() {
        this.tinTucList = tinTucManager.getTinTucList();
        notifyDataSetChanged();
    }

    public void changeData(ArrayList<TinTuc> tinTucList) {
        this.tinTucList = tinTucList;
        notifyDataSetChanged();
    }

    public void removeTinTuc() {
        notifyItemRemoved(currentPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvNgayDang;
        ImageView ivHinhAnh;

        public ViewHolder(View itemView) {
            super(itemView);

            ivHinhAnh = (ImageView) itemView.findViewById(R.id.iv_tin_tuc);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTitle.setMovementMethod(new ScrollingMovementMethod());
            tvNgayDang = (TextView) itemView.findViewById(R.id.tv_ngay_dang);

            itemView.findViewById(R.id.btn_update).setOnClickListener(this);
            itemView.findViewById(R.id.btn_delete).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();
            switch (v.getId()) {
                case R.id.btn_delete:
                    final ConfirmDialog confirmDialog = new ConfirmDialog(tinTucManager.getFragment().getContext());
                    confirmDialog.setContent(Utils.getStringByRes(R.string.xac_nhan),
                            Utils.getStringByRes(R.string.xac_nhan_xoa_tin_tuc));

                    confirmDialog.setOnClickOkListener(new ConfirmDialog.OnClickOkListener() {
                        @Override
                        public void onClickOk() {
                            tinTucManager.deleteTinTuc(tinTucList.get(currentPosition));
                            confirmDialog.dismiss();

                        }
                    });
                    break;
                case R.id.btn_update:
                    ThemTinTucDialog themTinTucDialog = tinTucManager.getThemTinTucDialog();
                    tinTucManager.setCurrentTinTuc(tinTucList.get(currentPosition));
                    themTinTucDialog.clear();
                    themTinTucDialog.fillContent(tinTucList.get(currentPosition));
                    themTinTucDialog.show();
                    break;
                default:
                    break;
            }

        }
    }
}
