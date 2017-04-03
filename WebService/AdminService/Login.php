<?php
require_once '../dbConnect.php';

function dispInfo() {

    $user = $_POST['tenDangNhap'];
    $pass = $_POST['matKhau'];
    $token = $_POST['token'];
    $mode = $_POST['mode'];

    $db = new Database();

    $db->prepare('SELECT * FROM admin 
                    INNER JOIN tai_khoan ON tai_khoan.MaTaiKhoan = admin.MaTaiKhoan
                    INNER JOIN person ON person.MaPerson = admin.MaPerson
                    WHERE TenDangNhap = :tenDangNhap AND MatKhau = :matKhau');
    $db->bind(':tenDangNhap', $user);
    $db->bind(':matKhau', $pass);
    $db->execute();

    if ($db->getRowCount() > 0) {

        $row = $db->getRow();
        
        $maAdmin = $row["MaAdmin"];

        $t = array();
        
        $t["maAdmin"] = $maAdmin;
        $t["hoTen"] = $row["HoTen"];
        $t["soDienThoai"] = $row["SoDienThoai"];
        $t["diaChi"] = $row["DiaChi"];
        $t["maToken"] = $row["MaToken"];
        $t['type'] = $row['Type'];

        $maToken = $t["maToken"];
        $maTaiKhoan = $row['MaTaiKhoan'];

        $db->prepare('SELECT Token FROM token WHERE token.MaToken = :maToken');
        $db->bind(':maToken', $maToken);
        $db->execute();

        if ($db->getRowCount() > 0) {
            $lastToken = $db->getRow()['Token'];

            if ($mode == "auto") {

                if ($token != $lastToken) {
                    echo "other";
                } else {

                    header('Content-Type: application/json');

                    echo json_encode($t);
                }

            } else {
                if ($token != $lastToken) {
                    $db->prepare('SELECT MaToken FROM token WHERE Token = "'.$token.'"');

                    $maToken = $db->getRow()['MaToken'];

                    $db->query('UPDATE tai_khoan SET MaToken = "'.$maToken.'" WHERE MaTaiKhoan = "'.
                        $maTaiKhoan.'"');

                    if ($db->getRowCount() > 0) {
                        $t["maToken"] = $maToken;

                        include_once '../Firebase.php';
                        $firebase = new Firebase();
                        $push = new Push();

                        $datas = array();
                        $datas['maAdmin'] = $maAdmin;

                        $push->setDatas("LOGOUT_ACTION", $datas);

                        $firebase->send($lastToken, null, $push->getDatas());
                    }

                }

                header('Content-Type: application/json');

                echo json_encode($t);

            }
        } else {
            echo "fail";
        }

    } else {
        echo "fail";
    }
}

dispInfo();
?>