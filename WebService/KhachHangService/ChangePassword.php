<?php
require_once '../dbConnect.php';

function dispInfo()
{

    $maKhachHang = $_POST['maKhachHang'];
    $matKhauMoi = $_POST['matKhauMoi'];

    $db = new Database();

    $db->prepare('UPDATE tai_khoan SET MatKhau = :matKhauMoi WHERE MaTaiKhoan = (SELECT MaTaiKhoan FROM khach_hang WHERE MaKhachHang = :maKhachHang)');
    $db->bind(':matKhauMoi', $matKhauMoi);
    $db->bind(':maKhachHang', $maKhachHang);
    $db->execute();

    if ($db->getRowCount() > 0)
    {
        echo 'success';
    }

}

dispInfo();
?>