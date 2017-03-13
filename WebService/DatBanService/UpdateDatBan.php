<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatBan = $_GET['maDatBan'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;
 
        $db = new Database();

        $db->prepare('UPDATE dat_ban SET TenKhachHang = :tenKhachHang, SoDienThoai = :soDienThoai, GioDen = :gioDen, YeuCau = :yeuCau WHERE MaDatBan = :maDatBan');
        $db->bind(':tenKhachHang', $tenKhachHang);
        $db->bind(':soDienThoai', $soDienThoai);
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();
        
    
        if($db->getRowCount() > 0){
            echo 'success';
            
        }
        
    }

    dispInfo();
?>