<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maChiTietHD'])){
        
            $maChiTietHD = $_GET['maChiTietHD'];
            
            $qr = '';

            
            if(isset($_POST['soLuong'])){
                $soLuong = $_POST['soLuong'];
                $qr .= "SoLuong = '".$soLuong."'";
                
            }


            
            $db = new Database();

            $db->query('UPDATE chi_tiet_hd SET '.$qr.' WHERE maChiTietHD = '.$maChiTietHD.' ');
         
        
            if($db->getRowCount() > 0){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>