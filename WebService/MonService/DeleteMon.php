<?php
require_once '../dbConnect.php';

function dispInfo() {

    $maMon = $_GET['maMon'];

    $db = new Database();

    $db->query('DELETE FROM mon WHERE MaMon = '.$maMon.'');

    if ($db->getRowCount() > 0) {

        echo 'success';


    }

}

dispInfo();
?>