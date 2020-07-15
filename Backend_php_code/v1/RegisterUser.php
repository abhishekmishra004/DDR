<?php
	
	require_once '../includes/DbOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST')
	{
		if(isset($_POST['userid']) and isset($_POST['name']) and isset($_POST['email_id']) and isset($_POST['password']) and isset($_POST['regd_no']))
		{

			$db = new DbOperations();
			if($db->isuserexist($_POST['userid'])){
				$response['error'] = true ;
				$response['message'] = 'Userid exist';
			}
			else{
				if($db->isemailexist($_POST['email_id'])){
					$response['error'] = true ;
					$response['message'] = 'Email exists';
				}
				else{
						$result = $db->createUser(
						$_POST['userid'],
						$_POST['name'],$_POST['email_id'],
						$_POST['password'],$_POST['regd_no']);
						if($result){
							$response['error'] = false ;
							$response['message'] = 'User Register Succesfully';
						}
						else{
							$response['error'] = true ;
							$response['message'] = 'Some Eror Happened';
						}
					}
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