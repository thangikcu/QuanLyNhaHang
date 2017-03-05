<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatBan = $_GET['maDatBan'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;
 
        $db = new Database();

        $db->query('UPDATE dat_ban SET TenKhachHang="'.$tenKhachHang.'", SDT="'.$soDienThoai.'", GioDen="'.$gioDen.'", YeuCau="'.$yeuCau.'" WHERE MaDatBan = '.$maDatBan.'');
     
    
        if($db->getRowCount() > 0){
            echo 'success';
            
        }
        
    }

    dispInfo();
?>