<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        if(isset($_GET['maBan'])){
        
            $maBan = $_GET['maBan'];
            
            $qr = '';

            
            if(isset($_POST['trangThai'])){
                $trangThai = $_POST['trangThai'];
                $qr .= "TrangThai = '".$trangThai."'";
                
            }
            if(isset($_POST['tenBan'])){
                $tenBan = $_POST['tenBan'];
                $qr .= ", TenBan = '".$tenBan."'";
                
            }

            
            $db = new Database();
            $db->query('UPDATE ban SET '.$qr.' WHERE MaBan = '.$maBan.' ');
            

            if($db->getRowCount() > 0){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>