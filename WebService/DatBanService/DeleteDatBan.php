<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        
        $maDatBan = $_GET['maDatBan'];
        $maBan = isset($_GET['maBan']) ? $_GET['maBan'] : NULL;
        

        $db = new Database();

        $db->prepare('DELETE FROM dat_ban WHERE MaDatBan = :maDatBan');
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();
     
    
        if($db->getRowCount() > 0){
            
            if(!empty($maBan)){
                
                $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
            }
            
            echo 'success';
         }
        
   


    }
 
    dispInfo();
?>