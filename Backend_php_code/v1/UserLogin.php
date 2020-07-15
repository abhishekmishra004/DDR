<?php

require_once '../includes/DbOperations.php';
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
 
	if(isset($_POST['userid']) and isset($_POST['password'])){
		$db = new DbOperations();

		if($db->userLogin($_POST['userid'],$_POST['password'])){
			$result = $db->getUserbyUserid($_POST['userid']);
			$response['error'] = false ;
			$response['message'] = 'User Logined Succesfully';
			$response['userid'] = $result['userid'];
			$response['name'] = $result['name'];
			$response['email_id'] = $result['email_id'];
			$response['password'] = $result['password'];
			$response['regd_no'] = $result['regd_no'];
			$response['usertype'] = $result['usertype'];

		}
		else
		{
			$response['error'] = true ;
			$response['message'] = 'Invalid Details';
		}

	}
	else{
			$response['error'] = true ;
			$response['message'] = 'Feilds missing';

		}
}
else
	{
		$response['error'] = true ;
		$response['message'] = 'Invalid Request';
	}

echo json_encode($response);