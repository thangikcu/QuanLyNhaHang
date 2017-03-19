<?php
require_once '../dbConnect.php';

function dispInfo()
{

    $maDatBan = isset($_POST['maDatBan']) ? $_POST['maDatBan'] : null;

    $maBan = $_POST['maBan'];

    $gioDen = $_POST['gioDen'];

    $db = new Database();

    $db->prepare('INSERT INTO hoa_don(MaBan, MaDatBan, GioDen, TrangThai) VALUES (:maBan, :maDatBan, :gioDen, :trangThai)');
    $db->bind(':maBan', $maBan);
    $db->bind(':maDatBan', $maDatBan);
    $db->bind(':gioDen', $gioDen);
    $db->bind(':trangThai', 0);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $maMon = $_POST['maMon'];
        $soLuong = $_POST['soLuong'];

        $maHoaDon = $db->getLastInsertId();

        $db->prepare('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES (:maHoaDon, :maMon, :soLuong)');
        $db->bind(':maHoaDon', $maHoaDon);
        $db->bind(':maMon', $maMon);
        $db->bind(':soLuong', $soLuong);
        $db->execute();

        if ($db->getRowCount() > 0) {

            $response["ma"] = array();
            $t = array();
            $t['maHoaDon'] = $maHoaDon;
            $t['maChiTietHD'] = $db->getLastInsertId();

            $db->query('UPDATE ban SET TrangThai = 2 WHERE MaBan = '.$maBan.' ');
            if (!is_null($maDatBan)) {
                $db->query('UPDATE dat_ban SET TrangThai = 1 WHERE MaDatBan = '.$maDatBan.' ');
            }

            array_push($response["ma"], $t);
            header('Content-Type: application/json');

            echo json_encode($response);
        }
    } else {
        echo "false";
    }

}

dispInfo();
?>