<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maDatBan'])){
        
            $maDatBan = $_GET['maDatBan'];
            $tenKhachHang = $_GET['tenKhachHang'];
            
            $db = new Database();

            $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');
         
        
            if($db->getRowCount() > 0){

                echo 'success';
                
                include_once '../Firebase.php';
                $firebase = new Firebase();
                $push = new Push();              

            
                $datas = array();
                $datas['maDatBan'] = $maDatBan;
                $datas['tenKhachHang'] = $tenKhachHang;

                
                $push->setDatas("HUY_DAT_BAN_CHUA_SET_BAN_ACTION", $datas);
                
    
                $firebase ->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
             }
            
        }


    }
 
    dispInfo();
?>