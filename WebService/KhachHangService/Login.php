<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
        
        if(isset($_POST['username']) && isset($_POST['password'])){
            $user = $_POST['username'];
            $pass = $_POST['password'];
            $token = $_POST['token'];
            $mode = $_POST['mode'];
            
            $result = mysql_query('SELECT * FROM khachhang WHERE TenDangNhap = "'.$user.'" AND MatKhau = "'.$pass.'"');
            
            $check = mysql_num_rows($result);
            
            if($check > 0){
                
                $maToken = mysql_fetch_array($result);
                $result = mysql_query('SELECT tk.Token FROM khachhang AS kh JOIN token AS tk WHERE tk.MaToken = "'.$maToken['MaToken'].'" AND tk.Type = 2');
                
                if($result){
                    $lastToken = mysql_fetch_row($result);
                
                    if($mode == "auto"){
                       
                        if($token != $lastToken[0]){
                            return;
                        }else{
                            echo "success";
                        }
                        
                    }else{
                        if($token != $lastToken[0]){
                            
                            $maToken = mysql_fetch_row(mysql_query('SELECT MaToken FROM token WHERE Token = "'.$token.'" AND Type = 2'));
                            $result = mysql_query('UPDATE khachhang SET MaToken = "'.$maToken[0].'" WHERE TenDangNhap = "'.$user.'" AND MatKhau = "'.$pass.'"');
                            
                            if($result){
                                
                                include_once '../Firebase.php';
                                $firebase = new Firebase();
                                $push = new Push();
                                
                                $push->setNotification(null, null);
                                $data = array();
                                $data['action'] = "LOGOUT_ACTION";
                                
                                $firebase->send($lastToken[0], $push->getNotification(), $data);
                            }
                            
                        }
                        echo "success";
                        
                    }
                }


            }
        }
     }
 
    dispInfo();
?>