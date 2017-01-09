<?php
     include_once '../dbConnect.php';
     
     function dispInfo(){
        $db = new dbConnect();
     
     
        $response["nhomMon"] = array();
     
      
        $result = mysql_query("SELECT * FROM nhommon");
     
     
        while($row = mysql_fetch_array($result)){ 
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