<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            $maBan = $_POST['maBan'];
            $tongTien = $_POST['tongTien'];
            
            

            $db = new Database();

            $db->query('UPDATE hoa_don SET TongTien = '.$tongTien.', TrangThai = 1 WHERE MaHoaDon = '.$maHoaDon.'');
         
        
            if($db->getRowCount() > 0){
                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                
                if($db->getRowCount() > 0){
                    echo 'success';
                }
                
            }
            
        }


    }
 
    dispInfo();
?>