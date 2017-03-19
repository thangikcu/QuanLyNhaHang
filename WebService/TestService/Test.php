<?php

    function dispInfo(){
        
        include_once '../dbConnect.php';
        include_once '../Firebase.php';
        
        
        
        $db = new Database();
        
        $firebase = new Firebase();
        $push = new Push();

        $title = isset($_POST['title']) ? $_POST['title'] : 'test';
        $message = isset($_POST['message']) ? $_POST['message'] : 'message test';
        
        $push->setNotification($title, $message);
        $push->setDatas("NOTIFI_ACTION", null);

        
        
        $respond = $firebase ->sendMultiple($db->getAllTokenKhachHang(), $push->getNotification(), $push->getDatas());
        echo $respond;

     }
 
    dispInfo();
?>