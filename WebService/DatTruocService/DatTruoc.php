<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){

        $maBan = $_POST['maBan'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $ghiChu = NULL;
        if(isset($_POST['ghiChu'])){
            $ghiChu = $_POST['ghiChu'];
        } 
        
        
    
        $db = new dbConnect();

        $result = mysql_query('INSERT INTO datban(TenKhachHang, SDT, GioDen, GhiChu, MaBan, TrangThai) VALUES ("'.$tenKhachHang.'", "'.$soDienThoai.'", "'.$gioDen.'", "'.$ghiChu.'", "'.$maBan.'", "0")');
     
    
        if($result){
            $result2 = mysql_query('SELECT MaDatBan FROM datban ORDER BY MaDatBan DESC LIMIT 1');
            $row2 = mysql_fetch_row($result2);
            
            $maDatTruoc = $row2[0];
            
            mysql_query('UPDATE ban SET TrangThai = 1 WHERE MaBan = '.$maBan.' ');
            
            echo $maDatTruoc;
            
        }
        
    }

    dispInfo();
?>