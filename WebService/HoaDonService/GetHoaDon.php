<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

            $db = new Database();
     
            
            $response["hoaDon"] = array();
         
          
            $db->prepare('Select * From hoa_don Where TrangThai = 0');
         
         
            foreach($db->getArray() as $row){
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
                
                
                $db->prepare("Select ct.MaMon, SoLuong, MaChiTietHD From chi_tiet_hd AS ct INNER JOIN thuc_don AS td ON ct.MaMon = td.MaMon Where ct.MaHoaDon = '".$row['MaHoaDon']."'");
                
                foreach($db->getArray() as $row){
                    $r = array();
                    $r['maMon'] = $row['MaMon'];
                    $r['soLuong'] = $row['SoLuong'];
                    $r['maChiTietHD'] = $row['MaChiTietHD'];
                    
                    array_push($t['thucDonOrder'], $r);
                }
                
                
                array_push($response["hoaDon"], $t);
                
            }
            header('Content-Type: application/json');
            
            echo json_encode($response);

        }

    dispInfo();
?>