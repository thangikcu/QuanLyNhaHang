<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maBan = $_POST['maBan'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : NULL;
 
        $db = new Database();
        $db->query('INSERT INTO dat_ban(TenKhachHang, SDT, GioDen, YeuCau, MaBan, TrangThai) VALUES ("'.$tenKhachHang.'", "'.$soDienThoai.'", "'.$gioDen.'", "'.$yeuCau.'", "'.$maBan.'", "0")');

        if($db->getRowCount() > 0){
            $db->query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
            
            $db->prepare('SELECT MaDatBan FROM dat_ban ORDER BY MaDatBan DESC LIMIT 1');
            
            echo $db->getRow()['MaDatBan'];
            
        }
        
    }

    dispInfo();
?>