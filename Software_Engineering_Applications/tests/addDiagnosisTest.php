<?php
include("master.php");
require __DIR__ . "/../webpages/php/addDiagnosis.php";
use PHPUnit\Framework\TestCase;
final class addDiagnosisTest extends TestCase
{
    public function testsInstantiation(): void
    {
	    $masterTest = new master();
	    $db = $masterTest->getConnection();
	    $newaddDiagnosis = new addDiagnosis();
	    $this->assertEquals("AddedDiagnosis",$newaddDiagnosis->addDiagnosisToPatient('123','diagnosis'));
	     
    }

}
?>
