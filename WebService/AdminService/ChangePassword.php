<?php
require_once '../dbConnect.php';

function dispInfo()
{

    $maAdmin = $_POST['maAdmin'];
    $matKhauMoi = $_POST['matKhauMoi'];

    $db = new Database();

    $db->prepare('UPDATE tai_khoan SET MatKhau = :matKhauMoi WHERE MaTaiKhoan = (SELECT MaTaiKhoan FROM admin WHERE MaAdmin = :maAdmin)');
    $db->bind(':matKhauMoi', $matKhauMoi);
    $db->bind(':maAdmin', $maAdmin);
    $db->execute();

    if ($db->getRowCount() > 0)
    {
        echo 'success';
    }

}

dispInfo();
?>