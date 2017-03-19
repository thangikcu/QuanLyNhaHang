<?php
require_once '../dbConnect.php';

function dispInfo()
{
    $db = new Database();

    $response["thucDon"] = array();

    $db->prepare("SELECT * FROM thuc_don ORDER BY MaMon DESC");

    foreach ($db->getArray() as $row)
    {
        $t = array();
        $t["maMon"] = $row["MaMon"];
        $t["tenMon"] = $row["TenMon"];
        $t["maLoai"] = $row["MaLoai"];
        $t["donGia"] = $row["DonGia"];
        $t["donViTinh"] = $row["DVT"];
        $t["hinhAnh"] = base64_encode($row["HinhAnh"]);
        $t["hienThi"] = $row["HienThi"];

        array_push($response["thucDon"], $t);
    }

    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>