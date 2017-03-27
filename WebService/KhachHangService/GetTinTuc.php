<?php
require_once '../dbConnect.php';

function dispInfo()
{
    $db = new Database();

    $db->prepare("SELECT * FROM tin_tuc WHERE HienThi = 1 ORDER BY MaTinTuc DESC");

    $response["tinTuc"] = array();

    foreach ($db->getArray() as $row) {
        $t = array();
        $t["maTinTuc"] = $row["MaTinTuc"];
        $t["tieuDe"] = $row["TieuDe"]; 
        $t["noiDung"] = $row["NoiDung"];
        $t["ngayDang"] = $row["NgayDang"];
        $t["hinhAnh"] = base64_encode($row["HinhAnh"]);
        $t["hienThi"] = $row["HienThi"];

        array_push($response["tinTuc"], $t);
    }

    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>