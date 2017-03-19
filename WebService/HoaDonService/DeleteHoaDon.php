<?php
require_once '../dbConnect.php';

function dispInfo()
{
    if (isset($_GET['maHoaDon'])) {

        $maHoaDon = $_GET['maHoaDon'];
        $maBan = $_GET['maBan'];

        $db = new Database();

        $db->query('DELETE FROM hoa_don WHERE MaHoaDon = '.$maHoaDon.'');

        if ($db->getRowCount() > 0) {
            if (isset($_GET['maDatBan'])) {
                $maDatBan = $_GET['maDatBan']; 

                $db->query('DELETE FROM person WHERE MaPerson = (SELECT MaPerson FROM dat_ban WHERE MaDatBan = '.$maDatBan.')');
                $db->query('DELETE FROM dat_ban WHERE MaDatBan = '.$maDatBan.'');
            }

            $db->query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');

            echo 'success';
        }

    }

}

dispInfo();
?>