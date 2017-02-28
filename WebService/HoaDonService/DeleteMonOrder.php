<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maChiTietHD'])){
        
            $maChiTietHD = $_GET['maChiTietHD'];
            

            $db = new Database();

            $db->query('DELETE FROM chi_tiet_hd WHERE MaChiTietHD = '.$maChiTietHD.'');
         
        
            if($db->getRowCount() > 0){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>