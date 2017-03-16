package thanggun99.quanlynhahang.model;

import android.os.AsyncTask;

/**
 * Created by Thanggun99 on 07/03/2017.
 */

public class MainInteractor {
    private Database database;
    private OnMainInteractorFinishListener onMainInteractorFinishListener;

    public MainInteractor(OnMainInteractorFinishListener onMainInteractorFinishListener, Database database) {
        this.database = database;
        this.onMainInteractorFinishListener = onMainInteractorFinishListener;
    }

    public void getDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onMainInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return database.getDatas();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onMainInteractorFinishListener.onFinishGetDatas();
                } else {
                    onMainInteractorFinishListener.onGetDatasFail();
                }
                onMainInteractorFinishListener.onFinishTask();
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


    public interface OnMainInteractorFinishListener {
        void onFinishGetDatas();

        void onGetDatasFail();

        void onStartTask();

        void onFinishTask();
    }
}

