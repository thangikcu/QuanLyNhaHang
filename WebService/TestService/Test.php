<?php

    function dispInfo(){
        
        include_once '../dbConnect.php';
        include_once '../Firebase.php';
        
        
        
        $db = new Database();
        
        $firebase = new Firebase();
        $push = new Push();

        $title = isset($_POST['title']) ? $_POST['title'] : 'test';
        $message = isset($_POST['message']) ? $_POST['message'] : 'message test';
        
        
        $db->prepare('SELECT * FROM token');
        $tokens = array();
        foreach($db->getArray() as $row){
            $tokens[] = $row['Token'];
        }
    
        $push->setNotification($title, $message);
        $push->setDatas("TEST_ACTION", null);

        
        
        $respond = $firebase ->sendMultiple($tokens, $push->getNotification(), $push->getDatas());
        echo $respond;

     }
 
    dispInfo();
?>