<?php
	
	require_once '../includes/DbOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		$db = new DbOperations();
		if(isset($_POST['userid']) and isset($_POST['subject']) and isset($_POST['description']) and isset($_POST['semester']) and isset($_POST['title']) and isset($_POST['name'])and isset($_POST['type'])){

			$result = $db->addnote($_POST['userid'],$_POST['subject'],$_POST['description'],$_POST['semester'],$_POST['title'],$_POST['name'],$_POST['type']);
			if($result){
				$response['error'] = false ;
				$response['message'] = 'Data Entered Succesfully';
			}
			else{
				$response['error'] = true ;
				$response['message'] = 'Some Eror Happened';
			}
		}
	}
	else
	{
		$response['error'] = true ;
		$response['message'] = 'Invalid Request';
	}
echo json_encode($response);