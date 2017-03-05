
<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        
        $maKhachHang = $_GET['maKhachHang'];
        
        $db = new Database();
        $db->prepare('SELECT * FROM `dat_ban` WHERE `MaKhachHang`= :maKhachHang AND TrangThai= :trangThai');
        $db->bind('maKhachHang', $maKhachHang);
        $db->bind('trangThai', 0);
        $db->execute();
        
        if($db->getRowCount() > 0){
            
            $response["datBan"] = array();
            
            $row = $db->getRow();
            
            $t = array();
            $t["maDatBan"] = $row["MaDatBan"];
            $t["gioDen"] = $row["GioDen"];
            $t["yeuCau"] = $row["YeuCau"];

            header('Content-Type: application/json');
        
            echo json_encode($t);
        }else{
            echo 'fail';
        }


    }
 
    dispInfo();
?>