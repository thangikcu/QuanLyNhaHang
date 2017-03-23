<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTokenAdmin = $_POST['maTokenAdmin'];
    $maDatBan = $_GET['maDatBan'];
    $tenKhachHang = $_POST['tenKhachHang'];
    $soDienThoai = $_POST['soDienThoai'];
    $gioDen = $_POST['gioDen'];
    $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;

    $db = new Database();
    include_once '../Firebase.php';
    $firebase = new Firebase();
    $push = new Push();

    $datas = array();
    $datas['maDatBan'] = $maDatBan;
    $datas['tenKhachHang'] = $tenKhachHang;
    $datas['soDienThoai'] = $soDienThoai;
    $datas['gioDen'] = $gioDen;
    $datas['yeuCau'] = $yeuCau;
    $datas['maKhachHang'] = isset($_POST['maKhachHang']) ? $_POST['maKhachHang'] : null;

    $push->setDatas("UPDATE_DAT_BAN_ACTION", $datas);

    if (isset($_POST['maKhachHang'])) {
        $rowCount;
        $maKhachHang = $_POST['maKhachHang'];
        $maTokenKH = $_POST['maTokenKH'];
        

        $db->prepare("UPDATE person SET HoTen = :hoTen, SoDienThoai = :soDienThoai 
                            WHERE MaPerson = (SELECT MaPerson FROM khach_hang WHERE MaKhachHang = :maKhachHang)");
        $db->bind(':hoTen', $tenKhachHang);
        $db->bind(':soDienThoai', $soDienThoai);
        $db->bind(':maKhachHang', $maKhachHang);
        $db->execute();

        $rowCount = $db->getRowCount();

        $db->prepare('UPDATE dat_ban SET GioDen = :gioDen, YeuCau = :yeuCau WHERE MaDatBan = :maDatBan');
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();

        $rowCount += $db->getRowCount();

        if ($rowCount > 0) {
            echo 'success';

            $firebase->send($db->getTokenByMa($maTokenKH), null, $push->getDatas());
            $firebase->sendMultiple($db->getAllTokenAdminExcept($maTokenAdmin), null, $push->
                getDatas());

        }

    } else {
        $rowCount;
        $db->prepare("UPDATE person SET HoTen = :hoTen, SoDienThoai = :soDienThoai 
                            WHERE MaPerson = (SELECT MaPerson FROM dat_ban WHERE MaDatBan = :maDatBan)");
        $db->bind(':hoTen', $tenKhachHang);
        $db->bind(':soDienThoai', $soDienThoai);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();

        $rowCount = $db->getRowCount();

        $db->prepare('UPDATE dat_ban SET GioDen = :gioDen, YeuCau = :yeuCau WHERE MaDatBan = :maDatBan');
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':maDatBan', $maDatBan);
        $db->execute();

        $rowCount += $db->getRowCount();

        if ($rowCount > 0) {
            echo 'success';
            $firebase->sendMultiple($db->getAllTokenAdminExcept($maTokenAdmin), null, $push->
                getDatas());

        }

    }

}

dispInfo();
?>