<?php

    function dispInfo(){
        
        include_once '../dbConnect.php';
        include_once '../Firebase.php';
        
        $firebase = new Firebase();
        $push = new Push();
        $db = new dbConnect();

        $title = isset($_POST['title']) ? $_POST['title'] : '';
        $message = isset($_POST['message']) ? $_POST['message'] : '';
        
        
        $result = mysql_query('SELECT * FROM Token');
        $tokens = array();
        while($row = mysql_fetch_array($result)){
            $tokens[] = $row['Token'];
        }
    
        $push->setNotification($title, $message);
        $data = array();
        $data['thang'] = 'ok';
        $data['bo'] = 'la';
        
        $respond = $firebase ->sendMultiple($tokens, $push->getNotification(), $data);
        echo $respond;

     }
 
    dispInfo();
?>