<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
     
     
        
        $response["thucDon"] = array();
     
      
        $result = mysql_query("SELECT * FROM thucdon");
     

        while($row = mysql_fetch_array($result)){ 
            $t = array();
            $t["maMon"] = $row["MaMon"];
            $t["tenMon"] = $row["TenMon"];
            $t["maLoai"] = $row["MaLoai"];
            $t["donGia"] = $row["DonGia"];
            $t["donViTinh"] = $row["DVT"];
            $t["hinhAnh"] = base64_encode($row["HinhAnh"]);
            $t["hienThi"] = $row["HienThi"];
         
        
            array_push($response["thucDon"], $t);
        }

        header('Content-Type: application/json');
    
        echo json_encode($response);            
        
     }
 
    dispInfo();
?>