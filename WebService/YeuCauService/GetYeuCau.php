<?php
require_once '../dbConnect.php';

function dispInfo()
{
    $db = new Database();

    $db->prepare("SELECT * FROM yeu_cau");

    $response["yeuCau"] = array();

    foreach ($db->getArray() as $row) {
        $t = array();
        $t["maYeuCau"] = $row["MaYeuCau"];
        $t["maKhachHang"] = $row["MaKhachHang"]; 
        $t["yeuCau"] = json_decode($row["YeuCau"]);
        $t["thoiGian"] = $row["ThoiGian"];

        array_push($response["yeuCau"], $t);
    }

    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>