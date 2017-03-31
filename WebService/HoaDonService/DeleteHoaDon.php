<?php
require_once '../dbConnect.php';

function dispInfo() {
    if (isset($_GET['maHoaDon'])) {

        $maHoaDon = $_GET['maHoaDon'];
        $maBan = $_GET['maBan'];

        $db = new Database();

        $db->query('DELETE FROM hoa_don WHERE MaHoaDon = '.$maHoaDon.'');

        if ($db->getRowCount() > 0) {
            if (isset($_GET['maDatBan'])) {
                $maDatBan = $_GET['maDatBan'];

                $db->prepare('SELECT MaPerson FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');
                $maPerson = $db->getRow()['MaPerson'];

                $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');

                if (!is_null($maPerson)) {
                    $db->query('DELETE FROM person WHERE MaPerson = '.$maPerson.'');
                }

            }

            $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');

            echo 'success';
        }

    }

}

dispInfo();
?>