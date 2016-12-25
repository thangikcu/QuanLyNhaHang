<?php
    include_once './dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
     
        
        $response["ban"] = array();
     
      
        $result = mysql_query("SELECT * FROM ban");
     
    
        while($row = mysql_fetch_array($result)){ 
            $t = array();
            $t["maBan"] = $row["MaBan"];
            $t["tenBan"] = $row["TenBan"];
            $t["trangThai"] = $row["TrangThai"];
            $t["hienThi"] = $row["HienThi"];
            
            
            array_push($response["ban"], $t);
        }
        header('Content-Type: application/json');
    
        echo json_encode($response);

    }
 
    dispInfo();
?>