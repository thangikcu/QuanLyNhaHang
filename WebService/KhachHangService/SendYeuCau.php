<?php
require_once '../dbConnect.php';

function dispInfo() {

    $yeuCau = $_POST['yeuCau'];
    $tenKhachHang = $_POST['tenKhachHang'];
    $maKhachHang = $_POST['maKhachHang'];
    $thoiGian = $_POST['thoiGian'];

    $db = new Database();

    $db->prepare('SELECT * FROM yeu_cau WHERE MaKhachHang = :maKhachHang');
    $db->bind(':maKhachHang', $maKhachHang);
    $db->execute();

    $isOk = false;

    if ($db->getRowCount() > 0) {
        $db->prepare('UPDATE yeu_cau SET YeuCau = :yeuCau, ThoiGian = :thoiGian WHERE MaKhachHang = :maKhachHang');
        $db->bind(':thoiGian', $thoiGian);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maKhachHang', $maKhachHang);
        $db->execute();

        if ($db->getRowCount() > 0) {

            $isOk = true;

        }
    } else {
        $db->prepare('INSERT INTO `yeu_cau`(`MaKhachHang`, `YeuCau`, `ThoiGian`) VALUES (:maKhachHang, :yeuCau, :thoiGian)');
        $db->bind(':thoiGian', $thoiGian);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maKhachHang', $maKhachHang);
        $db->execute();

        if ($db->getRowCount() > 0) {

            $isOk = true;
        }

    }

    if ($isOk) {
        echo 'success';

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maKhachHang'] = $maKhachHang;
        $datas['tenKhachHang'] = $tenKhachHang;
        $datas['thoiGian'] = $thoiGian;
        $datas['yeuCau'] = $yeuCau;

        $push->setDatas("KHACH_HANG_YEU_CAU_ACTION", $datas);

        $firebase->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
    }

}

dispInfo();
?>