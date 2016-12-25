<?php
    include_once './dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            
            if(isset($_POST['tongTien'])){
                $tongTien = $_POST['tongTien'];
            }
            

            $db = new dbConnect();

            $result = mysql_query('UPDATE hoadon SET TongTien = '.$tongTien.', TrangThai = 1 WHERE MaHoaDon = '.$maHoaDon.'');
         
        
            if($result){
                mysql_query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>