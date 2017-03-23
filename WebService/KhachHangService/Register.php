<?php
require_once '../dbConnect.php';

function dispInfo() {
    $db = new Database();

    $hoTen = $_POST['hoTen'];
    $diaChi = $_POST['diaChi'];
    $sdt = $_POST['soDienThoai'];
    $username = $_POST['tenDangNhap'];
    $password = $_POST['matKhau'];
    $token = $_POST['token'];

    $db->query('SELECT * FROM khach_hang
                    INNER JOIN tai_khoan ON tai_khoan.MaTaiKhoan = khach_hang.MaTaiKhoan
                    WHERE tai_khoan.TenDangNhap = "'.$username.'"');

    if ($db->getRowCount() == 0) {
        $db->query('SELECT MaToken FROM token WHERE Token = "'.$token.'"');

        if ($db->getRowCount() > 0) {
            $maToken = $db->getRow()['MaToken'];

            $db->prepare('INSERT INTO `tai_khoan`(`TenDangNhap`, `MatKhau`, `MaToken`, `Type`) VALUES (:tenDangNhap, :matKhau, :maToken, :type)');
            $db->bind(':tenDangNhap', $username);
            $db->bind(':matKhau', $password);
            $db->bind('maToken', $maToken);
            $db->bind(':type', 1);
            $db->execute();

            $maTaiKhoan = $db->getLastInsertId();

            $db->prepare('INSERT INTO `person`(`HoTen`, `SoDienThoai`, `DiaChi`) VALUES (:hoTen, :soDienThoai, :diaChi)');
            $db->bind(':hoTen', $hoTen);
            $db->bind(':soDienThoai', $sdt);
            $db->bind(':diaChi', $diaChi);
            $db->execute();

            $maPerson = $db->getLastInsertId();

            $db->prepare('INSERT INTO `khach_hang`(`MaPerson`, `MaTaiKhoan`) VALUES (:maPerson, :maTaiKhoan)');
            $db->bind(':maPerson', $maPerson);
            $db->bind(':maTaiKhoan', $maTaiKhoan);
            $db->execute();

            if ($db->getRowCount() > 0) {
                echo "success";

                include_once '../Firebase.php';
                $firebase = new Firebase();
                $push = new Push();

                $datas = array();
                $datas['maKhachHang'] = $db->getLastInsertId();
                $datas['tenKhachHang'] = $hoTen;
                $datas['soDienThoai'] = $sdt;
                $datas['diaChi'] = $diaChi;
                $datas['tenDangNhap'] = $username;
                $datas['matKhau'] = $password;
                $datas['maToken'] = $maToken;

                $push->setDatas("KHACH_HANG_REGISTER_ACTION", $datas);

                $firebase->sendMultiple($db->getAllTokenAdmin(), null, $push->getDatas());
            }
        }
    } else {
        echo "exist";
    }

}

dispInfo();
?> 