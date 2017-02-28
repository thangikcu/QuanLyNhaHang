<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];
            $maBan = $_GET['maBan'];
            $maDatTruoc = $_GET['maDatTruoc'];
            

            $db = new Database();

            $db->query('DELETE FROM chi_tiet_hd WHERE MaHoaDon = '.$maHoaDon.'');
         
        
            if($db->getRowCount() > 0){
                $db->query('DELETE FROM hoa_don WHERE MaHoaDon = '.$maHoaDon.'');
                
                $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatTruoc.'');
                
                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');

                echo 'success';

                
            }
            
        }


    }
 
    dispInfo();
?>