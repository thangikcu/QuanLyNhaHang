<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){

        $maDatBan = $_GET['maDatBan'];
        $tenKhachHang = $_POST['tenKhachHang'];
        $soDienThoai = $_POST['soDienThoai'];
        $gioDen = $_POST['gioDen'];
        $yeuCau = isset($_POST['yeuCau']) ? $_POST['yeuCau'] : null;
        
        $db = new Database();
        include_once '../Firebase.php';
        $firebase = new Firebase();
        $push = new Push();
        
        $datas = array();
        $datas['maDatBan'] = $maDatBan;
        $datas['tenKhachHang'] = $tenKhachHang;
        $datas['soDienThoai'] = $soDienThoai;
        $datas['gioDen'] = $gioDen;
        $datas['yeuCau'] = $yeuCau;

        
        $push->setDatas("UPDATE_DAT_BAN_ACTION", $datas);
        
        if (isset($_POST['maKhachHang'])){
            
            $maKhachHang = $_POST['maKhachHang'];
            $maToken = $_POST['maToken'];
            
            $db->prepare('UPDATE khach_hang SET TenKhachHang = :tenKhachHang, SoDienThoai = :soDienThoai WHERE MaKhachHang = :maKhachHang');
            $db->bind(':tenKhachHang', $tenKhachHang);
            $db->bind(':soDienThoai', $soDienThoai);
            $db->bind(':maKhachHang', $maKhachHang);
            $db->execute();


            if($db->getRowCount() > 0){

                $db->prepare('UPDATE dat_ban SET GioDen = :gioDen, YeuCau = :yeuCau WHERE MaDatBan = :maDatBan');
                $db->bind(':gioDen', $gioDen);
                $db->bind(':yeuCau', $yeuCau);
                $db->bind(':maDatBan', $maDatBan);
                $db->execute(); 
                
                if($db->getRowCount() > 0){
                    echo 'success';
                    
                    $firebase ->send($db->getTokenKhachHangByMa($maToken), null, $push->getDatas());
                    $firebase ->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
       
                } 
            }
          
        }else{
            
            $db->prepare('UPDATE dat_ban SET TenKhachHang = :tenKhachHang, SoDienThoai = :soDienThoai, GioDen = :gioDen, YeuCau = :yeuCau WHERE MaDatBan = :maDatBan');
            $db->bind(':tenKhachHang', $tenKhachHang);
            $db->bind(':soDienThoai', $soDienThoai);
            $db->bind(':gioDen', $gioDen);
            $db->bind(':yeuCau', $yeuCau);
            $db->bind(':maDatBan', $maDatBan);
            $db->execute();
        
    
            if($db->getRowCount() > 0){
                echo 'success';
                $firebase ->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
                
            }
        }
 
     }

    dispInfo();
?>