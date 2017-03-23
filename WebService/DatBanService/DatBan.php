<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTokenAdmin = $_POST['maTokenAdmin'];
    $maBan = isset($_POST['maBan']) ? $_POST['maBan'] : null;
    $tenKhachHang = $_POST['tenKhachHang'];
    $soDienThoai = $_POST['soDienThoai'];
    $gioDen = $_POST['gioDen'];
    $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;

    $db = new Database();

    $db->prepare('INSERT INTO `person`(`HoTen`, `SoDienThoai`) VALUES (:hoTen, :soDienThoai)');
    $db->bind(':hoTen', $tenKhachHang);
    $db->bind(':soDienThoai', $soDienThoai);
    $db->execute();

    $maPerson = $db->getLastInsertId();

    $db->prepare('INSERT INTO dat_ban(MaPerson, GioDen, YeuCau, MaBan, TrangThai) VALUES (:maPerson, :gioDen, :yeuCau, :maBan, :trangThai)');
    $db->bind(':maPerson', $maPerson);
    $db->bind(':gioDen', $gioDen);
    $db->bind(':yeuCau', $yeuCau);
    $db->bind(':maBan', $maBan);
    $db->bind(':trangThai', 0);

    $db->execute();

    if ($db->getRowCount() > 0) {

        $maDatBan = $db->getLastInsertId();
        echo $maDatBan;

        if (!empty($maBan)) {
            $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
        }

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maDatBan'] = $maDatBan;
        $datas['tenKhachHang'] = $tenKhachHang;
        $datas['soDienThoai'] = $soDienThoai;
        $datas['gioDen'] = $gioDen;
        $datas['yeuCau'] = $yeuCau;
        $datas['maBan'] = $maBan;

        $push->setDatas("DAT_BAN_ACTION", $datas);

        $firebase->sendMultiple($db->getAllTokenAdminExcept($maTokenAdmin), null, $push->getDatas());

    }

}

dispInfo();
?>