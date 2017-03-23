package thanggun99.quanlynhahang.adapter;

import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.model.entity.NhomMon;
import thanggun99.quanlynhahang.presenter.PhucVuPresenter;

/**
 * Created by Thanggun99 on 19/11/2016.
 */

public class NhomMonAdapter extends BaseAdapter {
    private ArrayList<NhomMon> nhomMonList;
    private PhucVuPresenter phucVuPresenter;

    public NhomMonAdapter(PhucVuPresenter phucVuPresenter) {
        this.phucVuPresenter = phucVuPresenter;
        this.nhomMonList = phucVuPresenter.getDatabase().getNhomMonList();
    }

    @Override
    public int getCount() {
        if (nhomMonList == null) return 0;
        return nhomMonList.size();
    }

    @Override
    public NhomMon getItem(int position) {
        return nhomMonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhom_mon, parent, false);

            viewHolder.tvTenNhom = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTenNhom.setMovementMethod(new ScrollingMovementMethod());
        viewHolder.tvTenNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phucVuPresenter.onClickNhomMon(nhomMonList.get(position));
            }
        });
        viewHolder.tvTenNhom.setText(nhomMonList.get(position).getTenLoai());
        viewHolder.tvTenNhom.setBackgroundColor(nhomMonList.get(position).getMauSac());
        return convertView;
    }

    class ViewHolder {
        TextView tvTenNhom;
    }

}
