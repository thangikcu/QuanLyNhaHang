<?php
require_once '../dbConnect.php';

function dispInfo()
{

    $db = new Database();

    $response["hoaDon"] = array();

    $db->prepare('Select * From hoa_don Where TrangThai = 0 ORDER BY MaHoaDon DESC');

    foreach ($db->getArray() as $row) {
        $t = array();
        $t["maHoaDon"] = $row["MaHoaDon"]; 

        if ($row["GiamGia"] != null && $row["GiamGia"] != 0) {
            $t["giamGia"] = $row["GiamGia"];
        }

        if ($row["MaDatBan"] != null) {
            $t["maDatBan"] = $row["MaDatBan"];
        }

        $t["trangThai"] = $row["TrangThai"];
        $t["gioDen"] = $row["GioDen"];
        $t["maBan"] = $row["MaBan"];
        $t["maKhachHang"] = $row["MaKhachHang"];

        $t['thucDonOrder'] = array();

        $db->prepare("Select ct.MaMon, SoLuong, MaChiTietHD From chi_tiet_hd AS ct INNER JOIN mon AS td ON ct.MaMon = td.MaMon 
                                    Where ct.MaHoaDon = :maHoaDon ORDER BY ct.MaChiTietHD DESC");

        $db->bind(':maHoaDon',$row['MaHoaDon']);

        foreach ($db->getArray() as $row) {
            $r = array();
            $r['maMon'] = $row['MaMon'];
            $r['soLuong'] = $row['SoLuong'];
            $r['maChiTietHD'] = $row['MaChiTietHD'];

            array_push($t['thucDonOrder'],$r);
        }

        array_push($response["hoaDon"],$t);

    }
    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>