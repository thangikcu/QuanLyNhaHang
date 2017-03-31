<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maKhachHang = $_POST['maKhachHang'];
    $json = json_decode($_POST['listMon'], true);
    $maHoaDon = $_POST['maHoaDon'];

    $db = new Database();

    foreach ($json['listMonUpdate'] as $mon) {
        $db->prepare('UPDATE chi_tiet_hd SET SoLuong = :soLuong WHERE maChiTietHD = :maChiTietHD ');
        $db->bind(':soLuong', $mon['soLuong']);
        $db->bind(':maChiTietHD', $mon['maChiTietHD']);
        $db->execute();
    }

    $t['maChiTietHD'] = array();
    $listMaChiTietHD = array();

    foreach ($json['listMonNew'] as $mon) {
        $db->prepare('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES (:maHoaDon, :maMon, :soLuong)');
        $db->bind(':maHoaDon', $maHoaDon);
        $db->bind(':maMon', $mon['maMon']);
        $db->bind(':soLuong', $mon['soLuong']);
        $db->execute();

        $listMaChiTietHD['maChiTietHD'] = $db->getLastInsertId();

        array_push($t['maChiTietHD'], $listMaChiTietHD);
    }

    $db->prepare('DELETE FROM `yeu_cau` WHERE MaKhachHang = :maKhachHang');
    $db->bind(':maKhachHang', $maKhachHang);
    $db->execute();

    $db = new Database();
    include_once '../Firebase.php';
    $firebase = new Firebase();
    $push = new Push();

    $datas = array();
    $datas['maKhachHang'] = $maKhachHang;

    $push->setDatas("ORDER_MON_ACTION", $datas);

    $firebase->send($db->getTokenKhachHangByMaKhachHang($maKhachHang), null, $push->getDatas());

    header('Content-Type: application/json');

    echo json_encode($t);

}

dispInfo();
?>