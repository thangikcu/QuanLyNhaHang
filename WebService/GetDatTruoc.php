<?php
    include_once './dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
     
     
        
        $response["datTruoc"] = array();
     
      
        $result = mysql_query("SELECT * FROM datban");
     

        while($row = mysql_fetch_array($result)){ 
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