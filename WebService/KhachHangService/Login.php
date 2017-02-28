<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $user = $_POST['tenDangNhap'];
        $pass = $_POST['matKhau'];
        $token = $_POST['token'];
        $mode = $_POST['mode'];
            
        $db = new Database();
        
        $db->query('SELECT * FROM khach_hang WHERE TenDangNhap = "'.$user.'" AND MatKhau = "'.$pass.'"');

        if($db->getRowCount() > 0){
            $response["khachHang"] = array();

            foreach($db->getArray() as $row){ 
                $t = array();
                $t["maKhachHang"] = $row["MaKhachHang"];
                $t["tenKhachHang"] = $row["TenKhachHang"];
                $t["sdt"] = $row["SoDienThoai"];
                $t["diaChi"] = $row["DiaChi"];
                $t["maToken"] = $row["MaToken"];
                
                
                array_push($response["khachHang"], $t);
            }
                    
            $maToken = $db->getRow()['MaToken'];
            $db->query('SELECT tk.Token FROM khach_hang AS kh JOIN token AS tk WHERE tk.MaToken = "'.$maToken.'" AND tk.Type = 2');
            
            if($db->getRowCount() > 0){
                $lastToken = $db->getRow()['Token'];
            
                if($mode == "auto"){
                   
                    if($token != $lastToken){
                        echo "other";
                    }else{
                        
                        header('Content-Type: application/json');
                    
                        echo json_encode($response);
                    }
                    
                }else{
                    if($token != $lastToken){
                        $db->prepare('SELECT MaToken FROM token WHERE Token = "'.$token.'" AND Type = 2');
                        
                        $maToken = $db->getRow()['MaToken'];
                        $db->query('UPDATE khach_hang SET MaToken = "'.$maToken.'" WHERE TenDangNhap = "'.$user.'" AND MatKhau = "'.$pass.'"');
                        
                        if($db->getRowCount() > 0){
                            
                            include_once '../Firebase.php';
                            $firebase = new Firebase();
                            $push = new Push();
                            
                            $push->setNotification(null, null);
                            $data = array();
                            $data['action'] = "LOGOUT_ACTION";
                            
                            $firebase->send($lastToken, $push->getNotification(), $data);
                        }
                        
                    }

                    header('Content-Type: application/json');
                
                    echo json_encode($response);
                    
                }
            }else{
                echo "fail";
            }

        }else{
            echo "fail";
        }
    }

 
    dispInfo();
?>