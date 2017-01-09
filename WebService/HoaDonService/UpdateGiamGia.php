<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];

            if(isset($_POST['giamGia'])){
                $giamGia = $_POST['giamGia'];
            }
 
            $db = new dbConnect();

            $result = mysql_query('UPDATE hoadon SET GiamGia = '.$giamGia.' WHERE MaHoaDon = '.$maHoaDon.' ');
         
        
            if($result){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>