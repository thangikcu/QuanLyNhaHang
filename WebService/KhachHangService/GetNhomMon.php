<?php
     require_once '../dbConnect.php';
     
     function dispInfo(){
        $db = new Database();
     
     
        $response["nhomMon"] = array();
     
      
        $db->prepare("SELECT * FROM nhom_mon WHERE HienThi = 1 ORDER BY MaLoai DESC");
     
     
        foreach($db->getArray() as $row){ 
            $t = array();
            $t["maLoai"] = $row["MaLoai"];
            $t["tenLoai"] = $row["TenLoai"];
            $t["mauSac"] = $row["MauSac"];         
            
            array_push($response["nhomMon"], $t);
        }
        header('Content-Type: application/json');
     
        echo json_encode($response);
    }
     
    dispInfo();
?>