<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maDatTruoc'])){
        
            $maDatTruoc = $_GET['maDatTruoc'];
            
            $db = new Database();

            $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatTruoc.'');
         
        
            if($db->getRowCount() > 0){

                echo 'success';
             }
            
        }


    }
 
    dispInfo();
?>