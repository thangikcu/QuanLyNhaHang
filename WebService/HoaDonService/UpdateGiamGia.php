<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maHoaDon'])){
        
            $maHoaDon = $_GET['maHoaDon'];

            if(isset($_POST['giamGia'])){
                $giamGia = $_POST['giamGia'];
            }
 
            $db = new Database();

            $db->prepare('UPDATE hoa_don SET GiamGia = :giamGia WHERE MaHoaDon = :maHoaDon ');
            $db->bind(':giamGia', $giamGia);
            $db->bind(':maHoaDon', $maHoaDon);
            $db->execute();
         
        
            if($db->getRowCount() > 0){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>