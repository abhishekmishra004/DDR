<?php
require_once '../includes/DbOperations.php';
$ans = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	
	$res = array();
	$db = new DbOperations();
	$response = $db->viewprojectall();
	foreach ($response as $key => $value) {
		# code...
		$res['id'] = $value['id'];
		$res['userid']  = $value['userid'];
		$res['project_type'] = $value['project_type']; 
		$res['projectSem'] = $value['projectSem'];
		$res['title'] = $value['title'];
		$res['description'] = $value['description']; 
		$res['technology'] = $value['technology']; 
		$res['name'] = $value['name'];
		$res['type'] =  $value['type'];
		$ans[] = $res;
	}
}
echo json_encode($ans);