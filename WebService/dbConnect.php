<?php
    define('DB_USERNAME', 'root');
    define('DB_PASSWORD', '');
    define('DB_HOST', 'localhost');
    define('DB_NAME', 'db_quan_ly_nha_hang');

    class dbConnect {
 
        private $conn;

 
        function __construct() { 
  
            $this->connect();
        }
 
        function __destruct() {
  
            $this->close();
        }


        function connect() { 
 
  
            $this->conn = mysql_connect(DB_HOST, DB_USERNAME, DB_PASSWORD) or die(mysql_error());
            
  
            mysql_query("SET character_set_results=utf8", $this->conn);
            mysql_query("SET NAMES 'UTF8'");

  
            mysql_select_db(DB_NAME) or die(mysql_error());
 
  
            return $this->conn;
        }


        function close() {

            mysql_close($this->conn);
        }
    }
?>