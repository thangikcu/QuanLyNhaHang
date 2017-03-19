<?php
require_once '../dbConnect.php';

function dispInfo() {

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

        echo $db->getLastInsertId();

        if (!empty($maBan)) {
            $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
        }

    }

}

dispInfo();
?>