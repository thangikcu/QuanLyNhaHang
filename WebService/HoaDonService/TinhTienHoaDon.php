<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            $maBan = $_POST['maBan'];
            $tongTien = $_POST['tongTien'];
            $maDatBan = $_POST['maDatBan'];
            
            

            $db = new Database();

            $db->prepare('UPDATE hoa_don SET TongTien = :tongTien, TrangThai = :trangThai WHERE MaHoaDon = :maHoaDon');
            $db->bind(':tongTien', $tongTien);
            $db->bind(':trangThai', 1);
            $db->bind(':maHoaDon', $maHoaDon);
            $db->execute();
         
        
            if($db->getRowCount() > 0){
                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                $db->query('UPDATE dat_ban SET TrangThai = 2 WHERE MaDatBan = '.$maDatBan.' ');
                
                
                echo 'success';
                
             }
            
        }


    }
 
    dispInfo();
?>