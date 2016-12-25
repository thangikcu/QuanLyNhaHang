<?php
    include_once './dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maChiTietHD'])){
        
            $maChiTietHD = $_GET['maChiTietHD'];
            
            $qr = '';

            
            if(isset($_POST['soLuong'])){
                $soLuong = $_POST['soLuong'];
                $qr .= "SoLuong = '".$soLuong."'";
                
            }


            
            $db = new dbConnect();

            $result = mysql_query('UPDATE chitiethd SET '.$qr.' WHERE maChiTietHD = '.$maChiTietHD.' ');
         
        
            if($result){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>