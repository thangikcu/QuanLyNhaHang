<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maDatTruoc'])){
        
            $maDatTruoc = $_GET['maDatTruoc'];
            $maBan = $_GET['maBan'];
            

            $db = new dbConnect();

            $result = mysql_query('DELETE FROM datban WHERE MaDatBan = '.$maDatTruoc.'');
         
        
            if($result){

                mysql_query('UPDATE ban SET TrangThai = 0 WHERE MaBan = '.$maBan.' ');
                echo 'success';
             }
            
        }


    }
 
    dispInfo();
?>