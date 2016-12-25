<?php
    include_once './dbConnect.php';
 
    function dispInfo(){

        $maDatTruoc = 'NULL';
        
        if(isset($_POST['maBan'])){
            $maBan = $_POST['maBan'];
        }
        if(isset($_POST['gioDen'])){
            $gioDen = $_POST['gioDen'];
        }
        if(isset($_POST['maDatTruoc'])){
            $maDatTruoc = $_POST['maDatTruoc'];
        }
    
        $db = new dbConnect();

        $result = mysql_query('INSERT INTO hoadon(MaBan, MaDatBan, GioDen, TrangThai) VALUES ("'.$maBan.'", '.$maDatTruoc.', "'.$gioDen.'", "0")');
        
        if($result){
            
            $maMon = $_POST['maMon'];
            $soLuong = $_POST['soLuong'];
            
            $result2 = mysql_query('SELECT MaHoaDon FROM hoadon ORDER BY MaHoaDon DESC LIMIT 1');
            $row2 = mysql_fetch_row($result2);
            
            $maHoaDon = $row2[0];
            
            $result3 = mysql_query('INSERT INTO chitiethd (MaHoaDon, MaMon, SoLuong) VALUES ("'.$maHoaDon.'", "'.$maMon.'", "'.$soLuong.'")');
     
    
            if($result3){
                $result4 = mysql_query('SELECT MaChiTietHD FROM chitiethd ORDER BY MaChiTietHD DESC LIMIT 1');
                $row3 = mysql_fetch_array($result4);
                
                
                $response["ma"] = array();
                $t = array();
                $t['maHoaDon'] = $maHoaDon;
                $t['maChiTietHD'] = $row3['MaChiTietHD'];
            
                mysql_query('UPDATE ban SET TrangThai = 2 WHERE MaBan = '.$maBan.' ');
                mysql_query('UPDATE datban SET TrangThai = 1 WHERE MaDatBan = '.$maDatTruoc.' ');
                
                array_push($response["ma"], $t);
                header('Content-Type: application/json');
            
                echo json_encode($response);
            }
        }else{
                echo "false";
            }
        
    }

    dispInfo();
?>