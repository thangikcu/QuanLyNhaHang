<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maMon = $_POST['maMon'];
 
    $tenMon = $_POST['tenMon'];
    $donViTinh = $_POST['donViTinh'];
    $donGia = $_POST['donGia'];
    $maLoai = $_POST['maLoai'];
    $hinhAnh = $_POST['hinhAnh'];
    $hienThi = $_POST['hienThi']; 

    $db = new Database();

    $db->prepare("UPDATE mon SET TenMon = :tenMon, DVT = :donViTinh, DonGia = :donGia, MaLoai = :maLoai, HinhAnh = :hinhAnh, HienThi = :hienThi 
                            WHERE MaMon = :maMon");
    $db->bind(':tenMon', $tenMon);
    $db->bind(':donViTinh', $donViTinh);
    $db->bind(':donGia', $donGia);
    $db->bind(':maLoai', $maLoai);
    $db->bind(':hinhAnh', base64_decode($hinhAnh));
    $db->bind(':hienThi', $hienThi);
    $db->bind(':maMon', $maMon);
    $db->execute();

    if ($db->getRowCount() > 0) {
        echo 'success';

    }

}

dispInfo();
?>