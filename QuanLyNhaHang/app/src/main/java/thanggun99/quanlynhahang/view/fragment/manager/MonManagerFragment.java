package thanggun99.quanlynhahang.view.fragment.manager;


import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import thanggun99.quanlynhahang.R;
import thanggun99.quanlynhahang.adapter.TinTucAdapter;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.TinTucManager;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.view.fragment.BaseFragment;

@SuppressLint("ValidFragment")
public class MonManagerFragment extends BaseFragment {
    private Button btnThemMoi;
    private RecyclerView tinTucrecyclerView;
    private TinTucAdapter tinTucAdapter;
    private Database database;
    private MainPresenter mainPresenter;
    private TinTucManager tinTucManager;


    public MonManagerFragment(MainPresenter mainPresenter) {
        super(R.layout.fragment_mon_manager);
        this.database = mainPresenter.getDatabase();
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void findViews(View view) {
        tinTucrecyclerView = (RecyclerView) view.findViewById(R.id.list_thuc_don);
        btnThemMoi = (Button) view.findViewById(R.id.btn_them_thuc_don);
    }

    @Override
    public void initComponents() {

        tinTucAdapter = new TinTucAdapter(getContext(), database.getTinTucList(), tinTucManager);

    }

    @Override
    public void setEvents() {
        tinTucrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
