package thanggun99.quanlynhahang.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.NhomMon;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class NhomMonAdapter extends BaseAdapter {
    private ArrayList<NhomMon> nhomMons;

    public NhomMonAdapter(ArrayList<NhomMon> nhomMons) {
        this.nhomMons = nhomMons;
    }

    @Override
    public int getCount() {
        if (nhomMons == null) return 0;
        return nhomMons.size();
    }

    @Override
    public NhomMon getItem(int position) {
        return nhomMons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhom_mon, parent, false);
            viewHolder.tvTenNhom = (TextView) convertView.findViewById(R.id.tv_ten_loai);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTenNhom.setText(nhomMons.get(position).getTenLoai());
        viewHolder.tvTenNhom.setBackgroundColor(nhomMons.get(position).getMauSac());

        return convertView;
    }

    class ViewHolder {
        TextView tvTenNhom;
    }
}
