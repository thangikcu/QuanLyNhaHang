<?php
require_once '../dbConnect.php';

function dispInfo()
{
    $db = new Database();

    $title = $_POST['title'];
    $content = $_POST['content'];

    $db->prepare('INSERT INTO gop_y (TieuDe, NoiDung) VALUES (:title, :content)');
    $db->bind(':title', $title);
    $db->bind(':content', $content);
    $db->execute();

    if ($db->getRowCount() > 0)
    {
        echo "success";
    }

}

dispInfo();
?> 