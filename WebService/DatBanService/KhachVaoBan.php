<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatBan = $_POST['maDatBan'];
        $maBan = $_POST['maBan'];

 
        $db = new Database();

        $db->prepare('UPDATE dat_ban SET MaBan = :maBan WHERE MaDatBan = :maDatBan');
        $db->bind(':maBan', $maBan);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();
        
    
        if($db->getRowCount() > 0){
            $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
            echo 'success';
            
        }
        
    }

    dispInfo();
?>