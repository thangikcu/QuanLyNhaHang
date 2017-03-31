<?php
require_once '../dbConnect.php';

function dispInfo() {
    $db = new Database();

    $maKhachHang = $_GET['maKhachHang'];

    $db->prepare("SELECT * FROM yeu_cau WHERE MaKhachHang = :maKhachHang");
    $db->bind(':maKhachHang', $maKhachHang);
    $db->execute();

    $response["yeuCau"] = array();

    if ($db->getRowCount() > 0) {
        $t = array();
        $row = $db->getRow();

        $t["maYeuCau"] = $row["MaYeuCau"];
        $t["yeuCau"] = json_decode($row["YeuCau"]);

        array_push($response["yeuCau"], $t);

    }
    header('Content-Type: application/json');

    echo json_encode($response);

}

dispInfo();
?>