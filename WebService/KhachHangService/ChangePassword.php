<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        
        $maKhachHang = $_POST['maKhachHang'];
        $matKhauMoi = $_POST['matKhauMoi'];

        $db = new Database();

        $db->prepare('UPDATE khach_hang SET MatKhau = :matKhauMoi WHERE MaKhachHang = :maKhachHang');
        $db->bind('matKhauMoi', $matKhauMoi);
        $db->bind('maKhachHang', $maKhachHang);
        $db->execute();
     
    
        if($db->getRowCount() > 0){
            echo 'success';
        }

    }
 
    dispInfo();
?>