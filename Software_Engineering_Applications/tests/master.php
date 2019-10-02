<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
use PHPUnit\DbUnit\TestCaseTrait;


class master extends TestCase
{
	use TestCaseTrait;

	public function __construct(){

	}

	public function getConnection(): PDO
	{
		try{
		$db = new PDO('mysql:host=127.0.0.1;dbname=test', 'travis', '',array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
		return $db;
}catch(PDOException $ex){
    echo "<script>console.log('Failed to open database')</script>";
}

	}
	public function getDataSet()
	{

	}
	public function start(): void
    {
	session_start();
    }

    public function unset(): void
    {
	    session_unset();
    }

    public function destroy(): void
    {
	    die();
    }
}
?>
