<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maDatBan'])){
        
            $maDatBan = $_GET['maDatBan'];
            $maBan = $_GET['maBan'];
            

            $db = new Database();

            $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');
         
        
            if($db->getRowCount() > 0){

                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                echo 'success';
             }
            
        }


    }
 
    dispInfo();
?>