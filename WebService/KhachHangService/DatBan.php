<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maKhachHang = $_POST['maKhachHang'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : NULL;
    
        $db = new Database();

        $db->prepare('INSERT INTO `dat_ban`(`MaKhachHang`, `GioDen`, `YeuCau`, `TrangThai`) VALUES (:maKhachHang, :gioDen, :yeuCau, :trangThai)');
        $db->bind(':maKhachHang', $maKhachHang);
        $db->bind(':gioDen', $gioDen);
        $db->bind(':yeuCau', $yeuCau);
        $db->bind(':trangThai', 0);
        $db->execute();
     
    
        if($db->getRowCount() > 0){
            $db->prepare('SELECT `MaDatBan` FROM `dat_ban` ORDER BY MaDatBan DESC LIMIT 1');
            
            echo $db->getRow()['MaDatBan'];
        }
        
    }

    dispInfo();
?>