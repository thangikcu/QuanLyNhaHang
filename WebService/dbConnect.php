<?php
    require_once '../Config.php';
    
    class Database {
        
        private $username  = DB_USERNAME;
        private $password = DB_PASSWORD;
        private $host = DB_HOST;
        private $db = DB_NAME;
        
        private $dpo;
        private $stm;

 
        function __construct() { 
  
            $this->connect();
        }
 
        function __destruct() {
  
            $this->close();
        }


        private function connect() { 
 
            $options = array(
                PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8",
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
            );
            
            try {
                
                $this->dpo = new PDO('mysql:host='.$this->host.';dbname='.$this->db.'', $this->username, $this->password, $options );
            
            }catch (PDOException $e) {
                echo $e->getMessage();
                exit();
            }
             
        }
        
        public function query($query){
            $this->stm = $this->dpo->query($query);        
        }
        
        public function prepare($query){
            $this->stm = $this->dpo->prepare($query);        
        }
        
        public function bind($param, $value){
            $type = null;
            if(is_null($type)){
                switch(true){
                    case is_int($value):
                        $type = PDO::PARAM_INT;
                        break;
                    case is_null($value):
                        $type = PDO::PARAM_NULL;
                        break;
                    case is_bool($value):
                        $type = PDO::PARAM_BOOL;
                        break;
                   default:
                        $type = PDO::PARAM_STR;
                        break;                                             
                }
            }
            $this->stm->bindValue($param, $value, $type);
        }
        
        public function execute(){
            $this->stm->execute();
        }

        public function getArray(){
            $this->execute();
            return $this->stm->fetchAll(PDO::FETCH_ASSOC);
        }

        public function getRow(){
            $this->execute();
            return $this->stm->fetch(PDO::FETCH_ASSOC);
        }
        
        public function getRowCount(){
            return $this->stm->rowCount();
        }
        
        public function getLastInsertId(){
            return $this->dpo->lastInsertId();
        }
        
        private function close() {

            $this->pdo = null;
            
        }
    }

?>