package thanggun99.quanlynhahang.presenter;

import thanggun99.quanlynhahang.interfaces.ShowOnMain;
import thanggun99.quanlynhahang.model.Database;
import thanggun99.quanlynhahang.model.MainInteractor;
import thanggun99.quanlynhahang.util.Utils;

/**
 * Created by Thanggun99 on 07/03/2017.
 */

public class MainPresenter implements MainInteractor.OnMainInteractorFinishListener {
    private MainView mainview;
    private MainInteractor mainInteractor;

    public MainPresenter(MainView mainview, Database database) {

        this.mainview = mainview;
        mainInteractor = new MainInteractor(this, database);
    }

    //check connection
    private boolean checkConnect() {
        if (Utils.isConnectingToInternet()) {
            return true;
        } else {
            mainview.showConnectFailDialog();
            return false;
        }
    }

    public void getDatas() {

        mainInteractor.getDatas();

    }

    @Override
    public void onFinishGetDatas() {
        mainview.showContent();
    }

    @Override
    public void onGetDatasFail() {
        mainview.showGetDatasFailDialog();
    }

    @Override
    public void onStartTask() {
        mainview.showProgress();
    }

    @Override
    public void onFinishTask() {
        mainview.hideProgress();
    }

    public interface MainView extends ShowOnMain {

        void showGetDatasFailDialog();

        void showContent();
    }

}
