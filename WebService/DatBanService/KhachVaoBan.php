<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTokenAdmin = $_POST['maTokenAdmin'];
    $tenBan = $_POST['tenBan'];
    $maDatBan = $_POST['maDatBan'];
    $maBan = $_POST['maBan'];

    $db = new Database();

    $db->prepare('UPDATE dat_ban SET MaBan = :maBan WHERE MaDatBan = :maDatBan');
    $db->bind(':maBan', $maBan);
    $db->bind(':maDatBan', $maDatBan);
    $db->execute();

    if ($db->getRowCount() > 0) {
        $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
        echo 'success';

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maBan'] = $maBan;
        $datas['tenBan'] = $tenBan;
        $datas['maDatBan'] = $maDatBan;

        $push->setDatas("KHACH_VAO_BAN_ACTION", $datas);

        $firebase->sendMultiple($db->getAllTokenAdminExcept($maTokenAdmin), null, $push->getDatas());
    }

}

dispInfo();
?>