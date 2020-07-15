<?php
require_once '../includes/DbOperations.php';
$ans = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	
	$res = array();
	$db = new DbOperations();
	$response = $db->viewallbook();
	#$userid, $title, $author, $semester, $description, $version, $pdf
	foreach ($response as $key => $value) {
		# code...
		$res['id'] = $value['id'];
		$res['userid']  = $value['userid'];
		$res['title'] = $value['title'];
		$res['author']  = $value['author'];
		$res['semester'] = $value['semester'];
		$res['subject'] =$value['subject'];
		$res['description'] = $value['description']; 
		$res['version'] = $value['version']; 
		$res['pdf'] =  $value['pdf'];
		$ans[] = $res;
	}
}
echo json_encode($ans);