<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
     
        $db->prepare("SELECT * FROM dat_ban");
     
        
        $response["datTruoc"] = array();
     
        foreach($db->getArray() as $row){ 
            $t = array();
            $t["maDatTruoc"] = $row["MaDatBan"];
            $t["tenKhachHang"] = $row["TenKhachHang"];
            $t["soDienThoai"] = $row["SDT"];
            $t["gioDen"] = $row["GioDen"];
            $t["ghiChu"] = $row["GhiChu"];
            $t["maBan"] = $row["MaBan"];
            $t["trangThai"] = $row["TrangThai"];
         
        
            array_push($response["datTruoc"], $t);
        }

        header('Content-Type: application/json');
    
        echo json_encode($response);            
        
     }
 
    dispInfo();
?>