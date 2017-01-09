<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){

        $maHoaDon = $_POST['maHoaDon'];
        $maMon = $_POST['maMon'];
        $soLuong = $_POST['soLuong'];
    
        $db = new dbConnect();

        $result = mysql_query('INSERT INTO chitiethd (MaHoaDon, MaMon, SoLuong) VALUES ("'.$maHoaDon.'", "'.$maMon.'", "'.$soLuong.'")');
     
    
        if($result){
            $result2 = mysql_query('SELECT MaChiTietHD FROM chitiethd ORDER BY MaChiTietHD DESC LIMIT 1');
            $row2 = mysql_fetch_row($result2);
            echo $row2[0];
        }
        
    }

    dispInfo();
?>