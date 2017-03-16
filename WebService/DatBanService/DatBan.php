<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maBan = isset($_POST['maBan']) ? $_POST['maBan'] : NULL;
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : NULL;
 
        $db = new Database();
        $db->prepare('INSERT INTO dat_ban(TenKhachHang, SoDienThoai, GioDen, YeuCau, MaBan, TrangThai) VALUES (:tenKhachHang, :soDienThoai, :gioDen, :yeuCau, :maBan, :trangThai)');
        $db->bind(':tenKhachHang', $tenKhachHang);
        $db->bind(':soDienThoai', $soDienThoai);
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maBan', $maBan);
        $db->bind(':trangThai', 0);
        
        $db->execute();
        
        
        if($db->getRowCount() > 0){
            
            echo $db->getLastInsertId();
            
            if(!empty($maBan)){
                $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
            }
            
         }
        
    }

    dispInfo();
?>