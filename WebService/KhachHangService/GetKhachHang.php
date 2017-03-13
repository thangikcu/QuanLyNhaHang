<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
     
        $db->prepare("SELECT * FROM khach_hang");
     
        
        $response["khachHang"] = array();

        foreach($db->getArray() as $row){ 
            $t = array();
            $t["maKhachHang"] = $row["MaKhachHang"];
            $t["tenKhachHang"] = $row["TenKhachHang"];
            $t["sdt"] = $row["SoDienThoai"];
            $t["diaChi"] = $row["DiaChi"];
            $t["tenDangNhap"] = $row["TenDangNhap"];
            $t["matKhau"] = $row["MatKhau"];
            $t["maToken"] = $row["MaToken"];
            
            
            array_push($response["khachHang"], $t);
        }


        header('Content-Type: application/json');
    
        echo json_encode($response);            
        
     }
 
    dispInfo();
?>