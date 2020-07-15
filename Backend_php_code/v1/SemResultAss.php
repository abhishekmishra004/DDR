<?php
require_once '../includes/DbOperations.php';
$ans = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$res = array();
	$db = new DbOperations();
	if(isset($_POST['sem']))
	{
		$response = $db->viewsemass($_POST['sem']);
		foreach ($response as $key => $value) {
		# code...
		$res['id'] = $value['id'];
		$res['userid']  = $value['userid'];
		$res['semester'] = $value['semester'];
		$res['subject'] = $value['subject']; 
		$res['description'] = $value['description'];
		$res['feature'] = $value['feature'];
		$res['title'] = $value['title']; 
		$res['name'] = $value['name']; 
		$ans[] = $res;
		}
	}
}
echo json_encode($ans);