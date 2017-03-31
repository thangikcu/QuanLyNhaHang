<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maHoaDon = $_GET['maHoaDon'];

    $giamGia = $_POST['giamGia'];
    $maKhachHang = isset($_POST['maKhachHang']) ? $_POST['maKhachHang'] : null;

    $db = new Database();

    $db->prepare('UPDATE hoa_don SET GiamGia = :giamGia WHERE MaHoaDon = :maHoaDon ');
    $db->bind(':giamGia', $giamGia);
    $db->bind(':maHoaDon', $maHoaDon);
    $db->execute();

    if ($db->getRowCount() > 0) {
        echo 'success';

        if (!is_null($maKhachHang)) {

            $db = new Database();
            include_once '../Firebase.php';
            $firebase = new Firebase();
            $push = new Push();

            $datas = array();
            $datas['maKhachHang'] = $maKhachHang;
            $datas['giamGia'] = $giamGia;

            $push->setDatas("GIAM_GIA_HOA_DON_ACTION", $datas);

            $firebase->send($db->getTokenKhachHangByMaKhachHang($maKhachHang), null, $push->
                getDatas());
        }
    }

}

dispInfo();
?>