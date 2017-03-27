<?php
require_once '../dbConnect.php';

function dispInfo() {
    $tieuDe = $_POST['tieuDe'];
    $noiDung = $_POST['noiDung'];
    $ngayDang = $_POST['ngayDang'];
    $hinhAnh = $_POST['hinhAnh'];
    $hienThi = $_POST['hienThi'];

    $db = new Database();

    $db->prepare('INSERT INTO `tin_tuc`(`TieuDe`, `NoiDung`, `NgayDang`, `HinhAnh`, `HienThi`) VALUES (:tieuDe, :noiDung, :ngayDang, :hinhAnh, :hienThi)');
    $db->bind(':tieuDe', $tieuDe);
    $db->bind(':noiDung', $noiDung);
    $db->bind(':ngayDang', $ngayDang);
    $db->bind(':hinhAnh', base64_decode($hinhAnh));
    $db->bind(':hienThi', $hienThi);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $maTinTuc = $db->getLastInsertId();

        echo $maTinTuc;


    }

}

dispInfo();
?>