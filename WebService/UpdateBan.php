<?php
    include_once './dbConnect.php';
 
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

            
            $db = new dbConnect();

            $result = mysql_query('UPDATE ban SET '.$qr.' WHERE MaBan = '.$maBan.' ');
         
        
            if($result){
                echo 'success';
            }
            
        }


    }
 
    dispInfo();
?>