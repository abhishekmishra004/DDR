<?php
	
	require_once '../includes/DbOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		$db = new DbOperations();
		if(isset($_POST['userid']) and  isset($_POST['pass'])){
			if($db->changepass($_POST['userid'],$_POST['pass']) == true){
				$response['error'] = false ;
				$response['message'] = 'Password Updated Succesfully';
			}
			else{
				$response['error'] = true ;
				$response['message'] = 'Some Error Happened';
			}
	}
}
echo json_encode($response);