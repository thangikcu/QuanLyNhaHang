package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    private ArrayList<Ban> banList;
    private TextView tvSelected = new TextView(App.getContext());
    private PhucVuPresenter phucVuPresenter;

    public BanAdapter(ArrayList<Ban> banList, PhucVuPresenter phucVuPresenter) {
        this.banList = banList;
        this.phucVuPresenter = phucVuPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ban ban = banList.get(position);

        holder.tvBan.setText(ban.getTenBan());
        holder.tvBan.setBackgroundResource(ban.getIdResBgBan());

        if (position == 0) {
            holder.tvBan.setSelected(true);
            tvSelected = holder.tvBan;
        }

        if (tvSelected.getText().toString().equals(ban.getTenBan())) {
            holder.tvBan.setSelected(true);
            tvSelected = holder.tvBan;
        }
    }

    public void updateBan(Ban ban) {
        notifyItemChanged(banList.indexOf(ban));
    }

    @Override
    public int getItemCount() {
        if (banList.isEmpty()) return 0;
        return banList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBan;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvBan = (TextView) itemView.findViewById(R.id.tv_ten_ban);
            tvBan.setMovementMethod(new ScrollingMovementMethod());
            tvBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvBan.isSelected()) return;
                    tvSelected.setSelected(false);
                    tvSelected = tvBan;
                    tvBan.setSelected(true);

                    phucVuPresenter.getThongTinbanAtPosition(getAdapterPosition());
                }
            });
        }
    }
}
