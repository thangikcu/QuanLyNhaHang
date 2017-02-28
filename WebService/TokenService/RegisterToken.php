<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        
        if(isset($_POST['token']) && isset($_POST['type'])){
            $token = $_POST['token'];
            $type = $_POST['type'];
            
            $db->query('INSERT INTO token (Token, Type) VALUES ("'.$token.'", "'.$type.'") ON DUPLICATE KEY UPDATE Token = "'.$token.'" ');
            
            if($db->getRowCount() > 0){
                echo "success";
            }
        }
     }
 
    dispInfo();
?>