<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTokenAdmin = $_GET['maTokenAdmin'];
    $maKhachHang = isset($_GET['maKhachHang']) ? $_GET['maKhachHang'] : null;
    $tenKhachHang = $_GET['tenKhachHang'];
    $maDatBan = $_GET['maDatBan'];
    $maBan = isset($_GET['maBan']) ? $_GET['maBan'] : null;

    $db = new Database();

    $db->prepare('SELECT MaPerson FROM dat_ban WHERE MaDatBan = :maDatBan');
    $db->bind(':maDatBan', $maDatBan);
    $maPerson = $db->getRow()['MaPerson'];

    $db->prepare('DELETE FROM dat_ban WHERE MaDatBan = :maDatBan');
    $db->bind(':maDatBan', $maDatBan);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $db->prepare('DELETE FROM person WHERE MaPerson = :maPerson');
        $db->bind(':maPerson', $maPerson);
        $db->execute();

        if (!empty($maBan)) {

            $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
        }

        echo 'success';

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maDatBan'] = $maDatBan;
        $datas['maKhachHang'] = $maKhachHang;
        $datas['tenKhachHang'] = $tenKhachHang;

        $push->setDatas("HUY_DAT_BAN_ACTION", $datas);

        if (!is_null($maKhachHang)) {
            $firebase->send($db->getTokenKhachHangByMaKhachHang($maKhachHang), null, $push->getDatas());
        }

        $firebase->sendMultiple($db->getAllTokenAdminExcept($maTokenAdmin), null, $push->getDatas());
    }

}

dispInfo();
?>