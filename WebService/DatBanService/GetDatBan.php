<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
     
        $db->prepare("SELECT * FROM dat_ban WHERE TrangThai != 2 ORDER BY MaDatBan DESC");
     
        
        $response["datBan"] = array();
     
        foreach($db->getArray() as $row){ 
            $t = array();
            $t["maDatBan"] = $row["MaDatBan"];
            $t["tenKhachHang"] = $row["TenKhachHang"];
            $t["soDienThoai"] = $row["SoDienThoai"];
            $t["maKhachHang"] = $row["MaKhachHang"];
            $t["gioDen"] = $row["GioDen"];
            $t["yeuCau"] = $row["YeuCau"];
            $t["maBan"] = $row["MaBan"];
            $t["trangThai"] = $row["TrangThai"];
         
        
            array_push($response["datBan"], $t);
        }

        header('Content-Type: application/json');
    
        echo json_encode($response);            
        
     }
 
    dispInfo();
?>