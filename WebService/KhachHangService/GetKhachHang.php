<?php
require_once '../dbConnect.php';

function dispInfo()
{
    $db = new Database();

    $db->prepare("SELECT * FROM khach_hang
                    INNER JOIN tai_khoan ON tai_khoan.MaTaiKhoan = khach_hang.MaTaiKhoan
                    INNER JOIN person ON person.MaPerson = khach_hang.MaPerson");

    $response["khachHang"] = array();

    foreach ($db->getArray() as $row) {
        $t = array();
        $t["maKhachHang"] = $row["MaKhachHang"];
        $t["tenKhachHang"] = $row["HoTen"]; 
        $t["sdt"] = $row["SoDienThoai"];
        $t["diaChi"] = $row["DiaChi"];
        $t["tenDangNhap"] = $row["TenDangNhap"];
        $t["matKhau"] = $row["MatKhau"];
        $t["maToken"] = $row["MaToken"];

        array_push($response["khachHang"], $t);
    }

    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>