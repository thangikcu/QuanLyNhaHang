<?php
    include_once './dbConnect.php';
 
    function dispInfo(){

        $maDatTruoc = $_GET['maDatTruoc'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $ghiChu = NULL;
        if(isset($_POST['ghiChu'])){
            $ghiChu = $_POST['ghiChu'];
        } 
        
        
    
        $db = new dbConnect();

        $result = mysql_query('UPDATE datban SET TenKhachHang="'.$tenKhachHang.'", SDT="'.$soDienThoai.'", GioDen="'.$gioDen.'", GhiChu="'.$ghiChu.'" WHERE MaDatBan = '.$maDatTruoc.'');
     
    
        if($result){
            echo 'success';
            
        }
        
    }

    dispInfo();
?>