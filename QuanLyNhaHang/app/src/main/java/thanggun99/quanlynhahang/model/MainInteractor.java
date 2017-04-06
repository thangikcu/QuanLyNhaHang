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

    public void changePassword(String password, String passwordNew) {
        class ChangePasswordTask extends AsyncTask<String, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onMainInteractorFinishListener.onStartTask();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onMainInteractorFinishListener.onChangePasswordSucess();
                } else {
                    onMainInteractorFinishListener.onChangePasswordFail();
                }
                onMainInteractorFinishListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... params) {
                delay(500);
                return database.getAdmin().changePassword(params[0]);
            }
        }


        if (!database.getAdmin().getMatKhau().equals(password)) {

            onMainInteractorFinishListener.passwordWrong();
        } else {

            new ChangePasswordTask().execute(passwordNew);
        }
    }

    public void logout() {
        database.getAdmin().huyGhiNhoDangNhap();
        database.setAdmin(null);
    }

    public Database getDatabase() {
        return database;
    }

    public void reloadDatas() {
        LoginTask loginTask = new LoginTask();
        loginTask.setOnLoginListener(new LoginTask.OnLoginListener() {
            @Override
            public void onStartTask() {
                onMainInteractorFinishListener.onStartTask();
            }

            @Override
            public void onFinishTask() {
                onMainInteractorFinishListener.onFinishTask();
            }

            @Override
            public void onLoginSuccess() {
                database.refresh();
                getDatas();
            }

            @Override
            public void onLoginFail() {
                onMainInteractorFinishListener.onLoginFail();
            }

            @Override
            public void onOtherLogin() {
                onMainInteractorFinishListener.onOtherlogin();
            }
        });

        loginTask.login(getDatabase().getAdmin());
    }


    public interface OnMainInteractorFinishListener {
        void onFinishGetDatas();

        void onGetDatasFail();

        void onStartTask();

        void onFinishTask();

        void onChangePasswordSucess();

        void onChangePasswordFail();

        void passwordWrong();

        void onLoginFail();

        void onOtherlogin();
    }
}

