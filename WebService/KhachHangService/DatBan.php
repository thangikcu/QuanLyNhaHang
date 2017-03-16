<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maKhachHang = $_POST['maKhachHang'];
        $gioDen = $_POST['gioDen'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : NULL;
    
        $db = new Database();

        $db->prepare('INSERT INTO `dat_ban`(`MaKhachHang`, `GioDen`, `YeuCau`, `TrangThai`) VALUES (:maKhachHang, :gioDen, :yeuCau, :trangThai)');
        $db->bind(':maKhachHang', $maKhachHang);
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':trangThai', 0);
        $db->execute();
     
    
        if($db->getRowCount() > 0){
            
            $maDatBan = $db->getLastInsertId();

            echo $maDatBan;
            
            include_once '../Firebase.php';
            $firebase = new Firebase();
            $push = new Push();           

        
            $datas = array();
            $datas['maDatBan'] = $maDatBan;
            $datas['tenKhachHang'] = $tenKhachHang;
            $datas['maKhachHang'] = $maKhachHang;
            $datas['gioDen'] = $gioDen;
            $datas['yeuCau'] = $yeuCau;
            
            $push->setDatas("DAT_BAN_CHUA_SET_BAN_ACTION", $datas);
            

            $firebase ->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
                      
        }
        
    }

    dispInfo();
?>