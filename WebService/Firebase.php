<?php

    class Push {
     
        private $title;
        private $message;
        
        private $action;
        private $datas;
        
        function __construct() {
             
        }
     
        public function setNotification($title, $message) {
            $this->title = $title;
            $this->message = $message;
        }

        public function getNotification() {
            $res = array();
            $res['title'] = $this->title;
            $res['body'] = $this->message;
            return $res;
        }
        
        
        public function setDatas($action, $datas) {
            $this->action = $action;
            $this->datas = $datas;
            $res = array();

        }
        
        public function getDatas() {
            $this->datas['action'] = $this->action;

            return $this->datas;

        }      
    }
    
    class Firebase {

        public function send($to, $notifi, $datas) {
            $fields = array(
                'to' => $to,
                'notification' => $notifi,
                'data' => $datas,
            );
            return $this->sendPushNotification($fields);
        }

        public function sendMultiple($registration_ids, $notifi, $data) {
            $fields = array(
                'registration_ids' => $registration_ids,
                'notification' => $notifi,
                'data' => $data,
            );
     
            return $this->sendPushNotification($fields);
        }
     
        
        private function sendPushNotification($fields) {
            
            $server_key = 'AAAAd1m-eSw:APA91bGH3hiwQ1sDkbBpqia6E_j9SCXx02Fk02jCE8JG3ALhdGESISt8xjhCE8c4fYQr4GeFP6En8BV66c2QyoLNAkRFWiK4HKZBsPK5X87vtrWkEsYNQsONn2NmUlLNNDD3dJz4CSGm-QBsGjF6e2BA-yz_Ahm4ow';
            $url = 'https://fcm.googleapis.com/fcm/send';
            $headers = array(
                'Authorization: key=' .$server_key,
                'Content-Type: application/json'
            );
            
            $ch = curl_init();
     
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4 ); 
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
     
            
            $result = curl_exec($ch);
            if ($result === FALSE) {
                die('Curl failed: ' . curl_error($ch));
            }
     
            curl_close($ch);
     
            return $result;
        }
    }

?>
