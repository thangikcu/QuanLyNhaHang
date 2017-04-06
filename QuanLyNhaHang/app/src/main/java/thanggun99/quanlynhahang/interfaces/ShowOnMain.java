package thanggun99.quanlynhahang.interfaces;

/**
 * Created by Thanggun99 on 07/03/2017.
 */

public interface ShowOnMain {
    void showProgress();

    void hideProgress();

    void showNotifyDialog(String message);

    void showPhucVu();

    void showFloatButton();

    void removeFloatButton();

    void updateFloatButton(int size);

    void hideKhachHangYeuCauDialog();
}
