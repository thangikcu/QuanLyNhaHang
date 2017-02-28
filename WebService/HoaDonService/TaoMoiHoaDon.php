<?php
    require_once '../dbConnect.php';
 
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
    
        $db = new Database();

        $db->query('INSERT INTO hoa_don(MaBan, MaDatBan, GioDen, TrangThai) VALUES ("'.$maBan.'", '.$maDatTruoc.', "'.$gioDen.'", "0")');
        
        if($db->getRowCount() > 0){
            
            $maMon = $_POST['maMon'];
            $soLuong = $_POST['soLuong'];
            
            $db->prepare('SELECT MaHoaDon FROM hoa_don ORDER BY MaHoaDon DESC LIMIT 1');
            
            
            $maHoaDon = $db->getRow()['MaHoaDon'];
            
            $db->query('INSERT INTO chi_tiet_hd (MaHoaDon, MaMon, SoLuong) VALUES ("'.$maHoaDon.'", "'.$maMon.'", "'.$soLuong.'")');
     
    
            if($db->getRowCount() > 0){
                $db->prepare('SELECT MaChiTietHD FROM chi_tiet_hd ORDER BY MaChiTietHD DESC LIMIT 1');
                
                $response["ma"] = array();
                $t = array();
                $t['maHoaDon'] = $maHoaDon;
                $t['maChiTietHD'] = $db->getRow()['MaChiTietHD'];
            
                $db->query('UPDATE ban SET TrangThai = 2 WHERE MaBan = '.$maBan.' ');
                $db->query('UPDATE dat_ban SET TrangThai = 1 WHERE MaDatBan = '.$maDatTruoc.' ');
                
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