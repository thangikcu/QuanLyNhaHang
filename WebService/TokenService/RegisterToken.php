<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
        
        if(isset($_POST['token']) && isset($_POST['type'])){
            $token = $_POST['token'];
            $type = $_POST['type'];
            
            $result = mysql_query('INSERT INTO token (Token, Type) VALUES ("'.$token.'", "'.$type.'") ON DUPLICATE KEY UPDATE Token = "'.$token.'" ');
            
            if($result){
                echo "success";
            }
        }
     }
 
    dispInfo();
?>