<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTinTuc = $_POST['maTinTuc'];
    $tieuDe = $_POST['tieuDe'];
    $noiDung = $_POST['noiDung'];
    $ngayDang = $_POST['ngayDang'];
    $hinhAnh = $_POST['hinhAnh'];
    $hienThi = $_POST['hienThi'];

    $db = new Database();

    $db->prepare("UPDATE tin_tuc SET TieuDe = :tieuDe, NoiDung = :noiDung, NgayDang = :ngayDang, HinhAnh = :hinhAnh, HienThi = :hienThi 
                            WHERE MaTinTuc = :maTinTuc");
    $db->bind(':tieuDe', $tieuDe);
    $db->bind(':noiDung', $noiDung);
    $db->bind(':ngayDang', $ngayDang);
    $db->bind(':hinhAnh', base64_decode($hinhAnh));
    $db->bind(':hienThi', $hienThi);
    $db->bind(':maTinTuc', $maTinTuc);
    $db->execute();

    if ($db->getRowCount() > 0) {
        echo 'success';

    }

}

dispInfo();
?>