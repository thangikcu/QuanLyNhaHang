package thanggun99.quanlynhahang.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.Ban;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    private ArrayList<Ban> banList;
    private Ban banSelected;
    private PhucVuPresenter phucVuPresenter;

    public BanAdapter(PhucVuPresenter phucVuPresenter) {
        this.banList = phucVuPresenter.getDatabase().getBanList();
        this.phucVuPresenter = phucVuPresenter;
        if (banList != null && banList.size() > 0) {

            banSelected = banList.get(0);
        }
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

        if (ban.getSelected()) {
            holder.tvBan.setSelected(true);
        } else {
            holder.tvBan.setSelected(false);
        }

    }

    public void updateBan(Ban ban) {
        notifyItemChanged(banList.indexOf(ban));
    }

    @Override
    public int getItemCount() {
        if (banList == null) return 0;
        return banList.size();
    }

    public void changeSelectBan(Ban ban) {
        banSelected.setSelected(false);
        notifyItemChanged(banList.indexOf(banSelected));

        ban.setSelected(true);
        notifyItemChanged(banList.indexOf(ban));

        banSelected = ban;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBan;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvBan = (TextView) itemView.findViewById(R.id.tv_title);
            tvBan.setMovementMethod(new ScrollingMovementMethod());
            tvBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ban ban = banList.get(getAdapterPosition());
                    if (ban.getSelected()) {

                        return;
                    }

                    banSelected.setSelected(false);
                    notifyItemChanged(banList.indexOf(banSelected));

                    ban.setSelected(true);
                    notifyItemChanged(getAdapterPosition());

                    banSelected = ban;

                    phucVuPresenter.onClickBan(banList.get(getAdapterPosition()));
                }
            });
        }
    }
}
