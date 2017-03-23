package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;

/**
 * Created by Thanggun99 on 23/03/2017.
 */

public class TinTucManager {

    private Database database;
    private TinTucView tinTucView;

    public TinTucManager(Database database) {

        this.database = database;
    }

    public void getDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                tinTucView.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay(500);
                return database.loadListTinTuc();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    tinTucView.onFinishGetDatas();
                } else {
                    tinTucView.onGetDatasFail();
                }
                tinTucView.onFinishTask();
            }
        }
        new GetDatasTask().execute();
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTinTucView(TinTucView tinTucView) {
        this.tinTucView = tinTucView;
    }



    public interface TinTucView {

        void onStartTask();

        void onFinishGetDatas();

        void onGetDatasFail();

        void onFinishTask();
    }

}
