<?php
$json = '{
  "yeuCau": [
    {
      "maYeuCau": "10",
      "yeuCau": {
        "monYeuCauHuyList": [
          
        ],
        "monYeuCauList": [
          {
            "maMon": 23,
            "soLuong": 10
          },
          {
            "maMon": 50,
            "soLuong": 69
          }
        ],
        "maHoaDon": 15,
        "maYeuCau": 0,
        "type": 1
      }
    }
  ]
}';
$json2 = '{
  "listMonUpdate": [
    {
      "maMon": 25,
      "soLuong": 9
    },
    {
      "maMon": 24,
      "soLuong": 7
    }
  ],
  "listMonNew": [
    {
      "maMon": 34,
      "soLuong": 5
    },
    {
      "maMon": 36,
      "soLuong": 2
    },
    {
      "maMon": 10,
      "soLuong": 5
    }
  ]
}';
$yummy = json_decode($json2, true);
foreach ($yummy['listMonNew'] as $monYeuCau) {
    echo $monYeuCau['maMon'];
    echo $monYeuCau['soLuong'];
}
?>