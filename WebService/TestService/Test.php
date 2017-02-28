<?php

    function dispInfo(){
        
        include_once '../dbConnect.php';
        include_once '../Firebase.php';
        
        $firebase = new Firebase();
        $push = new Push();
        $db = new Database();

        $title = isset($_POST['title']) ? $_POST['title'] : 'test';
        $message = isset($_POST['message']) ? $_POST['message'] : 'message test';
        
        
        $db->prepare('SELECT * FROM token');
        $tokens = array();
        foreach($db->getArray() as $row){
            $tokens[] = $row['Token'];
        }
    
        $push->setNotification($title, $message);
        $data = array();
        $data['action'] = 'TEST_ACTION';
        
        
        $respond = $firebase ->sendMultiple($tokens, $push->getNotification(), $data);
        echo $respond;

     }
 
    dispInfo();
?>