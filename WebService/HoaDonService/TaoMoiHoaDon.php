<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatBan = 'NULL';
        
        if(isset($_POST['maBan'])){
            $maBan = $_POST['maBan'];
        }
        if(isset($_POST['gioDen'])){
            $gioDen = $_POST['gioDen'];
        }
        if(isset($_POST['maDatBan'])){
            $maDatBan = $_POST['maDatBan'];
        }
    
        $db = new Database();

        $db->query('INSERT INTO hoa_don(MaBan, MaDatBan, GioDen, TrangThai) VALUES ("'.$maBan.'", '.$maDatBan.', "'.$gioDen.'", "0")');
        
        if($db->getRowCount() > 0){
            
            $maMon = $_POST['maMon'];
            $soLuong = $_POST['soLuong'];
            
            
            $maHoaDon = $db->getLastInsertId();
            
            $db->query('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES ("'.$maHoaDon.'", "'.$maMon.'", "'.$soLuong.'")');
     
    
            if($db->getRowCount() > 0){
                
                $response["ma"] = array();
                $t = array();
                $t['maHoaDon'] = $maHoaDon;
                $t['maChiTietHD'] = $db->getLastInsertId();
            
                $db->query('UPDATE ban SET TrangThai = 2 WHERE MaBan = '.$maBan.' ');
                $db->query('UPDATE dat_ban SET TrangThai = 1 WHERE MaDatBan = '.$maDatBan.' ');
                
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