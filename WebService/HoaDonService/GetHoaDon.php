<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){

            $db = new dbConnect();
     
            
            $response["hoaDon"] = array();
         
          
            $result = mysql_query('Select * From hoadon Where TrangThai = 0');
         
         
            while($row = mysql_fetch_array($result)){
                $t = array();
                $t["maHoaDon"] = $row["MaHoaDon"];
                
                if($row["GiamGia"] != null && $row["GiamGia"] != 0){
                    $t["giamGia"] = $row["GiamGia"];
                }
                
                if($row["MaDatBan"] != null){
                    $t["maDatTruoc"] = $row["MaDatBan"];
                }
                
                
                $t["gioDen"] = $row["GioDen"];
                $t["maBan"] = $row["MaBan"];
                
                $t['thucDonOrder'] = array();
                
                
                $result2 = mysql_query("Select ct.MaMon, SoLuong, MaChiTietHD From chitiethd AS ct INNER JOIN thucdon AS td ON ct.MaMon = td.MaMon Where ct.MaHoaDon = '".$row['MaHoaDon']."'");
                
                while($row2 = mysql_fetch_array($result2)){
                    $r = array();
                    $r['maMon'] = $row2['MaMon'];
                    $r['soLuong'] = $row2['SoLuong'];
                    $r['maChiTietHD'] = $row2['MaChiTietHD'];
                    
                    array_push($t['thucDonOrder'], $r);
                }
                
                
                array_push($response["hoaDon"], $t);
                
            }
            header('Content-Type: application/json');
            
            echo json_encode($response);

        }

    dispInfo();
?>