
<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maKhachHang = $_GET['maKhachHang'];

    $db = new Database();
    $response["hoaDon"] = array();
    $response["datBan"] = array();

    $db->prepare('Select * From hoa_don WHERE MaKhachHang = :maKhachHang AND TrangThai = :trangThai');
    $db->bind(':maKhachHang', $maKhachHang);
    $db->bind(':trangThai', 0);
    $db->execute();

    if ($db->getRowCount() > 0) {
        $row = $db->getRow();

        $t = array();
        $t["maHoaDon"] = $row["MaHoaDon"];

        if ($row["GiamGia"] != null && $row["GiamGia"] != 0) {
            $t["giamGia"] = $row["GiamGia"];
        }

        $t["gioDen"] = $row["GioDen"];

        $db->prepare('Select TenBan From ban Where MaBan = :maBan');
        $db->bind(':maBan', $row["MaBan"]);

        $t["tenBan"] = $db->getRow()['TenBan'];

        $t['thucDonOrder'] = array();

        $db->prepare("Select ct.MaMon, SoLuong, MaChiTietHD From chi_tiet_hd AS ct INNER JOIN mon AS td ON ct.MaMon = td.MaMon 
                                    Where ct.MaHoaDon = :maHoaDon ORDER BY ct.MaChiTietHD DESC");

        $db->bind(':maHoaDon', $row['MaHoaDon']);

        foreach ($db->getArray() as $row) {
            $r = array();
            $r['maMon'] = $row['MaMon'];
            $r['soLuong'] = $row['SoLuong'];
            $r['maChiTietHD'] = $row['MaChiTietHD'];

            array_push($t['thucDonOrder'], $r);
        }

        array_push($response["hoaDon"], $t);
    }

    $db->prepare('SELECT * FROM `dat_ban` WHERE `MaKhachHang`= :maKhachHang AND TrangThai= :trangThai');
    $db->bind(':maKhachHang', $maKhachHang);
    $db->bind(':trangThai', 0);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $response["datBan"] = array();

        $row = $db->getRow();

        $t = array();
        $t["maDatBan"] = $row["MaDatBan"];
        $t["gioDen"] = $row["GioDen"];
        $t["yeuCau"] = $row["YeuCau"];

        if ($row['MaBan'] != null) {
            $db->prepare('Select TenBan From ban Where MaBan = :maBan');
            $db->bind(':maBan', $row["MaBan"]);

            $t["tenBan"] = $db->getRow()['TenBan'];
        }

        array_push($response["datBan"], $t);
    }

    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>