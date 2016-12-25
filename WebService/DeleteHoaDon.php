<?php
    include_once './dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            $maBan = $_GET['maBan'];
            $maDatTruoc = $_GET['maDatTruoc'];
            

            $db = new dbConnect();

            $result = mysql_query('DELETE FROM chitiethd WHERE MaHoaDon = '.$maHoaDon.'');
         
        
            if($result){
                $result2 = mysql_query('DELETE FROM hoadon WHERE MaHoaDon = '.$maHoaDon.'');
                mysql_query('DELETE FROM datban WHERE MaDatBan = '.$maDatTruoc.'');
                if($result2){
                    mysql_query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                    echo 'success';
                }
                
            }
            
        }


    }
 
    dispInfo();
?>