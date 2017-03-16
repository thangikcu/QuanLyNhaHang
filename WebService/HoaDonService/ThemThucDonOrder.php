<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maHoaDon = $_POST['maHoaDon'];
        $maMon = $_POST['maMon'];
        $soLuong = $_POST['soLuong'];
    
        $db = new Database();

        $db->prepare('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES (:maHoaDon, :maMon, :soLuong)');
        $db->bind(':maHoaDon', $maHoaDon);
        $db->bind(':maMon', $maMon);
        $db->bind(':soLuong', $soLuong);
        $db->execute();
     
    
        if($db->getRowCount() > 0){
            
            
            echo $db->getLastInsertId();
        }
        
    }

    dispInfo();
?>