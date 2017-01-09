<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maChiTietHD'])){
        
            $maChiTietHD = $_GET['maChiTietHD'];
            

            $db = new dbConnect();

            $result = mysql_query('DELETE FROM chitiethd WHERE MaChiTietHD = '.$maChiTietHD.'');
         
        
            if($result){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>