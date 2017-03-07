<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        
        $maChiTietHD = $_GET['maChiTietHD'];
        $soLuong = $_POST['soLuong'];
        
        
        $db = new Database();

        $db->prepare('UPDATE chi_tiet_hd SET SoLuong=:soLuong WHERE maChiTietHD =:maChiTietHD ');
        $db->bind(':soLuong', $soLuong);
        $db->bind(':maChiTietHD', $maChiTietHD);
        $db->execute();
        
        if($db->getRowCount() > 0){
            echo 'success';
        }



    }
 
    dispInfo();
?>