<?php
require_once '../includes/DbOperations.php';
$ans = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$res = array();
	$db = new DbOperations();
	$response = $db->viewnotesall();
	foreach ($response as $key => $value) {
		# code...
		$res['id'] = $value['id'];
		$res['userid']  = $value['userid'];
		$res['subject'] = $value['subject']; 
		$res['description'] = $value['description'];
		$res['semester'] = $value['semester'];
		$res['title'] = $value['title']; 
		$res['name'] = $value['name']; 
		$res['type'] = $value['type'];
		$ans[] = $res;
	}
}
echo json_encode($ans);