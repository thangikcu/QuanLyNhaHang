<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        
        $hoTen = $_POST['hoTen'];
        $diaChi = $_POST['diaChi'];
        $sdt = $_POST['sdt'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $token = $_POST['token'];
        
        $db->query('SELECT * FROM khach_hang WHERE TenDangNhap = "'.$username.'"');
        
        if($db->getRowCount() == 0){
            $db->query('SELECT MaToken FROM token WHERE Token = "'.$token.'" AND Type = 2');
            
            if($db->getRowCount() > 0){
                $maToken = $db->getRow()['MaToken'];
                
                
                $db->query('INSERT INTO khach_hang (TenKhachHang, SoDienThoai, DiaChi, TenDangNhap, MatKhau, MaToken)
                 VALUES ("'.$hoTen.'", "'.$sdt.'", "'.$diaChi.'", "'.$username.'", "'.$password.'", "'.$maToken.'")');
                
                if($db->getRowCount() > 0){
                    echo "success";
                }
            } 
        }else{
            echo "exist";
        }

    }
 
    dispInfo();
?> 