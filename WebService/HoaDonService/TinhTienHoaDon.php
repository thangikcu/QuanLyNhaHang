<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            $maBan = $_POST['maBan'];
            $tongTien = $_POST['tongTien'];
            
            

            $db = new dbConnect();

            $result = mysql_query('UPDATE hoadon SET TongTien = '.$tongTien.', TrangThai = 1 WHERE MaHoaDon = '.$maHoaDon.'');
         
        
            if($result){
                $result2 = mysql_query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                
                if($result2){
                    echo 'success';
                }
                
            }
            
        }


    }
 
    dispInfo();
?>