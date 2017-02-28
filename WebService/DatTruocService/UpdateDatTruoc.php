<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatTruoc = $_GET['maDatTruoc'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $ghiChu = isset($_POST['ghiChu']) ? $_POST['ghiChu'] : null;
 
        $db = new Database();

        $db->query('UPDATE dat_ban SET TenKhachHang="'.$tenKhachHang.'", SDT="'.$soDienThoai.'", GioDen="'.$gioDen.'", GhiChu="'.$ghiChu.'" WHERE MaDatBan = '.$maDatTruoc.'');
     
    
        if($db->getRowCount() > 0){
            echo 'success';
            
        }
        
    }

    dispInfo();
?>