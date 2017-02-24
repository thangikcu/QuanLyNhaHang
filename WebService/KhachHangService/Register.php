<?php
    include_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new dbConnect();
        
        $hoTen = $_POST['hoTen'];
        $diaChi = $_POST['diaChi'];
        $sdt = $_POST['sdt'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $token = $_POST['token'];
        
        $result = mysql_query('SELECT * FROM khachhang WHERE TenDangNhap = "'.$username.'"');
        
        if(mysql_num_rows($result) == 0){
            $result = mysql_query('SELECT MaToken FROM token WHERE Token = "'.$token.'" AND Type = 2');
            
            if($result){
                $maToken = mysql_fetch_row($result);
                
                
                $result = mysql_query('INSERT INTO khachhang (TenKhachHang, SoDienThoai, DiaChi, TenDangNhap, MatKhau, MaToken)
                 VALUES ("'.$hoTen.'", "'.$sdt.'", "'.$diaChi.'", "'.$username.'", "'.$password.'", "'.$maToken[0].'")');
                
                if($result){
                    echo "success";
                }
            } 
        }else{
            echo "exist";
        }

    }
 
    dispInfo();
?> 