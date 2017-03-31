<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maDatBan = isset($_POST['maDatBan']) ? $_POST['maDatBan'] : null;

    $maBan = $_POST['maBan'];
    $maKhachHang = isset($_POST['maKhachHang']) ? $_POST['maKhachHang'] : null;
    $listMon = isset($_POST['listMon']) ? $_POST['listMon'] : null;

    $gioDen = $_POST['gioDen'];

    $db = new Database();

    $db->prepare('INSERT INTO hoa_don(MaBan, MaDatBan, GioDen, MaKhachHang, TrangThai) VALUES (:maBan, :maDatBan, :gioDen, :maKhachHang, :trangThai)');
    $db->bind(':maBan', $maBan);
    $db->bind(':maDatBan', $maDatBan);
    $db->bind(':gioDen', $gioDen);
    $db->bind(':maKhachHang', $maKhachHang);
    $db->bind(':trangThai', 0);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $maHoaDon = $db->getLastInsertId();

        $response["ma"] = array();
        $listMaChiTietHD = array();

        $t = array();
        $t['maHoaDon'] = $maHoaDon;
        $t['maChiTietHD'] = array();

        if (!is_null($listMon)) {

            $arrayMon = json_decode($listMon, true);

            foreach ($arrayMon['monYeuCauList'] as $monYeuCau) {

                $db->prepare('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES (:maHoaDon, :maMon, :soLuong)');
                $db->bind(':maHoaDon', $maHoaDon);
                $db->bind(':maMon', $monYeuCau['maMon']);
                $db->bind(':soLuong', $monYeuCau['soLuong']);
                $db->execute();

                $listMaChiTietHD['maChiTietHD'] = $db->getLastInsertId();

                array_push($t['maChiTietHD'], $listMaChiTietHD);

            }

            $db->prepare('DELETE FROM `yeu_cau` WHERE MaKhachHang = :maKhachHang');
            $db->bind(':maKhachHang', $maKhachHang);
            $db->execute();

            $db = new Database();
            include_once '../Firebase.php';
            $firebase = new Firebase();
            $push = new Push();

            $datas = array();
            $datas['maKhachHang'] = $maKhachHang;

            $push->setDatas("TAO_HOA_DON_MOI_ACTION", $datas);

            $firebase->send($db->getTokenKhachHangByMaKhachHang($maKhachHang), null, $push->
                getDatas());

        } else {

            $maMon = $_POST['maMon'];
            $soLuong = $_POST['soLuong'];

            $db->prepare('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES (:maHoaDon, :maMon, :soLuong)');
            $db->bind(':maHoaDon', $maHoaDon);
            $db->bind(':maMon', $maMon);
            $db->bind(':soLuong', $soLuong);
            $db->execute();

            $t['maChiTietHD'] = $db->getLastInsertId();
        }

        $db->prepare('UPDATE ban SET TrangThai = :trangThai WHERE MaBan = :maBan');
        $db->bind(':trangThai', 2);
        $db->bind(':maBan', $maBan);
        $db->execute();

        if (!is_null($maDatBan)) {
            $db->prepare('UPDATE dat_ban SET MaBan = :maBan WHERE MaDatBan = :maDatBan');
            $db->bind(':maBan', $maBan);
            $db->bind(':maDatBan', $maDatBan);
            $db->execute();
        }

        array_push($response["ma"], $t);
        header('Content-Type: application/json');

        echo json_encode($response);

    } else {
        echo "false";
    }

}

dispInfo();
?>