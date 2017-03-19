<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        
        if(isset($_POST['token'])){
            $token = $_POST['token'];
            
            
            $db->query('INSERT INTO token (Token) VALUES ("'.$token.'") ON DUPLICATE KEY UPDATE Token = "'.$token.'" ');
            
            if($db->getRowCount() > 0){
                echo "success";
            }
        }
     }
 
    dispInfo();
?>