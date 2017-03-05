<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        
        $maDatBan = $_POST['maDatBan'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : NULL;
 
        $db = new Database();

        $db->prepare('UPDATE dat_ban SET GioDen=:gioDen, YeuCau=:yeuCau WHERE MaDatBan =:maDatBan');
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();
    
        if($db->getRowCount() > 0){
            echo 'success';
            
        }
        
    }

    dispInfo();
?>