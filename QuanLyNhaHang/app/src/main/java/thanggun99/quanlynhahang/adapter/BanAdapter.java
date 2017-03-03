package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.App;
import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.interfaces.OnItemclickListener;
import thanggun99.quanlynhahang.model.entity.Ban;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    private ArrayList<Ban> banList;
    private OnItemclickListener onItemclickListener;
    private TextView tvSelected = new TextView(App.getContext());

    public BanAdapter(ArrayList<Ban> banList) {
        this.banList = banList;
    }

    public void setOnItemclickListener(OnItemclickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
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
            tvBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvBan.isSelected()) return;
                    tvSelected.setSelected(false);
                    tvSelected = tvBan;
                    tvBan.setSelected(true);
                    onItemclickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }
}
