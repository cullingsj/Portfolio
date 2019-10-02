<?php
session_start();

if($_GET['type']==0&&session_unset()&&session_destroy()) {
    header("Location: ../Login.php");
}
?>