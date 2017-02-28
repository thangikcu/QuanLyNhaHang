<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        $db->prepare('SELECT * FROM ban');
        
        $response["ban"] = array();
        
        foreach($db->getArray() as $row){ 
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