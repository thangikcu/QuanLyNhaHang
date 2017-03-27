<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maTinTuc = $_GET['maTinTuc'];

    $db = new Database();

    $db->query('DELETE FROM tin_tuc WHERE MaTinTuc = '.$maTinTuc.'');

    if ($db->getRowCount() > 0) {

        echo 'success';


    }

}

dispInfo();
?>