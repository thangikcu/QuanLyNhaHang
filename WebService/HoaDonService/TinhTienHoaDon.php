<?php
require_once '../dbConnect.php';

function dispInfo() {
    if (isset($_GET['maHoaDon'])) {

        $maHoaDon = $_GET['maHoaDon'];
        $maBan = $_POST['maBan'];
        $tongTien = $_POST['tongTien'];
        $maDatBan = $_POST['maDatBan'];
        $maKhachHang = isset($_POST['maKhachHang']) ? $_POST['maKhachHang'] : null;

        $db = new Database();

        $db->prepare('UPDATE hoa_don SET TongTien = :tongTien, TrangThai = :trangThai WHERE MaHoaDon = :maHoaDon');
        $db->bind(':tongTien', $tongTien);
        $db->bind(':trangThai', 1);
        $db->bind(':maHoaDon', $maHoaDon);
        $db->execute();

        if ($db->getRowCount() > 0) {
            $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
            $db->query('UPDATE dat_ban SET TrangThai = 1 WHERE MaDatBan = '.$maDatBan.' ');

            echo 'success';

            if (!is_null($maKhachHang)) {

                $db->prepare('DELETE FROM `yeu_cau` WHERE MaKhachHang = :maKhachHang');
                $db->bind(':maKhachHang', $maKhachHang);
                $db->execute();

                $db = new Database();
                include_once '../Firebase.php';
                $firebase = new Firebase();
                $push = new Push();

                $datas = array();
                $datas['maKhachHang'] = $maKhachHang;

                $push->setDatas("TINH_TIEN_HOA_DON_ACTION", $datas);

                $firebase->send($db->getTokenKhachHangByMaKhachHang($maKhachHang), null, $push->
                    getDatas());

            }

        }

    }

}

dispInfo();
?>