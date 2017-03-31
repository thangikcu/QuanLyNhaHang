package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;

import java.util.ArrayList;

import thanggun99.quanlynhahang.model.entity.TinTuc;
import thanggun99.quanlynhahang.presenter.MainPresenter;
import thanggun99.quanlynhahang.view.dialog.ThemTinTucDialog;
import thanggun99.quanlynhahang.view.fragment.manager.TinTucManagerFragment;

/**
 * Created by Thanggun99 on 23/03/2017.
 */

public class TinTucManager {

    private Database database;
    private MainPresenter mainPresenter;
    private TinTucManagerView tinTucManagerView;
    private TinTucManagerFragment fragment;
    private TinTuc currentTinTuc;
    private ThemTinTucDialog themTinTucDialog;

    public TinTucManager(MainPresenter mainPresenter) {

        this.database = mainPresenter.getDatabase();
        this.mainPresenter = mainPresenter;
    }

    public void getDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                mainPresenter.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return database.loadListTinTuc();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    tinTucManagerView.onFinishGetDatas();
                } else {
                    tinTucManagerView.onGetDatasFail();
                }
                mainPresenter.onFinishTask();
            }
        }
        if (database.getTinTucList() == null) {

            if (mainPresenter.checkConnect()) {

                new GetDatasTask().execute();
            }
        }
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTinTucManagerView(TinTucManagerView tinTucManagerView) {
        this.tinTucManagerView = tinTucManagerView;
    }

    public ArrayList<TinTuc> findDatBan(String keyWord) {
        return database.getListTinTucByTieuDe(keyWord);
    }

    public void setFragment(TinTucManagerFragment fragment) {
        this.fragment = fragment;
    }

    public TinTucManagerFragment getFragment() {
        return fragment;
    }

    public void addTinTuc(final TinTuc tinTuc) {
        class AddTinTucTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                mainPresenter.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                if (database.getTinTucList() != null) {

                    return tinTuc.addNew();
                } else {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.addTinTuc(tinTuc);
                    tinTucManagerView.onFinishAddTinTucSuccess();
                } else {
                    tinTucManagerView.onAddTinTucFail();
                }
                mainPresenter.onFinishTask();
            }
        }
        if (mainPresenter.checkConnect()) {

            new AddTinTucTask().execute();
        }
    }

    public void deleteTinTuc(final TinTuc tinTuc) {

        class DeleteTinTucTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected void onPreExecute() {
                mainPresenter.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return tinTuc.delete();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    database.deleteTinTuc(tinTuc);
                    tinTucManagerView.onFinishDeleteTinTucSuccess();
                } else {
                    tinTucManagerView.onDeleteTinTucFail();
                }
                mainPresenter.onFinishTask();
            }
        }
        if (mainPresenter.checkConnect()) {

            new DeleteTinTucTask().execute();
        }

    }

    public void updateTinTuc(final TinTuc tinTuc) {

        class UpdateTinTucTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                mainPresenter.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return currentTinTuc.updateTinTuc(tinTuc);

            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    tinTucManagerView.onFinishUpdateTinTuc();
                } else {
                    tinTucManagerView.onUpdateTinTucFail();
                }
                mainPresenter.onFinishTask();
            }
        }
        if (mainPresenter.checkConnect()) {

            new UpdateTinTucTask().execute();
        }
    }

    public void setCurrentTinTuc(TinTuc currentTinTuc) {
        this.currentTinTuc = currentTinTuc;
    }

    public TinTuc getCurrentTinTuc() {
        return currentTinTuc;
    }

    public ThemTinTucDialog getThemTinTucDialog() {
        return themTinTucDialog;
    }


    public void setThemTinTucDialog(ThemTinTucDialog themTinTucDialog) {
        this.themTinTucDialog = themTinTucDialog;
    }

    public ArrayList<TinTuc> getTinTucList() {
        return database.getTinTucList();
    }

    public interface TinTucManagerView {

        void onFinishGetDatas();

        void onGetDatasFail();

        void onFinishAddTinTucSuccess();

        void onAddTinTucFail();

        void onDeleteTinTucFail();

        void onFinishDeleteTinTucSuccess();

        void onFinishUpdateTinTuc();

        void onUpdateTinTucFail();
    }

}
