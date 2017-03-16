package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.ThucDonOrder;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;
import thanggun99.quanlynhahang.util.Utils;


/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class ThucDonOrderAdapter extends RecyclerView.Adapter<ThucDonOrderAdapter.ViewHolder> {
    private ArrayList<ThucDonOrder> thucDonOrderList;
    private PhucVuPresenter phucVuPresenter;
    private int currentPosition;
    private Animation animationBounce;

    public ThucDonOrderAdapter(PhucVuPresenter phucVuPresenter) {
        this.phucVuPresenter = phucVuPresenter;
        animationBounce = AnimationUtils.loadAnimation(App.getContext(), R.anim.bounce);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thuc_don_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ThucDonOrder thucDonOrder = thucDonOrderList.get(position);

        holder.tvTenMon.setText(thucDonOrder.getTenMon());
        holder.tvSoLuong.setText(thucDonOrder.getSoLuong() + thucDonOrder.getDonViTinh());
        holder.tvThanhTien.setText(Utils.formatMoney(thucDonOrder.getTongTien()));
        holder.ivThucDon.setImageBitmap(thucDonOrder.getHinhAnh());

        holder.itemView.startAnimation(animationBounce);

    }

    @Override
    public int getItemCount() {
        if (thucDonOrderList == null) return 0;
        return thucDonOrderList.size();
    }

    public void changeData(ArrayList<ThucDonOrder> data) {
        thucDonOrderList = data;
        notifyDataSetChanged();
    }

    public ThucDonOrder getItem(int position) {
        return thucDonOrderList.get(position);
    }

    public void updateThucDonOrder(ThucDonOrder currentThucDonOrder){
        notifyItemChanged(thucDonOrderList.indexOf(currentThucDonOrder));
    }

    public void deleteThucDonOrder(){
       notifyItemRemoved(currentPosition);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getSize() {
        if (thucDonOrderList == null) return 0;
        return thucDonOrderList.size();
    }

    public int getPositionOf(ThucDonOrder thucDonOrder) {
        return thucDonOrderList.indexOf(thucDonOrder);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenMon;
        TextView tvThanhTien;
        TextView tvSoLuong;
        ImageView ivThucDon;
        ImageButton btnDelete;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvThanhTien = (TextView) itemView.findViewById(R.id.tv_don_gia);
            tvSoLuong = (TextView) itemView.findViewById(R.id.tv_so_luong);
            ivThucDon = (ImageView) itemView.findViewById(R.id.iv_thuc_don);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete_mon_order);
            tvTenMon = (TextView) itemView.findViewById(R.id.tv_message);

            tvTenMon.setMovementMethod(new ScrollingMovementMethod());
            tvTenMon.setOnClickListener(this);

            btnDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            currentPosition = getAdapterPosition();

            switch (v.getId()) {
                case R.id.btn_delete_mon_order:
                    phucVuPresenter.onClickDeleteMonOrder(getItem(currentPosition));
                    break;
                default:
                    phucVuPresenter.onClickThucdonOrder(getItem(currentPosition));
                    break;
            }
        }
    }
}
