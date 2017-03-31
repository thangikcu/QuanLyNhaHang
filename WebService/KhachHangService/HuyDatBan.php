<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maDatBan = $_GET['maDatBan'];
    $tenKhachHang = $_GET['tenKhachHang'];

    $db = new Database();
    
    $db->prepare('SELECT MaBan FROM dat_ban WHERE MaDatBan = :maDatBan');
    $db->bind(':maDatBan', $maDatBan);
    $maBan = $db->getRow()['MaBan'];

    $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');

    if ($db->getRowCount() > 0) {
        
        if(!is_null($maBan)){
            $db->prepare('UPDATE ban SET TrangThai = 0 WHERE MaBan = :maBan');
            $db->bind(':maBan', $maBan);
            $db->execute();
        }

        echo 'success';

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maDatBan'] = $maDatBan;
        $datas['tenKhachHang'] = $tenKhachHang;

        $push->setDatas("HUY_DAT_BAN_ACTION", $datas);

        $firebase->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());

    }

}

dispInfo();
?>