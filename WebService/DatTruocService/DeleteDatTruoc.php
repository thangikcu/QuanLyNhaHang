<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maDatTruoc'])){
        
            $maDatTruoc = $_GET['maDatTruoc'];
            $maBan = $_GET['maBan'];
            

            $db = new Database();

            $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatTruoc.'');
         
        
            if($db->getRowCount() > 0){

                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                echo 'success';
             }
            
        }


    }
 
    dispInfo();
?>