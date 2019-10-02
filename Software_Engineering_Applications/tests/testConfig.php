<?php
try {
    $dsn = 'mysql:dbname=test;host = 127.0.0.1';
    $db = new PDO($dsn, 'travis', '');
}catch(PDOException $ex){
    echo "<script>console.log('Failed to open database')</script>";
}
?>
