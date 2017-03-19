<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maDatBan = $_POST['maDatBan'];
    $tenKhachHang = $_POST['tenKhachHang'];
    $gioDen = $_POST['gioDen'];
    $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;

    $db = new Database();

    $db->prepare('UPDATE dat_ban SET GioDen=:gioDen, YeuCau=:yeuCau WHERE MaDatBan =:maDatBan');
    $db->bind(':gioDen', $gioDen);
    $db->bind(':yeuCau', $yeuCau);
    $db->bind(':maDatBan', $maDatBan);
    $db->execute();

    if ($db->getRowCount() > 0) {
        echo 'success';

        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();

        $datas = array();
        $datas['maDatBan'] = $maDatBan;
        $datas['tenKhachHang'] = $tenKhachHang;
        $datas['gioDen'] = $gioDen;
        $datas['yeuCau'] = $yeuCau;

        $push->setDatas("UPDATE_DAT_BAN_ACTION", $datas);

        $firebase->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());

    }

}

dispInfo();
?>