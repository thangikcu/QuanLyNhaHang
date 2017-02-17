<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
        
        if(isset($_POST['username']) && isset($_POST['password'])){
            $user = $_POST['username'];
            $pass = $_POST['password'];
            
            $result = mysql_query('SELECT * FROM khachhang WHERE TenDangNhap = "'.$user.'" AND MatKhau = "'.$pass.'"');
            
            $check = mysql_num_rows($result);
            
            if($check > 0){
                echo "success";
            }
        }
     }
 
    dispInfo();
?>