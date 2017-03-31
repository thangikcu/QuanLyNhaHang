<?php
require_once '../dbConnect.php';

function dispInfo() {

    $tenMon = $_POST['tenMon'];
    $donViTinh = $_POST['donViTinh'];
    $donGia = $_POST['donGia'];
    $maLoai = $_POST['maLoai'];
    $hinhAnh = $_POST['hinhAnh']; 
    $hienThi = $_POST['hienThi'];

    $db = new Database();

    $db->prepare('INSERT INTO `mon`(`TenMon`, `MaLoai`, `DonGia`, `DVT`, `HinhAnh`, `HienThi`) 
                            VALUES (:tenMon, :maLoai, :donGia, :donViTinh, :hinhAnh, :hienThi)');
    $db->bind(':tenMon', $tenMon);
    $db->bind(':maLoai', $maLoai);
    $db->bind(':donGia', $donGia);
    $db->bind(':donViTinh', $donViTinh);
    $db->bind(':hinhAnh', base64_decode($hinhAnh));
    $db->bind(':hienThi', $hienThi);
    $db->execute();

    if ($db->getRowCount() > 0) {

        echo $db->getLastInsertId();

    }

}

dispInfo();
?>